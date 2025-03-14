package com.demo.flight_booking;

import com.demo.flight_booking.model.Flight;
import com.demo.flight_booking.model.FlightSeat;
import com.demo.flight_booking.model.Seat;
import com.demo.flight_booking.repository.FlightRepository;
import com.demo.flight_booking.repository.FlightSeatRepository;
import com.demo.flight_booking.repository.SeatRepository;
import lombok.Data;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Random;


/**
 * FlightSeatPopulator is responsible for filling the FlightSeat table with the seat data
 * for each flight in the database. It randomly decides which flight sets mark as booked.
 *
 * <p>
 * This component runs at application startup and if the FlightSeat table is empty.
 * </p>
 */
@Data
@Component
@Order(2)
public class FlightSeatPopulator implements CommandLineRunner {
    private final FlightSeatRepository flightSeatRepository;
    private final SeatRepository seatRepository;
    private final FlightRepository flightRepository;


    /**
     * Runs the population process for flight seats.
     *
     * <p>
     * For each flight it gets associated aircraft seats, then creates a FlightSeat record for
     * each seat and the randomly marks some seats as booked.
     * </p>
     * @param args command line arguments
     * @throws Exception if an error occurs during the data processing.
     */
    @Override
    public void run(String... args) throws Exception {
        // Skip population if data already exists
        if (flightSeatRepository.count() > 0) {
            return;
        }

        List<Flight> flights = flightRepository.findAll();
        Random random = new Random();

        for(Flight flight : flights) {
            Long aircraftId = flight.getAircraft().getAircraftId();
            List<Seat> seats = seatRepository.findByAircraft_AircraftId(aircraftId);

            for(Seat seat : seats) {
                FlightSeat fs = new FlightSeat();
                fs.setFlight(flight);
                fs.setSeat(seat);

                // Chance that a seat will be marked as booked
                double bookingChance = 0.40;
                Boolean isBooked = random.nextDouble() < bookingChance;
                fs.setIsBooked(isBooked);
                flightSeatRepository.save(fs);
            }
        }
        System.out.println("Table with flight seat is randomly populated!");
    }
}
