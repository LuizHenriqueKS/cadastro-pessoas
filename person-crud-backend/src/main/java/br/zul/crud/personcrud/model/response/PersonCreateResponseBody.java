package br.zul.crud.personcrud.model.response;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PersonCreateResponseBody {

  private Long id;
  private String firstName;
  private String lastName;
  private LocalDate birthDate;

}
