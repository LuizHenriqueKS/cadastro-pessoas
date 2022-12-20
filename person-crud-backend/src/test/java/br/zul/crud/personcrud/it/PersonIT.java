package br.zul.crud.personcrud.it;

import java.time.LocalDate;
import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

import br.zul.crud.personcrud.dto.PersonDto;
import br.zul.crud.personcrud.entity.Person;
import br.zul.crud.personcrud.model.request.PersonCreateRequestBody;
import br.zul.crud.personcrud.repository.PersonRepository;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase
@ActiveProfiles("test")
public class PersonIT {

  @Autowired
  private TestRestTemplate testRestTemplate;

  @Autowired
  private PersonRepository personRepository;

  @Test
  public void create_ReturnsOnePerson_WhenSuccessful() throws Exception {
    PersonCreateRequestBody requestBody = PersonCreateRequestBody.builder()
        .firstName("Luiz")
        .lastName("Silva")
        .birthDate(LocalDate.of(1990, 2, 1))
        .build();

    ResponseEntity<PersonDto> response = testRestTemplate.postForEntity("/api/v1/person", requestBody,
        PersonDto.class);
    Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

    PersonDto responseBody = response.getBody();
    Assertions.assertThat(responseBody.getId()).isNotNull();
    Assertions.assertThat(responseBody.getFirstName()).isEqualTo(requestBody.getFirstName());
    Assertions.assertThat(responseBody.getLastName()).isEqualTo(requestBody.getLastName());
    Assertions.assertThat(responseBody.getBirthDate()).isEqualTo(requestBody.getBirthDate());

    Person person = personRepository.findById(responseBody.getId()).get();
    Assertions.assertThat(person.getId()).isEqualTo(responseBody.getId());
    Assertions.assertThat(person.getFirstName()).isEqualTo(responseBody.getFirstName());
    Assertions.assertThat(person.getLastName()).isEqualTo(responseBody.getLastName());
    Assertions.assertThat(person.getBirthDate()).isEqualTo(responseBody.getBirthDate());
  }

  @Test
  public void findAll_ReturnsList_WhenSuccessful() throws Exception {
    Person personEx1 = Person.builder()
        .firstName("Zul")
        .lastName("Root")
        .birthDate(LocalDate.of(1990, 10, 5))
        .build();
    personRepository.save(personEx1);

    ResponseEntity<List<PersonDto>> response = testRestTemplate.exchange("/api/v1/person",
        HttpMethod.GET, null,
        new ParameterizedTypeReference<List<PersonDto>>() {
        });

    List<PersonDto> responseBody = response.getBody();
    Assertions.assertThat(responseBody.size()).isEqualTo(1);
    PersonDto firstResult = responseBody.get(0);
    Assertions.assertThat(firstResult.getId()).isEqualTo(personEx1.getId());
    Assertions.assertThat(firstResult.getFirstName()).isEqualTo(personEx1.getFirstName());
    Assertions.assertThat(firstResult.getLastName()).isEqualTo(personEx1.getLastName());
    Assertions.assertThat(firstResult.getBirthDate()).isEqualTo(personEx1.getBirthDate());
  }

}
