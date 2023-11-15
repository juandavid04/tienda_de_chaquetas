package com.gustavo.tienda_de_chaquetas.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

import lombok.Data;

@Data
public class LoginDto {
    @NotEmpty(message = "El correo no puede estar vacío")
    @Email(message = "Correo electrónico no válido")
    private String correo;
    private String password;
}
