package com.agiletv.user.infrastructure.rest.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Getter
@Builder
@AllArgsConstructor
public class UpdateUserRequest {
  @NotBlank(message = "name.is.empty")
  private final String name;
  
  @NotBlank(message = "first.surname.is.empty")
  private final String firstSurname;
  
  @NotBlank(message = "email.is.empty")
  @Email(message = "email.is.not.valid")
  private final String email;
  private final String gender;
  private final String pictureUrl;
}
