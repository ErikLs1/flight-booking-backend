package com.demo.flight_booking.service.impl;

import com.demo.flight_booking.dto.PersonDTO;
import com.demo.flight_booking.mapper.PersonMapper;
import com.demo.flight_booking.model.Person;
import com.demo.flight_booking.repository.PersonRepository;
import com.demo.flight_booking.service.PersonService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 *  Implements the Person service interface.
 *  <p>
 *      Provides basic CRUD operations.
 *  </p>
 */
@Service
@AllArgsConstructor
public class PersonServiceImpl implements PersonService {

    private final PersonRepository personRepository;
    private final PersonMapper personMapper;

    @Override
    public PersonDTO create(PersonDTO dto) {
        Person person = personMapper.toEntity(dto);
        Person saved = personRepository.save(person);
        return personMapper.toDTO(saved);
    }

    @Override
    public PersonDTO update(Long id, PersonDTO dto) {
        Person person = personRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Person not found with id: " + id));

        person.setFirstName(dto.getFirstName());
        person.setLastName(dto.getLastName());
        person.setEmail(dto.getEmail());
        person.setPhone(dto.getPhone());

        Person updated = personRepository.save(person);
        return personMapper.toDTO(updated);
    }

    @Override
    public PersonDTO getById(Long id) {
        Person person = personRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Person not found with id: " + id));
        return personMapper.toDTO(person);
    }

    @Override
    public List<PersonDTO> getAll() {
        return personRepository.findAll().stream()
                .map(personMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public void delete(Long id) {
        if (!personRepository.existsById(id)) {
            throw new RuntimeException("Person not found with id: " + id);
        }

        personRepository.deleteById(id);
    }
}
