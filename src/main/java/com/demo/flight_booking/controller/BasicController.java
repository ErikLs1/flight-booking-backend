package com.demo.flight_booking.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Generic CRUD Controller interface for handling basic CRUD operations.
 *
 * @param <T> The DTO type.
 * @param <ID> The identifier.
 */
public interface BasicController<T, ID> {

    @PostMapping
    ResponseEntity<T> create(@RequestBody T dto);

    @PutMapping("/{id}")
    ResponseEntity<T> update(@PathVariable("id") ID id, @RequestBody T dto);

    @GetMapping("/{id}")
    ResponseEntity<T> getById(@PathVariable("id") ID id);

    @GetMapping
    ResponseEntity<List<T>> getAll();

    @DeleteMapping("/{id}")
    ResponseEntity<String> delete(@PathVariable("id") ID id);
}
