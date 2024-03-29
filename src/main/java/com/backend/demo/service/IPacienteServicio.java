package com.backend.demo.service;

import com.backend.demo.dto.entrada.PacienteEntradaDto;
import com.backend.demo.dto.salida.PacienteSalidaDto;
import com.backend.demo.exceptions.RecursoNoEncontradoExcepcion;

import java.util.List;

public interface IPacienteServicio {
    PacienteSalidaDto ingresarPaciente(PacienteEntradaDto paciente);

    List<PacienteSalidaDto> listarTodosLosPaciente();
    PacienteSalidaDto buscarPaciente(Long id);
    void eliminarPaciente(Long id) throws RecursoNoEncontradoExcepcion;
    PacienteSalidaDto actualizarPaciente(PacienteEntradaDto pacienteEntradaDto, Long id);
}