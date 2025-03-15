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

/**
 * Service responsible for recommending seats based on user preferences.
 *
 *<p>
 *     The service retrieves available seats for the flight, applies filters
 *     based on the seat class, extra legroom, near exit, window, aisle, and if there is more than one
 *     passenger then passengers have option to apply filter for finding
 *     blocks of seat where they can seat together.
 *</p>
 */
@Service
@AllArgsConstructor
public class SeatRecommendationService {

    private final FlightSeatRepository flightSeatRepository;
    private final SeatMapper seatMapper;

    /**
     * Recommends seats based on the provided filters.
     *
     * <p>
     *     If users do not want to seat together, the method returns all matching seats.
     *     If users want to seat together, it tries to find blocks of free places where
     *     users can sit together.
     * </p>
     * @param filter the filter request including flightId, seat class, passenger count and seat preferences.
     * @return a list of SeatDTO objects matching the filters applied.
     */
    public List<SeatDTO> recommendSeats(SeatRecommendationDTO filter) {

        if (!Boolean.TRUE.equals(filter.getAdjacentPreferred())) {
            List<FlightSeat> freeFlightSeats = flightSeatRepository
                    .findByFlight_FlightIdAndIsBookedFalse(filter.getFlightId());

            if (filter.getSeatClassType() != null) {
                freeFlightSeats = freeFlightSeats.stream()
                        .filter(fs ->fs.getSeat().getSeatClass().getSeatClassName()
                                .equals(filter.getSeatClassType()))
                        .toList();
            }

            freeFlightSeats = freeFlightSeats.stream()
                    .filter(fs -> seatMatchPreference(fs.getSeat(), filter))
                    .toList();

            return freeFlightSeats.stream()
                    .map(fs -> seatMapper.toDTO(fs.getSeat()))
                    .toList();
        } else {
            return findSeatsInOneRow(filter);
        }
    }

    /**
     * Checks whether a given seat meets the user's preferences.
     * This method checks if seat has extra legroom, near exit, window and aisle.
     *
     * @param seat the Seat object to check.
     * @param filter the filter containing the user's preferences.
     * @return true if seat matches all the selected preferences, in other case false.
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

    /**
     * Finds a block of consecutive free seats in one row that fit the required number of passengers.
     *
     *<p>
     *     The method firs retrieves all seats for the given flight, grouped by row. Then it iterates through
     *     each row to find consecutive free seats that match the user's seat preferences.
     *</p>
     * @param filter the filter containing the user's preferences, flight id and passenger count.
     * @return a list of SeatDTO objects where users can seat together and that meet the preferences.
     */
    private List<SeatDTO> findSeatsInOneRow(SeatRecommendationDTO filter) {
        List<FlightSeat> allFlightSeats = flightSeatRepository.findFlightSeatsAndSortedByRowLetter(filter.getFlightId());

        if (filter.getSeatClassType() != null) {
            allFlightSeats = allFlightSeats.stream()
                    .filter(fs -> fs.getSeat().getSeatClass().getSeatClassName()
                            .equals(filter.getSeatClassType()))
                    .toList();
        }

        List<SeatDTO> result = new ArrayList<>();
        List<Seat> currentBlock = new ArrayList<>();

        Seat previousSeat = null;
        for (FlightSeat fs : allFlightSeats) {
            Seat seat = fs.getSeat();

            boolean isFree = !fs.getIsBooked() && seatMatchPreference(seat, filter);

            if (isFree) {
                if (previousSeat == null) {
                    currentBlock.add(seat);
                    previousSeat = seat;
                } else {
                    if (isAdjacent(previousSeat, seat)) {
                        currentBlock.add(seat);
                        previousSeat = seat;
                    } else {
                        if (currentBlock.size() >= filter.getPassengerCount()) {
                            addSeatBlockToResult(result, currentBlock);
                        }
                        currentBlock.clear();
                        currentBlock.add(seat);
                        previousSeat = seat;
                    }
                }
            } else {
                if (currentBlock.size() >= filter.getPassengerCount()) {
                    addSeatBlockToResult(result, currentBlock);
                }
                currentBlock.clear();
                previousSeat = null;
            }
        }

        if (currentBlock.size() >= filter.getPassengerCount()) {
            addSeatBlockToResult(result, currentBlock);
        }

        return result;
    }

    /**
     * Converts a block of Seat objects to a list of SeatDTO objects and adds them to the result list.
     *
     * @param result the lists to which the SeatDTOs will be added.
     * @param seatBlock a block of seat objects that match the filters.
     */
    private void addSeatBlockToResult(List<SeatDTO> result, List<Seat> seatBlock) {
        List<SeatDTO> seatDTOS = seatBlock.stream()
                .map(seatMapper::toDTO)
                .toList();
        result.addAll(seatDTOS);
    }

    /**
     * Determines whether two seats are adjacent.
     *
     * <p>
     *     Two seats are adjacent if they are in the same row and their seat letters are consecutive.
     *     Meaning that seats are located next to each other. Example: Seats with letters A and B.
     * </p>
     *
     * @param prev
     * @param next
     * @return
     */
    private boolean isAdjacent(Seat prev, Seat next) {
        if (!Objects.equals(prev.getRowNumber(), next.getRowNumber())) {
            return false;
        }
        char prevLetter = prev.getSeatLetter().charAt(0);
        char nextLetter = next.getSeatLetter().charAt(0);
        return (nextLetter - prevLetter) == 1;
    }
}
