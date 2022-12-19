package br.zul.crud.personcrud.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.zul.crud.personcrud.entity.Person;
import br.zul.crud.personcrud.mapper.PersonMapper;
import br.zul.crud.personcrud.model.response.PersonCreateResponseBody;
import br.zul.crud.personcrud.repository.PersonRepository;

@Service
public class PersonService {

  @Autowired
  private PersonRepository personRepository;

  @Autowired
  private PersonMapper personMapper;

  public PersonCreateResponseBody create(PersonCreateResponseBody requestBody) {
    Person person = personMapper.from(requestBody);
    personRepository.save(person);
    return personMapper.toResponseBody(person);
  }

  public List<PersonCreateResponseBody> findAll() {
    return personRepository.findAll()
        .stream()
        .map(personMapper::toResponseBody)
        .collect(Collectors.toList());
  }

}
