package br.zul.crud.personcrud.model.request;

import java.time.LocalDate;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

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
  @Min(3)
  private String firstName;
  private String lastName;
  private LocalDate birthDate;

}
