package com.gustavo.tienda_de_chaquetas.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import org.springframework.data.annotation.Id;

import javax.persis


//@Entity
@Data
@NoArgsConstructor
public class Persona {

    @Id
    private Long id;
    private String primer_nombre;
    private String segundo_nombre;
    private String primer_apellido;
    private String segundo_apellido;
    private String correo;
    private String telefono;

    public Persona(long id, String primer_nombre, String segundo_nombre,
                   String primer_apellido, String segundo_apellido,
                   String correo, String telefono){
        this.primer_nombre = primer_nombre;
        this.segundo_nombre = segundo_nombre;
        this.primer_apellido = primer_apellido;
        this.segundo_apellido = segundo_apellido;
        this.correo = correo;
        this.telefono = telefono;
    }
}
