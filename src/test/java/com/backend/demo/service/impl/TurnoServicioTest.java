package com.backend.demo.service.impl;

import com.backend.demo.dto.entrada.DomicilioEntradaDto;
import com.backend.demo.dto.entrada.OdontologoEntradaDto;
import com.backend.demo.dto.entrada.PacienteEntradaDto;
import com.backend.demo.dto.entrada.TurnoEntradaDto;
import com.backend.demo.dto.salida.PacienteSalidaDto;
import com.backend.demo.dto.salida.TurnoSalidaDto;
import com.backend.demo.exceptions.RecursoNoEncontradoExcepcion;
import com.backend.demo.exceptions.SolicitudIncorrectaExcepcion;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class TurnoServicioTest {
    @Autowired
    private TurnoServicio turnoServicio;
    @Autowired
    private PacienteServicio pacienteServicio;
    @Autowired

    private OdontologoServicio odontologoServicio;


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

    @Test
    @Order(2)
    void debeRegistrarUnTurnoCorrectamenteConUnPaciente_Y_OdontologoQueInsertamosEnEsteMismoMetodoComoParametros() throws SolicitudIncorrectaExcepcion, RecursoNoEncontradoExcepcion {

        PacienteEntradaDto pacienteEntradaDto = new PacienteEntradaDto("Juan", "Perez", 123456, LocalDate.of(2024, 12, 22), new DomicilioEntradaDto("Calle", 1234, "Localidad", "Provincia"));
        OdontologoEntradaDto odontologoEntradaDto = new OdontologoEntradaDto("kskj3", "jorge" , "martinez");

        pacienteServicio.ingresarPaciente(pacienteEntradaDto);
        odontologoServicio.ingresarOdontologo(odontologoEntradaDto);

        LocalDateTime fechaHora = LocalDateTime.of(2024, 5, 28, 15, 30);
        TurnoEntradaDto turno = new TurnoEntradaDto(1L, 1L, fechaHora);


       TurnoSalidaDto turnoRegistro = turnoServicio.ingresarTurno(turno);

        assertNotNull(turnoRegistro);
        assertEquals("kskj3", turnoRegistro.getOdontologoSalidaDto().getMatricula());

    }


    @Test
    @Order(3)
    void debeBuscarElTurnoConId1Correctamente() throws SolicitudIncorrectaExcepcion, RecursoNoEncontradoExcepcion {
        TurnoSalidaDto turno = turnoServicio.buscarTurno(1L);

        //assert
        assertNotNull(turno);
        assertEquals(1, turno.getId());
        assertEquals("Perez", turno.getPacienteSalidaDto().getApellido());

    }
}
