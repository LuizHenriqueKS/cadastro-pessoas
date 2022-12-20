package br.zul.crud.personcrud.mapper;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import br.zul.crud.personcrud.dto.PersonDto;
import br.zul.crud.personcrud.entity.Person;
import br.zul.crud.personcrud.model.request.PersonCreateRequestBody;

@Component
public class PersonMapperImpl implements PersonMapper {

  @Override
  public Person from(PersonCreateRequestBody requestBody) {
    return Person.builder()
        .firstName(requestBody.getFirstName())
        .lastName(requestBody.getLastName())
        .birthDate(requestBody.getBirthDate())
        .build();
  }

  @Override
  public PersonDto toResponseBody(Person person) {
    return PersonDto.builder()
        .id(person.getId())
        .firstName(person.getFirstName())
        .lastName(person.getLastName())
        .birthDate(person.getBirthDate())
        .build();
  }

  @Override
  public List<PersonDto> toResponseBody(List<Person> personList) {
    return personList.stream()
        .map(this::toResponseBody)
        .collect(Collectors.toList());
  }

}
