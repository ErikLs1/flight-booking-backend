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

@Service
@AllArgsConstructor
public class PersonServiceImpl implements PersonService {

    private final PersonRepository personRepository;
    private final PersonMapper personMapper;

    /**
     * Creates a new Person entity from the given DTO and saves it to the database.
     *
     * @param dto The DTO containing person details.
     * @return The saved Person entity as DTO.
     */
    @Override
    public PersonDTO create(PersonDTO dto) {
        Person person = personMapper.toEntity(dto);
        Person saved = personRepository.save(person);
        return personMapper.toDTO(saved);
    }

    /**
     * Updates the existing Person entity by the given id.
     *
     * @param id The ID of the entity to update.
     * @param dto The DTO containing the updated data.
     * @return The update Person entity converted to a DTO.
     * @throws RuntimeException if the person with the given id does not exist.
     */
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

    /**
     * Retrieves a Person entity by its id.
     *
     * @param id The id of the person to retrieve.
     * @return The Person entity converted to DTO.
     * @throws RuntimeException if the person with the given id does not exist.
     */
    @Override
    public PersonDTO getById(Long id) {
        Person person = personRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Person not found with id: " + id));
        return personMapper.toDTO(person);
    }

    /**
     * Retrieves a list of all Person entities.
     *
     * @return A list of all Person entities as DTOs.
     */
    @Override
    public List<PersonDTO> getAll() {
        return personRepository.findAll().stream()
                .map(personMapper::toDTO)
                .collect(Collectors.toList());
    }

    /**
     * Deletes Person entity by its id.
     *
     * @param id The id of the person to delete.
     * @throws RuntimeException if person with the given id does not exist.
     */
    @Override
    public void delete(Long id) {
        if (!personRepository.existsById(id)) {
            throw new RuntimeException("Person not found with id: " + id);
        }

        personRepository.deleteById(id);
    }
}
