package com.demo.flight_booking.mapper;

import com.demo.flight_booking.dto.PersonDTO;
import com.demo.flight_booking.model.Person;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface PersonMapper {
    PersonMapper INSTANCE = Mappers.getMapper(PersonMapper.class);

    Person toEntity(PersonDTO personDTO);
    PersonDTO toDTO(Person person);
}
