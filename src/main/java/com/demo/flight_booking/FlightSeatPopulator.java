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

@Data
@Component
@Order(2)
public class FlightSeatPopulator implements CommandLineRunner {
    private final FlightSeatRepository flightSeatRepository;
    private final SeatRepository seatRepository;
    private final FlightRepository flightRepository;


    @Override
    public void run(String... args) throws Exception {
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

                double bookingChance = 0.40;
                Boolean isBooked = random.nextDouble() < bookingChance;
                fs.setIsBooked(isBooked);
                flightSeatRepository.save(fs);
            }
        }
        System.out.println("Table with flight seat is randomly populated!");
    }
}
