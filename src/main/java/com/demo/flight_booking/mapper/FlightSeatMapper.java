package com.demo.flight_booking.mapper;

import com.demo.flight_booking.dto.FlightSeatDTO;
import com.demo.flight_booking.model.FlightSeat;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface FlightSeatMapper {
    FlightSeatMapper INSTANCE = Mappers.getMapper(FlightSeatMapper.class);

    @Mapping(source = "seat.seatId", target = "seatId")
    @Mapping(source = "flight.flightId", target = "flightId")
    @Mapping(source = "seat.seatNumber", target = "seatNumber")
    @Mapping(source = "seat.rowNumber", target = "rowNumber")
    @Mapping(source = "seat.seatLetter", target = "seatLetter")
    @Mapping(source = "seat.seatClass.seatClassName", target = "seatClassName")
    FlightSeatDTO toDTO(FlightSeat flightSeat);

    @Mapping(source = "seatId", target = "seat.seatId")
    @Mapping(source = "flightId", target = "flight.flightId")
    FlightSeat toEntity(FlightSeatDTO flightSeatDTO);

}
