package br.zul.crud.personcrud.mapper;

import br.zul.crud.personcrud.entity.Person;
import br.zul.crud.personcrud.model.response.PersonCreateResponseBody;

public class PersonMapperImpl implements PersonMapper {

  @Override
  public Person from(PersonCreateResponseBody requestBody) {
    return Person.builder()
        .firstName(requestBody.getFirstName())
        .lastName(requestBody.getLastName())
        .birthDate(requestBody.getBirthDate())
        .build();
  }

  @Override
  public PersonCreateResponseBody toResponseBody(Person person) {
    return PersonCreateResponseBody.builder()
        .id(person.getId())
        .firstName(person.getFirstName())
        .lastName(person.getLastName())
        .birthDate(person.getBirthDate())
        .build();
  }

}
