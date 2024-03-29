package com.backend.demo.service.impl;

import static org.junit.jupiter.api.Assertions.*;
import com.backend.demo.service.impl.PacienteServicio;
import com.backend.demo.service.impl.OdontologoServicio;

import com.backend.demo.dto.entrada.DomicilioEntradaDto;
import com.backend.demo.dto.entrada.OdontologoEntradaDto;
import com.backend.demo.dto.entrada.PacienteEntradaDto;
import com.backend.demo.dto.entrada.TurnoEntradaDto;
import com.backend.demo.dto.salida.PacienteSalidaDto;
import com.backend.demo.dto.salida.TurnoSalidaDto;
import com.backend.demo.entity.Domicilio;
import com.backend.demo.entity.Odontologo;
import com.backend.demo.entity.Paciente;
import com.backend.demo.entity.Turno;
import com.backend.demo.exceptions.RecursoNoEncontradoExcepcion;
import com.backend.demo.exceptions.SolicitudIncorrectaExcepcion;
import com.sun.xml.bind.v2.runtime.output.DOMOutput;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class TurnoServicioTest {
    @Autowired
    private TurnoServicio turnoServicio;


    @Test
    @Order(1)
    void deberiaArrojarUnaExcepcionDelTipoSolicitudIncorrectaExcepcionAlIntentarRegistrarUnTurnoConOdontologos_Y_PacienteNoRegistrados() throws SolicitudIncorrectaExcepcion {

        LocalDateTime fechaHora = LocalDateTime.of(2024, 5, 28, 15, 30);
        TurnoEntradaDto turno = new TurnoEntradaDto(1L, 1L, fechaHora);


        SolicitudIncorrectaExcepcion excepcion = assertThrows(SolicitudIncorrectaExcepcion.class, () -> {
            turnoServicio.ingresarTurno(turno);
        });

        assertEquals("Ni el pac, ni el odon se encuentran en nuestra base de datos", excepcion.getMessage());

    }






}