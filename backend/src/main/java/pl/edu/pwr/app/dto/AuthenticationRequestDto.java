package pl.edu.pwr.app.dto;

import lombok.Data;

@Data
public class AuthenticationRequestDto {
    private String email;
    private String password;
}
