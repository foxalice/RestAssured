package models;

import lombok.Data;

@Data
public class UserBodyModel {
  private String email;
  private String password;
  private String name;
  private String job;
}

