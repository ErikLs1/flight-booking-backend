package com.demo.flight_booking.service.impl;

import com.demo.flight_booking.dto.SeatDTO;
import com.demo.flight_booking.dto.filter.SeatRecommendationDTO;
import com.demo.flight_booking.mapper.SeatMapper;
import com.demo.flight_booking.model.FlightSeat;
import com.demo.flight_booking.model.Seat;
import com.demo.flight_booking.model.SeatClass;
import com.demo.flight_booking.model.enums.SeatClassType;
import com.demo.flight_booking.repository.FlightSeatRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class SeatRecommendationService {

    private final FlightSeatRepository flightSeatRepository;
    private final SeatMapper seatMapper;

    public List<SeatDTO> recommendSeats(SeatRecommendationDTO filter) {
        // Find all unbooked flights for the specific flight
        List<FlightSeat> freeFlightSeats = flightSeatRepository
                .findByFlight_FlightIdAndIsBooked(filter.getFlightId(), false);

        // Filter by seat class and filer
        List<FlightSeat> desiredSeats = freeFlightSeats.stream()
                .filter(fs -> seatMatchPreference(fs.getSeat(), filter))
                .toList();

        // If not enough seat the return
        if (desiredSeats.size() < filter.getPassengerCount()) {
            return Collections.emptyList(); // Handle later (not enough places for this plane
        }

        // If adjacency is required, go here
        if (!Boolean.TRUE.equals(filter.getAdjacentPreferred())) {
            return desiredSeats.stream()
                    .limit(filter.getPassengerCount())
                    .map(fs -> seatMapper.toDTO(fs.getSeat()))
                    .toList();
        }

        return findAdjacentSeats(desiredSeats, filter.getPassengerCount());
    }

    /**
     * Check if a given seat matches the user's chosen seat class and preferences.
     *
     * @param seat
     * @param filter
     * @return
     */
    private boolean seatMatchPreference(Seat seat, SeatRecommendationDTO filter) {
        SeatClass seatClass = seat.getSeatClass();

        // Check seat class type
        if (filter.getSeatClassType() != null) {
            SeatClassType type = filter.getSeatClassType();
            if (!seatClass.getSeatClassName().equals(type)) {
                return false;
            }
        }

        // Check extraLegRoom
        if (Boolean.TRUE.equals(filter.getExtraLegRoomPreferred()) && !Boolean.TRUE.equals(seatClass.getExtraLegRoom())) {
            return false;
        }

        // Check nearExit
        if (Boolean.TRUE.equals(filter.getNearExitPreferred()) && !Boolean.TRUE.equals(seatClass.getNearExit())) {
            return false;
        }

        // Check window
        if (Boolean.TRUE.equals(filter.getWindowPreferred()) && !Boolean.TRUE.equals(seatClass.getWindow())) {
            return false;
        }

        // Check aisle
        if (Boolean.TRUE.equals(filter.getAislePreferred()) && !Boolean.TRUE.equals(seatClass.getAisle())) {
            return false;
        }

        return true;
    }

    private List<SeatDTO> findAdjacentSeats(List<FlightSeat> matchingFlightSeats, int seatsNeeded) {
        // Get all seat fo the flight
        List<Seat> seats = matchingFlightSeats.stream()
                .map(FlightSeat::getSeat)
                .toList();

        // Group seats by row
        Map<Integer, List<Seat>> seatsByRow = new HashMap<>();
        for (Seat s : seats) {
            int rowNumber = parseRowNumber(s.getSeatNumber());
            seatsByRow.computeIfAbsent(rowNumber, k -> new ArrayList<>()).add(s);
        }

        // Sort each row by seat letter. Then check if there is block that suits us
        for (Map.Entry<Integer, List<Seat>> entry : seatsByRow.entrySet()) {
            int row = entry.getKey();
            List<Seat> rowSeats = entry.getValue();

            // Sort by letters;
            rowSeats.sort(Comparator.comparing(this::parseSeatLetter));

            List<Seat> contiguous = findContiguousBlock(rowSeats, seatsNeeded);
            if (contiguous.size() == seatsNeeded) {
                return contiguous.stream()
                        .map(seatMapper::toDTO)
                        .toList();
            }
        }

        return Collections.emptyList();
    }

    /**
     * Finds a contiguous block of needed seats in a sorted list.
     * Contiguous means consecutive: A -> B -> C, etc.
     *
     * @param sortedSeats
     * @param neededSeats
     * @return
     */
    private List<Seat> findContiguousBlock(List<Seat> sortedSeats, int neededSeats) {
        for (int i = 0; i <= sortedSeats.size() - neededSeats; i++) {
            List<Seat> block = new ArrayList<>();
            block.add(sortedSeats.get(i));
            char prevCol = parseSeatLetter(sortedSeats.get(i));

            for (int j = i + 1; j < i + neededSeats; j++) {
                char thisCol = parseSeatLetter(sortedSeats.get(j));
                if (thisCol == (char) (prevCol + 1)) {
                    block.add(sortedSeats.get(i));
                    prevCol = thisCol;
                } else {
                    break;
                }
            }

            if (block.size() == neededSeats) {
                return block;
            }
        }

        return Collections.emptyList();
    }

    /**
     * Parse the row number 12A -> 12
     *
     * @param seatNumber number of the seat in plane
     * @return return the row
     */
    private int parseRowNumber(String seatNumber) {
        StringBuilder sb = new StringBuilder();
        for (char c : seatNumber.toCharArray()) {
            if (Character.isDigit(c)) {
                sb.append(c);
            } else {
                break;
            }
        }
        return !sb.isEmpty() ? Integer.parseInt(sb.toString()) : 0;
    }

    /**
     * Parse the seat letter from the seat number. 12A -> A
     *
     * @param seat number of the seat in plane
     * @return column in which seat is located
     */
    private char parseSeatLetter(Seat seat) {
        String seatNumber = seat.getSeatNumber();

        for (char c : seatNumber.toCharArray()) {
            if (Character.isLetter(c)) {
                return c;
            }
        }

        return ' ';
    }
}
