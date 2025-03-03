package com.demo.flight_booking.mapper;

import com.demo.flight_booking.dto.PersonDTO;
import com.demo.flight_booking.model.Person;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PersonMapper {
    Person toEntity(PersonDTO personDTO);
    PersonDTO toDTO(Person person);
}
