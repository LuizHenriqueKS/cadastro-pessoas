package br.zul.crud.personcrud.service;

import java.security.SecureRandom;
import java.time.LocalDate;
import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import br.zul.crud.personcrud.dto.PersonDto;
import br.zul.crud.personcrud.entity.Person;
import br.zul.crud.personcrud.mapper.PersonMapperImpl;
import br.zul.crud.personcrud.model.request.PersonCreateRequestBody;
import br.zul.crud.personcrud.repository.PersonRepository;

@ExtendWith(SpringExtension.class)
public class PersonServiceTests {

  @InjectMocks
  private PersonService personService;

  @Mock
  private PersonRepository personRepository;

  @Spy
  private PersonMapperImpl personMapper;

  @Test
  public void create_ReturnsOnePerson_WhenSuccessful() {
    PersonCreateRequestBody requestBody = PersonCreateRequestBody.builder()
        .firstName("Luiz")
        .lastName("Silva")
        .birthDate(LocalDate.of(1990, 2, 1))
        .build();
    Mockito.when(personRepository.save(Mockito.any())).thenAnswer(a -> {
      Person person = (Person) a.getArgument(0);
      person.setId(new SecureRandom().nextLong());
      return person;
    });
    PersonDto responseBody = personService.create(requestBody);
    Assertions.assertThat(responseBody.getId()).isNotNull();
    Assertions.assertThat(responseBody.getFirstName()).isEqualTo(requestBody.getFirstName());
    Assertions.assertThat(responseBody.getLastName()).isEqualTo(requestBody.getLastName());
    Assertions.assertThat(responseBody.getBirthDate()).isEqualTo(requestBody.getBirthDate());
  }

  @Test
  public void findAll_ReturnsList_WhenSuccessful() {
    Person person = Person.builder()
        .id(1L)
        .firstName("Luiz")
        .lastName("Silva")
        .birthDate(LocalDate.of(1990, 2, 1))
        .build();
    Mockito.when(personRepository.findAll()).thenReturn(List.of(person));
    List<PersonDto> responseBody = personService.findAll();
    Assertions.assertThat(responseBody.size()).isEqualTo(1);
    PersonDto firstResult = responseBody.get(0);
    Assertions.assertThat(firstResult.getId()).isEqualTo(person.getId());
    Assertions.assertThat(firstResult.getFirstName()).isEqualTo(person.getFirstName());
    Assertions.assertThat(firstResult.getLastName()).isEqualTo(person.getLastName());
    Assertions.assertThat(firstResult.getBirthDate()).isEqualTo(person.getBirthDate());
  }

}
