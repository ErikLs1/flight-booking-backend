package com.demo.flight_booking.service.impl;

import com.demo.flight_booking.dto.SeatDTO;
import com.demo.flight_booking.dto.filter.SeatRecommendationDTO;
import com.demo.flight_booking.mapper.SeatMapper;
import com.demo.flight_booking.model.FlightSeat;
import com.demo.flight_booking.model.Seat;
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
                .findByFlight_FlightIdAndIsBookedFalse(filter.getFlightId());

        // Filter by seat class
        if (filter.getSeatClassType() != null) {
            freeFlightSeats = freeFlightSeats.stream()
                    .filter(fs ->fs.getSeat().getSeatClass().getSeatClassName()
                                            .equals(filter.getSeatClassType()))
                    .toList();
        }

        // Filter by sea specifications
       freeFlightSeats = freeFlightSeats.stream()
               .filter(fs -> seatMatchPreference(fs.getSeat(), filter))
               .toList();

        List<Seat> matchingSeats = freeFlightSeats.stream()
                .map(FlightSeat::getSeat)
                .toList();

        if (matchingSeats.size() < filter.getPassengerCount()) {
            return Collections.emptyList(); // Handle error later
        }

        // If adjacency is not required
        if (!Boolean.TRUE.equals(filter.getAdjacentPreferred())) {
            return matchingSeats.stream()
                    .map(seatMapper::toDTO)
                    .toList();
        }


        // if adjacency is required
        return findSeatsInOneRow(matchingSeats, filter.getPassengerCount());
    }

    /**
     * Check if a given seat matches the user's chosen seat class and preferences.
     *
     * @param seat
     * @param filter
     * @return
     */
    private boolean seatMatchPreference(Seat seat, SeatRecommendationDTO filter) {
        // Check extraLegRoom
        if (Boolean.TRUE.equals(filter.getExtraLegRoomPreferred()) && !Boolean.TRUE.equals(seat.getExtraLegRoom())) {
            return false;
        }

        // Check nearExit
        if (Boolean.TRUE.equals(filter.getNearExitPreferred()) && !Boolean.TRUE.equals(seat.getNearExit())) {
            return false;
        }

        // Check window
        if (Boolean.TRUE.equals(filter.getWindowPreferred()) && !Boolean.TRUE.equals(seat.getWindow())) {
            return false;
        }

        // Check aisle
        if (Boolean.TRUE.equals(filter.getAislePreferred()) && !Boolean.TRUE.equals(seat.getAisle())) {
            return false;
        }

        return true;
    }

    private List<SeatDTO> findSeatsInOneRow(List<Seat> seats, int seatsNeeded) {
        // Group by rowNumber
        Map<Integer, List<Seat>> seatByRow = seats.stream()
                .collect(Collectors.groupingBy(Seat::getRowNumber));

        List<Seat> result = new ArrayList<>();

        for (Map.Entry<Integer, List<Seat>> entry : seatByRow.entrySet()) {
            List<Seat> rowSeats = entry.getValue();
            if (rowSeats.size() >= seatsNeeded) {
                result.addAll(rowSeats);
            }
        }

        return result.stream()
                .map(seatMapper::toDTO)
                .toList();
    }
}
