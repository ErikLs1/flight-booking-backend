package com.demo.flight_booking.mapper;

import com.demo.flight_booking.dto.SeatDTO;
import com.demo.flight_booking.model.Seat;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface SeatMapper {
    SeatMapper INSTANCE = Mappers.getMapper(SeatMapper.class);

    @Mapping(source = "seatClass.seatClassId", target = "seatClassId")
    @Mapping(source = "aircraft.aircraftId", target = "aircraftId")
    SeatDTO toDTO(Seat seat);

    @Mapping(source = "seatClassId", target = "seatClass.seatClassId")
    @Mapping(source = "aircraftId", target = "aircraft.aircraftId")
    Seat toEntity(SeatDTO seatDTO);
}
