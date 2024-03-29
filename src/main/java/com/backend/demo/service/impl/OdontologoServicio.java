package com.backend.demo.service.impl;


import com.backend.demo.dto.entrada.OdontologoEntradaDto;
import com.backend.demo.dto.salida.OdontologoSalidaDto;
import com.backend.demo.entity.Odontologo;
import com.backend.demo.repository.OdontologoRepositorio;
import com.backend.demo.service.IOdontologoServicio;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service

public class OdontologoServicio implements IOdontologoServicio {
    private final Logger LOGGER = LoggerFactory.getLogger(OdontologoServicio.class);
    private final OdontologoRepositorio odontologoRepositorio;
    private final ModelMapper modelMapper;



    public OdontologoServicio(OdontologoRepositorio odontologoRepositorio, ModelMapper modelMapper) {
        this.odontologoRepositorio = odontologoRepositorio;
        this.modelMapper = modelMapper;
    }


    @Override
    public OdontologoSalidaDto ingresarOdontologo(OdontologoEntradaDto odontologo) {
        Odontologo odGuardado = odontologoRepositorio.save(modelMapper.map(odontologo, Odontologo.class));
        OdontologoSalidaDto odontologoSalidaDto = modelMapper.map(odGuardado, OdontologoSalidaDto.class);
        LOGGER.info("Odontologo insertado: {}", odontologoSalidaDto);
        return odontologoSalidaDto;
    }



    @Override
    public List<OdontologoSalidaDto> listarTodosLosOdonotologos() {
        List<OdontologoSalidaDto> odontologos = odontologoRepositorio.findAll().stream().map(o -> modelMapper.map(o, OdontologoSalidaDto.class)).toList();
        LOGGER.info("Listado de los odontologos: {}", odontologos);

        return odontologos;
    }

    @Override
    public OdontologoSalidaDto buscarOdontologo(Long id) {
        Odontologo odontologoBuscado = odontologoRepositorio.findById(id).orElse(null);

        OdontologoSalidaDto odontologoSalidaDto = null;
        if (odontologoBuscado != null) {
            odontologoSalidaDto = modelMapper.map(odontologoBuscado, OdontologoSalidaDto.class);
            LOGGER.info("Odontologo encontrado x id: {}", odontologoSalidaDto);
        } else LOGGER.error("El id no se encuentra registrado en la base de datos, no existe tal odontologo");

        return odontologoSalidaDto;
    }

    @Override
    public void eliminarOdontologo(Long id) {
        if (buscarOdontologo(id) != null) {
            odontologoRepositorio.deleteById(id);
            LOGGER.warn("Se ha eliminado el odontologo cuyo id era: {}", id);
        } else {
            LOGGER.error("No existe el odontologo con id {}", id);

        }
    }

    @Override
    public OdontologoSalidaDto actualizarOdontologo(OdontologoEntradaDto odontologoEntradaDto, Long id)
    {
        Optional<Odontologo> odontologoAActualizar = odontologoRepositorio.findById(id);

        if (odontologoAActualizar.isPresent()) {
            Odontologo odontologoExistente = odontologoAActualizar.get();


            odontologoExistente.setApellido(odontologoEntradaDto.getApellido());
            odontologoExistente.setNombre(odontologoEntradaDto.getNombre());
            odontologoExistente.setMatricula(odontologoEntradaDto.getMatricula());

            // Guardamos el odontólogo actualizado en la base de datos
            Odontologo odontologoActualizado = odontologoRepositorio.save(odontologoExistente);

            LOGGER.info("Odontologo actualizado: {}", odontologoActualizado);

            return modelMapper.map(odontologoActualizado, OdontologoSalidaDto.class);
        } else {
            LOGGER.info("No se encontró el odontologo a actualizar con ID: {}", id);
            return null;

        }
    }
    }
