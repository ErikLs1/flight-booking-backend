package com.demo.flight_booking.service.impl;

import com.demo.flight_booking.dto.PersonDTO;
import com.demo.flight_booking.service.PersonService;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class PersonServiceImpl implements PersonService {
    @Override
    public PersonDTO create(PersonDTO dto) {
        return null;
    }

    @Override
    public PersonDTO update(Long id, PersonDTO dto) {
        return null;
    }

    @Override
    public PersonDTO getById(Long id) {
        return null;
    }

    @Override
    public List<PersonDTO> getAll() {
        return List.of();
    }

    @Override
    public void delete(Long id) {

    }
}
