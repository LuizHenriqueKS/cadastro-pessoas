package br.zul.crud.personcrud.dto;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PersonDto {

  private Long id;
  private String firstName;
  private String lastName;
  private LocalDate birthDate;

}
