package com.demo.flight_booking;

import com.demo.flight_booking.model.Aircraft;
import com.demo.flight_booking.model.Seat;
import com.demo.flight_booking.model.SeatClass;
import com.demo.flight_booking.model.enums.SeatClassType;
import com.demo.flight_booking.record.AircraftRecord;
import com.demo.flight_booking.record.SeatClassRecord;
import com.demo.flight_booking.record.SeatRecord;
import com.demo.flight_booking.repository.AircraftRepository;
import com.demo.flight_booking.repository.SeatClassRepository;
import com.demo.flight_booking.repository.SeatRepository;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;


@Data
@Component
public class DataLoader implements CommandLineRunner {

    private final ObjectMapper objectMapper;
    private final SeatClassRepository seatClassRepository;
    private final AircraftRepository aircraftRepository;
    private final SeatRepository seatRepository;

    @Override
    public void run(String... args) throws Exception {
        // Parse JSON into record objects
        JsonNode json;
        try (InputStream inputStream = new ClassPathResource("data/plane-A320-data.json").getInputStream()) {
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

        System.out.println("Data loaded");

//        // Parse aircraft object
//        JsonNode aircraftNode = getAircraft(json);
//        AircraftRecord aircraftRecord = createAircraftFromNode(aircraftNode);
//
//        // Parse "setClasses" array
//        JsonNode seatClassesNode = getSeatClasses(json);
//        List<SeatClassRecord> seatClassRecords = new ArrayList<>();
//        for (JsonNode scNode: seatClassesNode) {
//            seatClassRecords.add(createSeatClassFromNode(scNode));
//        }
//
//        // Parse "seats" array
//        JsonNode seatsNode = getSeats(json);
//        List<SeatRecord> seatRecords = new ArrayList<>();
//        for (JsonNode seatNode: seatsNode) {
//            seatRecords.add(createSeatFromNode(seatNode));
//        }
//        System.out.println("==== AIRCRAFT ====");
//        System.out.println(aircraftRecord);
//
//        System.out.println("==== SEAT CLASSES ====");
//        seatClassRecords.forEach(System.out::println);
//
//        System.out.println("==== SEATS ====");
//        seatRecords.forEach(System.out::println);
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
}
