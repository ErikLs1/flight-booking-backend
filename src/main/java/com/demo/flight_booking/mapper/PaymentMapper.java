package com.demo.flight_booking.mapper;

import com.demo.flight_booking.dto.PaymentDTO;
import com.demo.flight_booking.model.Payment;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PaymentMapper {
    Payment toEntity(PaymentDTO paymentDTO);
    PaymentDTO toDTO(Payment payment);
}
