package com.backend.demo.dto.salida;

import java.time.LocalDate;

public class PacienteSalidaDto {
    private Long id;
    private String nombre;
    private String apellido;
    private int dni;
    private LocalDate fechaAlta;
    private DomicilioSalidaDto domicilioSalidaDto;

    public PacienteSalidaDto() {
    }

    public PacienteSalidaDto(Long id, String nombre, String apellido, int dni, LocalDate fechaAlta, DomicilioSalidaDto domicilioSalidaDto) {
        this.id = id;
        this.nombre = nombre;
        this.apellido = apellido;
        this.dni = dni;
        this.fechaAlta = fechaAlta;
        this.domicilioSalidaDto = domicilioSalidaDto;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public int getDni() {
        return dni;
    }

    public void setDni(int dni) {
        this.dni = dni;
    }

    public LocalDate getFechaAlta() {
        return fechaAlta;
    }

    public void setFechaAlta(LocalDate fechaAlta) {
        this.fechaAlta = fechaAlta;
    }

    public DomicilioSalidaDto getDomicilioSalidaDto() {
        return domicilioSalidaDto;
    }

    public void setDomicilioSalidaDto(DomicilioSalidaDto domicilioSalidaDto) {
        this.domicilioSalidaDto = domicilioSalidaDto;
    }
}