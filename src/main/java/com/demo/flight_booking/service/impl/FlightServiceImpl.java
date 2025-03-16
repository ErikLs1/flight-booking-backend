package com.demo.flight_booking.service.impl;

import com.demo.flight_booking.dto.FlightDTO;
import com.demo.flight_booking.dto.SeatClassFeeDto;
import com.demo.flight_booking.dto.filter.FlightFilterDTO;
import com.demo.flight_booking.excpetion.FlightNoFoundException;
import com.demo.flight_booking.mapper.FlightMapper;
import com.demo.flight_booking.model.Flight;
import com.demo.flight_booking.model.SeatClass;
import com.demo.flight_booking.model.enums.SeatClassType;
import com.demo.flight_booking.repository.FlightRepository;
import com.demo.flight_booking.service.FlightService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Implements the FlightService interface.
 *
 * <p>
 *     Provides business logic for CRUD operations. Additionally, it provides method for filtering the flights
 *     by various criteria, retrieving the aircraft model, available seat class types, flight cities, and seat class
 *     fee details for a flight.
 * </p>
 */
@Service
@AllArgsConstructor
public class FlightServiceImpl implements FlightService {

    private final FlightRepository flightRepository;
    private final FlightMapper flightMapper;

    @Override
    public FlightDTO create(FlightDTO dto) {
        Flight flight = flightMapper.toEntity(dto);
        Flight saved = flightRepository.save(flight);
        return flightMapper.toDTO(saved);
    }

    @Override
    public FlightDTO update(Long id, FlightDTO dto) {
        Flight flight = flightRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Flight not found with id: " + id));

        flight.setFlightNumber(dto.getFlightNumber());
        flight.setDepartureTime(dto.getDepartureTime());
        flight.setArrivalTime(dto.getArrivalTime());
        flight.setBasePrice(dto.getBasePrice());

        Flight updated = flightRepository.save(flight);
        return flightMapper.toDTO(updated);
    }

    @Override
    public FlightDTO getById(Long id) {
        Flight flight = flightRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Flight not found with id: " + id));
        return flightMapper.toDTO(flight);
    }

    @Override
    public List<FlightDTO> getAll() {
        return flightRepository.findAll().stream()
                .map(flightMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public void delete(Long id) {
        if (!flightRepository.existsById(id)) {
            throw new RuntimeException("Flight not found with id: " + id);
        }

        flightRepository.deleteById(id);
    }

    /**
     * Searches for flights based on the filtering provided in FlightFilterDTO.
     *
     * @param filter a FlightFilterDTO containing search parameters.
     * @return a list of FlightDTO objects that match the filtering criteria.
     */
    @Override
    public List<FlightDTO> searchFlights(FlightFilterDTO filter) {
        LocalDateTime depStart = filter.getDepartureStartTime() != null
                ? filter.getDepartureStartTime()
                : LocalDateTime.of(1970, 1, 1, 0, 0);

        LocalDateTime depEnd = filter.getDepartureEndTime() != null
                ? filter.getDepartureEndTime()
                : LocalDateTime.of(9999, 12, 31, 23, 59);

        List<Flight> flights = flightRepository.filterFlights(
                filter.getDepartureCity(),
                filter.getArrivalCity(),
                filter.getAirlineName(),
                filter.getMaxPrice(),
                depStart,
                depEnd
        );

        if (filter.getMaxPrice() != null) {
            flights = flights.stream()
                            .filter(flight -> {
                                double maxFee = flight.getAircraft().getSeatClasses().stream()
                                        .mapToDouble(SeatClass::getBasePrice)
                                        .max()
                                        .orElse(0.0);
                                double finalPrice = flight.getBasePrice() + maxFee;
                                return finalPrice <= filter.getMaxPrice();
                            })
                    .toList();
        }


        return flights.stream()
                .map(flightMapper::toDTO)
                .collect(Collectors.toList());
    }

    /**
     * Retrieves the aircraft model for the flight identified by the given id.
     *
     * @param flightId the id of the flight.
     * @return a String representing the aircraft model.
     */
    @Override
    public String getAircraftModelByFlightId(Long flightId) {
        return flightRepository.findAircraftModelByFlightId(flightId);
    }

    /**
     * Retrieves the distinct seat class types available on the flight.
     *
     * @param flightId the id of the flight.
     * @return a list of SeatClassType values representing available seat classes.
     */
    @Override
    public List<SeatClassType> getSeatClassesByFlightId(Long flightId) {
        return flightRepository.findSeatClassesByFlightId(flightId);
    }

    /**
     * Retrieves the departure and arrival cities for the flight.
     *
     * @param flightId the id of the flight.
     * @return a list of two strings: the departure city and the arrival city.
     */
    @Override
    public List<String> getFlightCitiesByFlightId(Long flightId) {
        List<String> cities = flightRepository.findFlightCitiesByFlightId(flightId);
        List<String> result = new ArrayList<>();
        for(String s : cities) {
            result.add(s.split(",")[0]);
            result.add(s.split(",")[1]);
        }
        return result;
    }

    /**
     * Retrieves fee information for each seat class on the flight.
     *
     * @param flightId the id of the flight.
     * @return a list of SeatClassFeeDTO objects
     */
    @Override
    public List<SeatClassFeeDto> getSeatClassesForFlight(Long flightId) {
        Flight flight = flightRepository.findById(flightId)
                .orElseThrow(() -> new FlightNoFoundException("Flight with id " + flightId + " not found."));

        List<SeatClass> seatClasses = flight.getAircraft().getSeatClasses();

        return seatClasses.stream()
                .map(sc -> new SeatClassFeeDto(sc.getSeatClassName(), sc.getBasePrice()))
                .toList();
    }


}
