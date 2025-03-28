package com.demo.flight_booking.controller;

import com.demo.flight_booking.dto.PaymentDTO;
import com.demo.flight_booking.model.enums.PaymentMethod;
import com.demo.flight_booking.service.PaymentService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

/**
 * REST Controller fo managing payments.
 *
 * <p>
 *     It provides endpoints for basic CRUD operations and additional endpoint
 *     to retrieve the list of supported payment methods.
 * </p>
 */
@CrossOrigin(origins = "http://localhost:3000")
@AllArgsConstructor
@RestController
@RequestMapping("/api/payment")
public class PaymentController implements BasicController<PaymentDTO, Long> {
    private final PaymentService paymentService;


    @Override
    public ResponseEntity<PaymentDTO> create(PaymentDTO dto) {
        PaymentDTO saved = paymentService.create(dto);
        return new ResponseEntity<>(saved, HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<PaymentDTO> update(Long id, PaymentDTO dto) {
        PaymentDTO updated = paymentService.update(id, dto);
        return ResponseEntity.ok(updated);
    }

    @Override
    public ResponseEntity<PaymentDTO> getById(Long id) {
        PaymentDTO paymentDTO = paymentService.getById(id);
        return ResponseEntity.ok(paymentDTO);
    }

    @Override
    public ResponseEntity<List<PaymentDTO>> getAll() {
        List<PaymentDTO> paymentDTOS = paymentService.getAll();
        return ResponseEntity.ok(paymentDTOS);
    }

    @Override
    public ResponseEntity<String> delete(Long id) {
        paymentService.delete(id);
        return ResponseEntity.ok("Payment deleted!");
    }

    /**
     * Retrieves all supported payment methods.
     *
     * @return a list of PaymentMethod enum values.
     */
    @GetMapping("/methods")
    public ResponseEntity<List<PaymentMethod>> getAllPaymentMethods() {
        List<PaymentMethod> paymentMethods = Arrays.asList(PaymentMethod.values());
        return ResponseEntity.ok(paymentMethods);
    }
}
