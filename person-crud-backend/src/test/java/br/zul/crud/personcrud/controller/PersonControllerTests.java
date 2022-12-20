package br.zul.crud.personcrud.controller;

import java.security.SecureRandom;
import java.time.LocalDate;
import java.util.List;
import java.util.Random;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.web.bind.MethodArgumentNotValidException;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.zul.crud.personcrud.dto.PersonDto;
import br.zul.crud.personcrud.entity.Person;
import br.zul.crud.personcrud.mapper.PersonMapperImpl;
import br.zul.crud.personcrud.model.request.PersonCreateRequestBody;
import br.zul.crud.personcrud.service.PersonService;

@WebMvcTest(PersonController.class)
@ExtendWith(SpringExtension.class)
public class PersonControllerTests {

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private ObjectMapper objectMapper;

  @MockBean
  private PersonService personService;

  @Spy
  private PersonMapperImpl personMapper;

  private Random random;

  private Person personEx1;

  @BeforeEach
  public void setUp() {
    random = new SecureRandom();

    personEx1 = Person.builder()
        .id(1L)
        .firstName("Luiz")
        .lastName("Silva")
        .birthDate(LocalDate.of(1990, 2, 1))
        .build();

    List<PersonDto> responseBody = personMapper.toResponseBody(List.of(personEx1));
    Mockito.when(personService.findAll()).thenReturn(responseBody);
    Mockito.when(personService.create(Mockito.any())).thenAnswer(a -> {
      PersonCreateRequestBody reqBody = (PersonCreateRequestBody) a.getArgument(0);
      return PersonDto.builder()
          .id(random.nextLong())
          .firstName(reqBody.getFirstName())
          .lastName(reqBody.getLastName())
          .birthDate(reqBody.getBirthDate())
          .build();
    });
  }

  @Test
  public void deleteById_ReturnsNonContent_WhenSuccessful() throws Exception {
    RequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/api/v1/person/1");
    mockMvc.perform(requestBuilder)
        .andExpect(MockMvcResultMatchers.status().is(HttpStatus.NO_CONTENT.value()));
    Mockito.verify(personService).deleteById(1L);
  }

  @Test
  public void findAll_ReturnsList_WhenSuccessful() throws Exception {
    RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/v1/person");
    MockHttpServletResponse response = mockMvc.perform(requestBuilder).andReturn().getResponse();
    Assertions.assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());

    List<PersonDto> responseBody = objectMapper.readValue(response.getContentAsString(),
        new TypeReference<List<PersonDto>>() {
        });

    Assertions.assertThat(responseBody.size()).isEqualTo(1);

    PersonDto firstResult = responseBody.get(0);

    Assertions.assertThat(firstResult.getId()).isEqualTo(personEx1.getId());
    Assertions.assertThat(firstResult.getFirstName()).isEqualTo(personEx1.getFirstName());
    Assertions.assertThat(firstResult.getLastName()).isEqualTo(personEx1.getLastName());
    Assertions.assertThat(firstResult.getBirthDate()).isEqualTo(personEx1.getBirthDate());
  }

  @Test
  public void create_ReturnsOnePerson_WhenSuccessful() throws Exception {
    PersonCreateRequestBody requestBody = PersonCreateRequestBody.builder()
        .firstName("Luiz")
        .lastName("Silva")
        .birthDate(LocalDate.of(1990, 2, 1))
        .build();
    RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/api/v1/person")
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(requestBody));
    MockHttpServletResponse response = mockMvc.perform(requestBuilder).andReturn().getResponse();
    Assertions.assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
    PersonDto responseBody = objectMapper.readValue(response.getContentAsString(),
        PersonDto.class);
    Assertions.assertThat(responseBody.getId()).isNotNull();
    Assertions.assertThat(responseBody.getFirstName()).isEqualTo(requestBody.getFirstName());
    Assertions.assertThat(responseBody.getLastName()).isEqualTo(requestBody.getLastName());
    Assertions.assertThat(responseBody.getBirthDate()).isEqualTo(requestBody.getBirthDate());
  }

  @Test
  public void create_ReturnsBadRequest_WhenFirstNameHasLessThanThreeChars() throws Exception {
    PersonCreateRequestBody requestBody = PersonCreateRequestBody.builder()
        .firstName("Luiz")
        .lastName("Silva")
        .birthDate(LocalDate.of(1990, 2, 1))
        .build();

    RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/api/v1/person")
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(requestBody));
    MockHttpServletResponse response = mockMvc.perform(requestBuilder).andReturn().getResponse();
    Assertions.assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());

    requestBody.setFirstName("Ab");

    requestBuilder = MockMvcRequestBuilders.post("/api/v1/person")
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(requestBody));

    MethodArgumentNotValidException exception = (MethodArgumentNotValidException) mockMvc
        .perform(requestBuilder)
        .andExpect(MockMvcResultMatchers.status().isBadRequest())
        .andReturn()
        .getResolvedException();

    Assertions.assertThat(exception.getFieldError().getField()).isEqualTo("firstName");
  }

  @Test
  public void create_ReturnsBadRequest_WhenFirstNameIsNull() throws Exception {
    PersonCreateRequestBody requestBody = PersonCreateRequestBody.builder()
        .firstName("Luiz")
        .lastName("Silva")
        .birthDate(LocalDate.of(1990, 2, 1))
        .build();

    RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/api/v1/person")
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(requestBody));
    MockHttpServletResponse response = mockMvc.perform(requestBuilder).andReturn().getResponse();
    Assertions.assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());

    requestBody.setFirstName(null);

    requestBuilder = MockMvcRequestBuilders.post("/api/v1/person")
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(requestBody));

    MethodArgumentNotValidException exception = (MethodArgumentNotValidException) mockMvc
        .perform(requestBuilder)
        .andExpect(MockMvcResultMatchers.status().isBadRequest())
        .andReturn()
        .getResolvedException();

    Assertions.assertThat(exception.getFieldError().getField()).isEqualTo("firstName");
  }

}
