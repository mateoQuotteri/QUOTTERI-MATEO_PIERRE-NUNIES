package com.backend.demo.service.impl;
import static org.junit.jupiter.api.Assertions.*;

import com.backend.demo.dto.entrada.DomicilioEntradaDto;
import com.backend.demo.dto.entrada.OdontologoEntradaDto;
import com.backend.demo.dto.entrada.PacienteEntradaDto;
import com.backend.demo.dto.salida.OdontologoSalidaDto;

import com.backend.demo.exceptions.RecursoNoEncontradoExcepcion;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;


import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.util.List;


@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class OdontologoServicioTest {

    @Autowired
    private OdontologoServicio odontologoService;

    @Test
    @Order(1)
    void deberiaRegistraseUnOdontologoDeNombreRodolfo_yRetornarSuId(){
        //arrange
        OdontologoEntradaDto odontologoEntradaDto = new OdontologoEntradaDto("123456", "Rodolfo", "Mesi");
        //act
        OdontologoSalidaDto odontologoSalidaDto = odontologoService.ingresarOdontologo(odontologoEntradaDto);

        //assert
        assertNotNull(odontologoSalidaDto);
        assertNotNull(odontologoSalidaDto.getId());
        assertEquals("Rodolfo", odontologoSalidaDto.getNombre());

    }

    @Test
    @Order(2)
    void deberiaEliminarseElPacienteConId1(){

        try{
            odontologoService.eliminarOdontologo(1L);
        }catch (Exception exception){
            exception.printStackTrace();
        }

        assertTrue(odontologoService.listarTodosLosOdonotologos().isEmpty());

    }

    @Test
    @Order(3)
    void deberiaDevolverUnaListaVaciaDeOdontologo() {
        List<OdontologoSalidaDto> odontologos = odontologoService.listarTodosLosOdonotologos();

        assertTrue(odontologos.isEmpty());
    }

}