package com.demo.flight_booking;

import com.demo.flight_booking.model.*;
import com.demo.flight_booking.model.enums.SeatClassType;
import com.demo.flight_booking.record.*;
import com.demo.flight_booking.repository.*;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.*;


/**
 * DataLoader is responsible for loading initial data into the database.
 * It processes JSON files for aircraft, seat classes, seats and flight data.
 *
 * <p>
 *     This component is executed on application startup. It reads JSON files from the classpath,
 *     parses them into record objects, and saves the data into the database.
 * </p>
 */
@Data
@Component
@Order(1)
public class DataLoader implements CommandLineRunner {

    private final ObjectMapper objectMapper;
    private final SeatClassRepository seatClassRepository;
    private final AircraftRepository aircraftRepository;
    private final SeatRepository seatRepository;
    private final AirlineRepository airlineRepository;
    private final AirportRepository airportRepository;
    private final FlightRepository flightRepository;

    /**
     * Executes the data loading process.
     *
     * <p>
     *     The method first checks if aircraft data already exist in the database.
     *     If not, it processes JSON files containing aircraft, seat classes, seats and flights data.
     * </p>
     * @param args command line arguments
     * @throws Exception if an error occurs during the data processing.
     */
    @Override
    public void run(String... args) throws Exception {
        // Skip if data is already in the database
        if (aircraftRepository.count() > 0) {
            return;
        }

        String[] filePaths = {
                "data/planes/airbus/plane-A320-data.json",
                "data/planes/airbus/plane-A321-data.json",
                "data/planes/airbus/plane-A322-data.json",
                "data/planes/embraer/plane-embraer-190-data.json",
                "data/planes/embraer/plane-embraer-191-data.json",
                "data/planes/embraer/plane-embraer-192-data.json"
        };

        for (String path : filePaths) {
            processFile(path);
        }

        String flightsPath = "data/flights/airlines-airports-flights.json";
        processFlightsFile(flightsPath);
    }

    /**
     * Reads JSON file and inserts aircraft, seat, classes, and seats into the database.
     *
     * @param filePath the path to the JSON file
     */
    private void processFile(String filePath) {
        // Parse JSON into record objects
        JsonNode json;
        try (InputStream inputStream = new ClassPathResource(filePath).getInputStream()) {
            json = objectMapper.readTree(inputStream);
        } catch (IOException e) {
            throw new RuntimeException("Failed to read JSON data", e);
        }

        AircraftRecord aircraftRecord = parseAircraft(json.get("aircraft"));
        List<SeatClassRecord> seatClassRecords = parseSeatClassRecords(json.get("seatClasses"));
        List<SeatRecord> seatRecords = parseSeatRecords(json.get("seats"));

        Aircraft aircraftEntity = insertAircraft(aircraftRecord);
        Map<String, SeatClass> seatClassMap = insertSeatClasses(seatClassRecords, aircraftEntity);
        insertSeats(seatRecords, seatClassMap, aircraftEntity);
        System.out.println("Processed: " + filePath);
    }

    /**
     * Insert seat records into the database.
     *
     * @param seatRecords the list of seat record objects parsed from JSON
     * @param seatClassMap a mapping of seat class names to SeatClass entities.
     * @param aircraft the aircraft entity for which the seats belong.
     */
    private void insertSeats(List<SeatRecord> seatRecords, Map<String, SeatClass> seatClassMap, Aircraft aircraft) {
        List<Seat> seats = new ArrayList<>();

        for (SeatRecord sr : seatRecords) {
            SeatClass seatClassName = seatClassMap.get(sr.seatClassName());
            if (seatClassName == null) {
                throw new RuntimeException("No matching seat classes: " + sr.seatClassName());
            }

            Seat seat = new Seat();
            seat.setSeatNumber(sr.seatNumber());
            seat.setRowNumber(sr.rowNumber());
            seat.setSeatLetter(sr.seatLetter());

            seat.setSeatClass(seatClassName);
            seat.setAircraft(aircraft);

            seat.setWindow(sr.window());
            seat.setAisle(sr.aisle());
            seat.setExtraLegRoom(sr.extraLegRoom());
            seat.setNearExit(sr.exitRow());

            seats.add(seat);
        }

        seatRepository.saveAll(seats);
    }

    /**
     * Insert seat classes data into the database.
     *
     * @param seatClassRecords the list of seat class records parsed from JSON.
     * @param aircraft tHE Aircraft entity that seat classes belong to.
     * @return a map linking the seat class name to the saved SeatClass entity
     */
    private Map<String, SeatClass> insertSeatClasses(List<SeatClassRecord> seatClassRecords, Aircraft aircraft) {
        Map<String, SeatClass> seatClassMap = new HashMap<>();
        for (SeatClassRecord scr : seatClassRecords) {
            SeatClassType className = SeatClassType.valueOf(scr.seatClassName());
            SeatClass seatClass  =new SeatClass();
            seatClass.setAircraft(aircraft);
            seatClass.setSeatClassName(className);
            seatClass.setBasePrice(scr.basePrice());

            SeatClass saved = seatClassRepository.save(seatClass);
            seatClassMap.put(scr.seatClassName(), saved);
        }

        return seatClassMap;
    }

    /**
     * Insert the aircraft data into the database.
     *
     * @param record AircraftRecord parsed from JSON.
     * @return saved Aircraft entity.
     */
    private Aircraft insertAircraft(AircraftRecord record) {
        Aircraft aircraft = new Aircraft();
        aircraft.setAircraftModel(record.aircraftModel());
        aircraft.setAircraftTotalCapacity(record.aircraftTotalCapacity());
        aircraft.setAircraftEconomySeats(record.aircraftEconomySeats());
        aircraft.setAircraftPremiumSeats(record.aircraftPremiumSeats());
        aircraft.setAircraftBusinessSeats(record.aircraftBusinessSeats());
        aircraft.setAircraftFirstClassSeats(record.aircraftFirstClassSeats());
        return aircraftRepository.save(aircraft);
    }

    /**
     * Parses JSON and converts it into a list of SeatRecord objects.
     *
     * @param node JSON node representing seats.
     * @return List of parsed SeatRecord objects.
     */
    private List<SeatRecord> parseSeatRecords(JsonNode node) {
        List<SeatRecord> seatRecords = new ArrayList<>();
        for (JsonNode n : node) {
            seatRecords.add(new SeatRecord(
                    n.get("seatNumber").asText(),
                    n.get("rowNumber").asInt(),
                    n.get("seatLetter").asText(),
                    n.get("seatClassName").asText(),
                    n.get("window").asBoolean(),
                    n.get("aisle").asBoolean(),
                    n.get("extraLegroom").asBoolean(),
                    n.get("exitRow").asBoolean()
            ));
        }
        return seatRecords;
    }

    /**
     * Parses JSON and converts it into a list of SeatClassRecord objects.
     *
     * @param node JSON node representing seatClasses.
     * @return List of parsed SeatClassRecords.
     */
    private List<SeatClassRecord> parseSeatClassRecords(JsonNode node) {
        List<SeatClassRecord> seatClassRecords = new ArrayList<>();
        for (JsonNode n : node) {
            seatClassRecords.add(new SeatClassRecord(
                    n.get("seatClassName").asText(),
                    n.get("basePrice").asDouble()
            ));
        }
        return seatClassRecords;
    }

    /**
     * Parses JSON and converts it into Aircraft object.
     *
     * @param node JSON node representing Aircraft.
     * @return Object of parsed AircraftRecord.
     */
    private AircraftRecord parseAircraft(JsonNode node) {
        return new AircraftRecord(
                node.get("aircraftModel").asText(),
                node.get("aircraftTotalCapacity").asInt(),
                node.get("aircraftEconomySeats").asInt(),
                node.get("aircraftPremiumSeats").asInt(),
                node.get("aircraftBusinessSeats").asInt(),
                node.get("aircraftFirstClassSeats").asInt()
        );
    }

    /**
     *  Parse JSON file and flights, airlines and airports data.
     *
     * @param filePath The path to the JSON file.
     */
    private void processFlightsFile(String filePath) {
        JsonNode json;
        try (InputStream inputStream = new ClassPathResource(filePath).getInputStream()) {
            json = objectMapper.readTree(inputStream);
        } catch (IOException e) {
            throw new RuntimeException("Failed to read JSON data", e);
        }

        List<AirlineRecord> airlineRecords = parseAirlines(json.get("airlines"));
        List<AirportRecord> airportRecords = parseAirports(json.get("airports"));
        List<FlightRecord> flightRecords = parseFlights(json.get("flights"));

        Map<String, Airline> airlineMap = insertAirlines(airlineRecords);
        Map<String, Airport> airportMap = insertAirports(airportRecords);
        insertFlights(flightRecords, airlineMap, airportMap);
    }

    /**
     * Insert flight records into the database.
     *
     * @param flightRecords the list of flight record objects parsed from JSON.
     * @param airlineMap a mapping of airline IATA codes to Airline entities.
     * @param airportMap a mapping of airport codes to Airport entities.
     */
    private void insertFlights(List<FlightRecord> flightRecords, Map<String, Airline> airlineMap, Map<String, Airport> airportMap) {
        for (FlightRecord fr : flightRecords) {
            Airline airline = airlineMap.get(fr.airlineIATACode());
            if (airline == null) {
                throw new RuntimeException("No airline found with code: " + fr.airlineIATACode());
            }

            Airport departureAirport = airportMap.get(fr.departureAirportCode());
            if (departureAirport == null) {
                throw new RuntimeException("No departure airport found with code: " + fr.departureAirportCode());
            }

            Airport arrivalAirport = airportMap.get(fr.arrivalAirportCode());
            if (arrivalAirport == null) {
                throw new RuntimeException("No arrival airport found with code: " + fr.arrivalAirportCode());
            }

            Aircraft aircraft = aircraftRepository.findByAircraftModel(fr.aircraftModel())
                    .orElseThrow(() -> new RuntimeException("No aircraft found with name: " + fr.aircraftModel()));

            Flight flight = new Flight();
            flight.setFlightNumber(fr.flightNumber());
            flight.setAirline(airline);
            flight.setDepartureAirport(departureAirport);
            flight.setArrivalAirport(arrivalAirport);
            flight.setAircraft(aircraft);
            flight.setBasePrice(fr.basePrice());
            flight.setDepartureTime(LocalDateTime.parse(fr.departureTime()));
            flight.setArrivalTime(LocalDateTime.parse(fr.arrivalTime()));
            flightRepository.save(flight);
        }
    }

    /**
     * Insert Airports data into the database.
     *
     * @param airportRecords The list of airport record objects parsed from JSON.
     * @return A mapping of airport codes to the Airport entities.
     */
    private Map<String, Airport> insertAirports(List<AirportRecord> airportRecords) {
        Map<String, Airport> map = new HashMap<>();
        for (AirportRecord record : airportRecords) {
            Optional<Airport> airport = airportRepository.findByAirportCode(record.airportCode());
            if (airport.isPresent()) {
                map.put(record.airportCode(), airport.get());
                continue;
            }

            Airport ar = new Airport();
            ar.setAirportCode(record.airportCode());
            ar.setAirportName(record.airportName());
            ar.setAirportCity(record.airportCity());
            ar.setAirportCountry(record.airportCountry());

            Airport saved = airportRepository.save(ar);
            map.put(record.airportCode(), saved);
        }
        return map;
    }

    /**
     * Inserts airline data into the database.
     *
     * @param airlineRecords THe list of airline record objects parsed from JSON.
     * @return A mapping of airline IATA code to the Airline entities.
     */
    private Map<String, Airline> insertAirlines(List<AirlineRecord> airlineRecords) {
        Map<String, Airline> map = new HashMap<>();
        for (AirlineRecord record : airlineRecords) {
            Optional<Airline> airline = airlineRepository.findByAirlineIATACode(record.airlineIATACode());
            if (airline.isPresent()) {
                map.put(record.airlineIATACode(),airline.get());
                continue;
            }

            Airline ar = new Airline();
            ar.setAirlineName(record.airlineName());
            ar.setAirlineIATACode(record.airlineIATACode());

            Airline saved = airlineRepository.save(ar);
            map.put(record.airlineIATACode(), saved);
        }
        return map;
    }

    /**
     * Parses JSON and converts it into a list of FlightRecord objects.
     *
     * @param node JSON node representing flights.
     * @return List of parsed FlightRecord objects.
     */
    private List<FlightRecord> parseFlights(JsonNode node) {
        List<FlightRecord> flightRecords = new ArrayList<>();
        for (JsonNode n : node) {
            flightRecords.add(new FlightRecord(
                    n.get("flightNumber").asText(),
                    n.get("airlineIATACode").asText(),
                    n.get("departureAirport").asText(),
                    n.get("arrivalAirport").asText(),
                    n.get("aircraftModel").asText(),
                    n.get("basePrice").asDouble(),
                    n.get("departureTime").asText(),
                    n.get("arrivalTime").asText()
            ));
        }
        return flightRecords;
    }

    /**
     * Parses JSON and converts it into a list of AirportRecord objects.
     *
     * @param node JSON node representing airports.
     * @return List of parsed AirportRecord objects.
     */
    private List<AirportRecord> parseAirports(JsonNode node) {
        List<AirportRecord> airportRecords = new ArrayList<>();
        for (JsonNode n : node) {
            airportRecords.add(new AirportRecord(
                    n.get("airportCode").asText(),
                    n.get("airportName").asText(),
                    n.get("airportCity").asText(),
                    n.get("airportCountry").asText()
            ));
        }
        return airportRecords;
    }

    /**
     * Parses JSON and converts it into a list of AirlineRecord objects.
     *
     * @param node JSON node representing airlines.
     * @return List of parsed AirlineRecord objects.
     */
    private List<AirlineRecord> parseAirlines(JsonNode node) {
        List<AirlineRecord> airlineRecords = new ArrayList<>();
        for (JsonNode n : node) {
            airlineRecords.add(new AirlineRecord(
                    n.get("airlineName").asText(),
                    n.get("airlineIATACode").asText()
            ));
        }
        return airlineRecords;
    }

//    version: '3.8'
//
//    services:
//    db:
//    image: postgres
//    environment:
//            - POSTGRES_USER=postgres
//      - POSTGRES_PASSWORD=Bosito123
//      - POSTGRES_DB=flight-db
//    ports:
//            - "5433:5432"
//
//    backend:
//    build: .
//    ports:
//            - "8000:8080"
//    depends_on:
//            - db
//    environment:
//    SPRING_DATASOURCE_URL: jdbc:postgresql://db:5432/flight-db
//    SPRING_DATASOURCE_USERNAME: postgres
//    SPRING_DATASOURCE_PASSWORD: Bosito123
//
//    frontend:
//    build: ../../flight-booking/flight-frontend
//    ports:
//            - "3000:3000"
//    depends_on:
//            - backend
//    environment:
//    VITE_API_URL: http://localhost:8000


}
