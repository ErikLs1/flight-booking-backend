package com.demo.flight_booking.service.impl;

import com.demo.flight_booking.dto.PaymentDTO;
import com.demo.flight_booking.mapper.PaymentMapper;
import com.demo.flight_booking.model.Payment;
import com.demo.flight_booking.repository.PaymentRepository;
import com.demo.flight_booking.service.PaymentService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepository;
    private final PaymentMapper paymentMapper;

    @Override
    public PaymentDTO create(PaymentDTO dto) {
        Payment payment = paymentMapper.toEntity(dto);
        Payment saved = paymentRepository.save(payment);
        return paymentMapper.toDTO(saved);
    }

    @Override
    public PaymentDTO update(Long id, PaymentDTO dto) {
        Payment payment =  paymentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Payment not found with id: " + id));

        payment.setAmount(dto.getAmount());
        payment.setPaymentStatus(dto.getPaymentStatus());
        payment.setPaymentMethod(dto.getPaymentMethod());

        Payment updated = paymentRepository.save(payment);
        return paymentMapper.toDTO(updated);
    }

    @Override
    public PaymentDTO getById(Long id) {
        Payment payment = paymentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Payment not found with id: " + id));
        return paymentMapper.toDTO(payment);
    }

    @Override
    public List<PaymentDTO> getAll() {
        return paymentRepository.findAll().stream()
                .map(paymentMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public void delete(Long id) {
        if (!paymentRepository.existsById(id)) {
            throw  new RuntimeException("Payment not found with id: " + id);
        }

        paymentRepository.deleteById(id);
    }
}
