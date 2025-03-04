package com.demo.flight_booking.mapper;

import com.demo.flight_booking.dto.PaymentDTO;
import com.demo.flight_booking.model.Payment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface PaymentMapper {
    PaymentMapper INSTANCE = Mappers.getMapper(PaymentMapper.class);

    @Mapping(source = "ticket.ticketId", target = "ticketId")
    PaymentDTO toDTO(Payment payment);

    @Mapping(source = "ticketId", target = "ticket.ticketId")
    Payment toEntity(PaymentDTO paymentDTO);
}
