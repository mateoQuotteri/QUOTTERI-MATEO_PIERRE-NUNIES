package com.backend.demo.service.impl;

import static org.junit.jupiter.api.Assertions.*;

import com.backend.demo.dto.entrada.DomicilioEntradaDto;
import com.backend.demo.dto.entrada.PacienteEntradaDto;
import com.backend.demo.dto.salida.PacienteSalidaDto;
import com.backend.demo.entity.Paciente;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class PacienteServicioTest {
    @Autowired
    private PacienteServicio pacienteServicio;


    @Test
    @Order(1)
    void deberiaRegistrarseUnPacienteDeNombreJuan_yRetornarSuId() {
        //arrange
        PacienteEntradaDto pacienteEntradaDto = new PacienteEntradaDto("Juan", "Perez", 123456, LocalDate.of(2024, 12, 22), new DomicilioEntradaDto("Calle", 1234, "Localidad", "Provincia"));

        //act
        PacienteSalidaDto pacienteSalidaDto = pacienteServicio.ingresarPaciente(pacienteEntradaDto);

        //assert
        assertNotNull(pacienteSalidaDto);
        assertNotNull(pacienteSalidaDto.getId());
        assertEquals("Juan", pacienteSalidaDto.getNombre());

    }

    @Test
    @Order(2)
    void deberiaRetornarElPacienteConId1() {

        PacienteSalidaDto paci = pacienteServicio.buscarPaciente(Long.valueOf(1));

        //assert
        assertNotNull(paci);
        assertEquals("Juan", paci.getNombre());

    }


    @Test
    @Order(3)
    void deberiaEditarElPacienteConId1() {

        PacienteEntradaDto pacienteEntradaDto = new PacienteEntradaDto("Mate", "Perez", 123456, LocalDate.of(2024, 12, 22), new DomicilioEntradaDto("Calle", 1234, "Localidad", "Provincia"));

        PacienteSalidaDto pacienteActualizado = pacienteServicio.actualizarPaciente(pacienteEntradaDto, 1L);
        assertNotNull(pacienteActualizado);
        assertEquals("Mate", pacienteActualizado.getNombre());

    }




}