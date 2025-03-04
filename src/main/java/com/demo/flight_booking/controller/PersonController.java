package com.demo.flight_booking.controller;

import com.demo.flight_booking.dto.PersonDTO;
import com.demo.flight_booking.service.PersonService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin(origins = "http://localhost:3000")
@AllArgsConstructor
@RestController
@RequestMapping("/api/person")
public class PersonController implements BasicController<PersonDTO, Long> {
    private  final PersonService personService;

    @Override
    public ResponseEntity<PersonDTO> create(PersonDTO dto) {
        PersonDTO saved = personService.create(dto);
        return new ResponseEntity<>(saved, HttpStatus.ACCEPTED);
    }

    @Override
    public ResponseEntity<PersonDTO> update(Long id, PersonDTO dto) {
        PersonDTO updated = personService.update(id, dto);
        return ResponseEntity.ok(updated);
    }

    @Override
    public ResponseEntity<PersonDTO> getById(Long id) {
        PersonDTO personDTO = personService.getById(id);
        return ResponseEntity.ok(personDTO);
    }

    @Override
    public ResponseEntity<List<PersonDTO>> getAll() {
        List<PersonDTO> personDTOS = personService.getAll();
        return ResponseEntity.ok(personDTOS);
    }

    @Override
    public ResponseEntity<String> delete(Long id) {
        personService.delete(id);
        return ResponseEntity.ok("Person deleted!");
    }
}
