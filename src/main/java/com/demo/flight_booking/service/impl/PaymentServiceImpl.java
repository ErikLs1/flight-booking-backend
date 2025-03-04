package com.demo.flight_booking.service.impl;

import com.demo.flight_booking.dto.PaymentDTO;
import com.demo.flight_booking.service.PaymentService;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class PaymentServiceImpl implements PaymentService {
    @Override
    public PaymentDTO create(PaymentDTO dto) {
        return null;
    }

    @Override
    public PaymentDTO update(Long id, PaymentDTO dto) {
        return null;
    }

    @Override
    public PaymentDTO getById(Long id) {
        return null;
    }

    @Override
    public List<PaymentDTO> getAll() {
        return List.of();
    }

    @Override
    public void delete(Long id) {

    }
}
