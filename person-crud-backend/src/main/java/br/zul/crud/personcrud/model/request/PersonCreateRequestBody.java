package br.zul.crud.personcrud.model.request;

import java.time.LocalDate;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PersonCreateRequestBody {

  @NotNull
  @NotEmpty
  @Size(min = 3)
  private String firstName;
  private String lastName;
  private LocalDate birthDate;

}
