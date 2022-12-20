package br.zul.crud.personcrud.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.zul.crud.personcrud.dto.PersonDto;
import br.zul.crud.personcrud.entity.Person;
import br.zul.crud.personcrud.mapper.PersonMapper;
import br.zul.crud.personcrud.model.request.PersonCreateRequestBody;
import br.zul.crud.personcrud.repository.PersonRepository;

@Service
public class PersonService {

  @Autowired
  private PersonRepository personRepository;

  @Autowired
  private PersonMapper personMapper;

  public PersonDto create(PersonCreateRequestBody requestBody) {
    Person person = personMapper.from(requestBody);
    personRepository.save(person);
    return personMapper.toResponseBody(person);
  }

  public List<PersonDto> findAll() {
    List<Person> result = personRepository.findAll();
    return personMapper.toResponseBody(result);
  }

}
