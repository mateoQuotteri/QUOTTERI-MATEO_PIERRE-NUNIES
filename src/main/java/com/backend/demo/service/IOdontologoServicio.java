package com.backend.demo.service;
import com.backend.demo.dto.entrada.OdontologoEntradaDto;
import com.backend.demo.dto.entrada.PacienteEntradaDto;
import com.backend.demo.dto.salida.OdontologoSalidaDto;
import com.backend.demo.dto.salida.PacienteSalidaDto;
import com.backend.demo.entity.Odontologo;
import com.backend.demo.dto.salida.OdontologoSalidaDto;

import java.util.List;
public interface IOdontologoServicio {

    OdontologoSalidaDto ingresarOdontologo(OdontologoEntradaDto odontologo);
    List<OdontologoSalidaDto> listarTodosLosOdonotologos();
    OdontologoSalidaDto buscarOdontologo(Long id);
    void eliminarOdontologo(Long id);
    OdontologoSalidaDto actualizarOdontologo(OdontologoEntradaDto odontologoEntradaDto, Long id);
}