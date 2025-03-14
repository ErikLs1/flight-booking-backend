package com.demo.flight_booking;

import com.demo.flight_booking.model.*;
import com.demo.flight_booking.model.enums.SeatClassType;
import com.demo.flight_booking.record.*;
import com.demo.flight_booking.repository.*;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.*;


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

    @Override
    public void run(String... args) throws Exception {
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

        System.out.println("Data loaded");
    }

    /**
     * Reads JSON file and inserts aircraft, seat, classes, and seats into the database.
     *
     * @param filePath
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

        // Insert Aircraft
        Aircraft aircraftEntity = insertAircraft(aircraftRecord);

        // Insert the Aircraft
        Map<String, SeatClass> seatClassMap = insertSeatClasses(seatClassRecords, aircraftEntity);

        // Insert seats
        insertSeats(seatRecords, seatClassMap, aircraftEntity);
        System.out.println("Processed: " + filePath);
    }

    /**
     * Insert seats that are linked to the correct SeatClass and sameAircraft
     *
     * @param seatRecords
     * @param seatClassMap
     * @param aircraft
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
     * Create SeatClass entities that are tied tot the same Aircraft.
     *
     * @param seatClassRecords
     * @param aircraft
     * @return
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
     * Create and save the aircraft entity into the database.
     *
     * @param record
     * @return
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

    // Use code below to print data in the console
    private AircraftRecord createAircraftFromNode(JsonNode node) {
        String aircraftModel = node.get("aircraftModel").asText();
        Integer aircraftTotalCapacity = node.get("aircraftTotalCapacity").asInt();
        Integer aircraftEconomySeats = node.get("aircraftEconomySeats").asInt();
        Integer aircraftPremiumSeats = node.get("aircraftPremiumSeats").asInt();
        Integer aircraftBusinessSeats = node.get("aircraftBusinessSeats").asInt();
        Integer aircraftFirstClassSeats = node.get("aircraftFirstClassSeats").asInt();

        return new AircraftRecord(aircraftModel,aircraftTotalCapacity,aircraftEconomySeats,aircraftPremiumSeats,aircraftBusinessSeats,aircraftFirstClassSeats);
    }

    private SeatClassRecord createSeatClassFromNode(JsonNode node) {
        String seatClassName = node.get("seatClassName").asText();
        Double basePrice = node.get("basePrice").asDouble();

        return new SeatClassRecord(seatClassName, basePrice);
    }

    private SeatRecord createSeatFromNode(JsonNode node) {
        String seatNumber = node.get("seatNumber").asText();
        Integer rowNumber = node.get("rowNumber").asInt();
        String seatLetter = node.get("seatLetter").asText();
        String seatClassName = node.get("seatClassName").asText();
        Boolean window = node.get("window").asBoolean();
        Boolean asle = node.get("aisle").asBoolean();
        Boolean extraLegroom = node.get("extraLegroom").asBoolean();
        Boolean exitRow = node.get("exitRow").asBoolean();

        return new SeatRecord(seatNumber,rowNumber, seatLetter, seatClassName, window, asle, extraLegroom, exitRow);
    }

    private JsonNode getAircraft(JsonNode json) {
        return Optional.ofNullable(json)
                .map(j -> j.get("aircraft"))
                .orElseThrow(() -> new IllegalArgumentException("Invalid JSON Object."));
    }

    private JsonNode getSeatClasses(JsonNode json) {
        return Optional.ofNullable(json)
                .map(j -> j.get("seatClasses"))
                .orElseThrow(() -> new IllegalArgumentException("Invalid JSON Object."));
    }


    private JsonNode getSeats(JsonNode json) {
        return Optional.ofNullable(json)
                .map(j -> j.get("seats"))
                .orElseThrow(() -> new IllegalArgumentException("Invalid JSON Object."));
    }


    private void processFlightsFile(String filePath) {
        JsonNode json;
        try (InputStream inputStream = new ClassPathResource(filePath).getInputStream()) {
            json = objectMapper.readTree(inputStream);
        } catch (IOException e) {
            throw new RuntimeException("Failed to read JSON data", e);
        }

        // 1) Parse arrays
        List<AirlineRecord> airlineRecords = parseAirlines(json.get("airlines"));
        List<AirportRecord> airportRecords = parseAirports(json.get("airports"));
        List<FlightRecord> flightRecords = parseFlights(json.get("flights"));

        // 2) Insert airlines
        Map<String, Airline> airlineMap = insertAirlines(airlineRecords);

        // 3) Insert airport
        Map<String, Airport> airportMap = insertAirports(airportRecords);

        // 4) Insert flights
        insertFlights(flightRecords, airlineMap, airportMap);
    }

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
}
