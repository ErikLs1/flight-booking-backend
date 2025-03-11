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

@Service
@AllArgsConstructor
public class SeatRecommendationService {

    private final FlightSeatRepository flightSeatRepository;
    private final SeatMapper seatMapper;

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

    private void addSeatBlockToResult(List<SeatDTO> result, List<Seat> seatBlock) {
        List<SeatDTO> seatDTOS = seatBlock.stream()
                .map(seatMapper::toDTO)
                .toList();
        result.addAll(seatDTOS);
    }

    private boolean isAdjacent(Seat prev, Seat next) {
        if (!Objects.equals(prev.getRowNumber(), next.getRowNumber())) {
            return false;
        }
        char prevLetter = prev.getSeatLetter().charAt(0);
        char nextLetter = next.getSeatLetter().charAt(0);
        return (nextLetter - prevLetter) == 1;
    }
}
