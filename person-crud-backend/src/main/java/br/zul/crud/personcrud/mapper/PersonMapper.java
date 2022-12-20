package br.zul.crud.personcrud.mapper;

import java.util.List;

import br.zul.crud.personcrud.dto.PersonDto;
import br.zul.crud.personcrud.entity.Person;
import br.zul.crud.personcrud.model.request.PersonCreateRequestBody;

public interface PersonMapper {

  Person from(PersonCreateRequestBody requestBody);

  PersonDto toResponseBody(Person person);

  List<PersonDto> toResponseBody(List<Person> person);

}
