package models;

import lombok.Data;

@Data
public class RegisterUserRequestModel {
  private String Email;
  private String Password;
  private String RememberMe;

}
