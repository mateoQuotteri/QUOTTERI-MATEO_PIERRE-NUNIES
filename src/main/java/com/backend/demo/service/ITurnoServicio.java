package com.backend.demo.service;

import com.backend.demo.dto.entrada.OdontologoEntradaDto;
import com.backend.demo.dto.entrada.TurnoEntradaDto;
import com.backend.demo.dto.salida.OdontologoSalidaDto;
import com.backend.demo.dto.salida.TurnoSalidaDto;
import com.backend.demo.exceptions.RecursoNoEncontradoExcepcion;
import com.backend.demo.exceptions.SolicitudIncorrectaExcepcion;

import java.util.List;

public interface ITurnoServicio {
    TurnoSalidaDto ingresarTurno(TurnoEntradaDto turnoEntradaDto) throws SolicitudIncorrectaExcepcion, RecursoNoEncontradoExcepcion;

    List<TurnoSalidaDto> listarTodosLosTurnos();

    TurnoSalidaDto buscarTurno(Long id);

    void eliminarTurno(Long id);

    TurnoSalidaDto actualizarTurno(TurnoEntradaDto turnoEntradaDto, Long id) throws RecursoNoEncontradoExcepcion;


}