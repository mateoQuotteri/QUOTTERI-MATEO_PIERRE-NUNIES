package com.backend.demo.dto.entrada;


import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class OdontologoEntradaDto {

    @NotNull(message = "La matricula no puede ser nula")
    @NotBlank(message = "Debe especificarse la matricula del profesional (odontologo)")
    @Size(min = 5, max = 50, message = "El campo debe tener mínimo 5 caracteres")
    private String matricula;

    @Size(min = 2, max = 50, message = "El nombre de odontólogo puede tener hasta 50 caracteres y como minimo 2")
    @NotNull(message = "El nombre del odontólogo no puede ser nulo")
    @NotBlank(message = "Debe especificarse el nombre del odon.")
    private String nombre;

    @Size(min = 2, max = 50, message = "El apellido de odontólogo puede tener hasta 50 caracteres y como minimo 2")
    @NotNull(message = "El apellido de odontólogo no puede ser nulo")
    @NotBlank(message = "El apellido de odontólogo no puede ser vacio")
    private String apellido;

    public OdontologoEntradaDto() {
    }

    public OdontologoEntradaDto(String matricula, String nombre, String apellido) {
        this.matricula = matricula;
        this.nombre = nombre;
        this.apellido = apellido;
    }

    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }
}