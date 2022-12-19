package br.zul.crud.personcrud.mapper;

import org.springframework.stereotype.Component;

import br.zul.crud.personcrud.entity.Person;
import br.zul.crud.personcrud.model.response.PersonCreateResponseBody;

@Component
public interface PersonMapper {

  Person from(PersonCreateResponseBody requestBody);

  PersonCreateResponseBody toResponseBody(Person person);

}
