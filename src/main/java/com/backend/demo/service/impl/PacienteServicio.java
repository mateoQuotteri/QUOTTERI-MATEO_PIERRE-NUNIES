package com.backend.demo.service.impl;

import com.backend.demo.dto.entrada.PacienteEntradaDto;
import com.backend.demo.dto.salida.OdontologoSalidaDto;
import com.backend.demo.dto.salida.PacienteSalidaDto;
import com.backend.demo.entity.Odontologo;
import com.backend.demo.entity.Paciente;
import com.backend.demo.exceptions.RecursoNoEncontradoExcepcion;
import com.backend.demo.repository.PacienteRepositorio;
import com.backend.demo.service.IPacienteServicio;
import com.backend.demo.utils.JsonPrinter;
import com.backend.demo.service.IPacienteServicio;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PacienteServicio implements IPacienteServicio {
    private  final Logger LOGGER = LoggerFactory.getLogger(PacienteServicio.class);


    private  final PacienteRepositorio pacienteRepositorio;

    private  final ModelMapper modelMapper;


    public PacienteServicio(PacienteRepositorio pacienteRepositorio, ModelMapper modelMapper) {
        this.pacienteRepositorio = pacienteRepositorio;
        this.modelMapper = modelMapper;
        configureMapping();
    }




    private void configureMapping() {
        modelMapper.typeMap(PacienteEntradaDto.class, Paciente.class)
                .addMappings(mapper -> mapper.map(PacienteEntradaDto::getDomicilioEntradaDto, Paciente::setDomicilio));


        modelMapper.typeMap(Paciente.class, PacienteSalidaDto.class)
                .addMappings(mapper -> mapper.map(Paciente::getDomicilio, PacienteSalidaDto::setDomicilioSalidaDto));

    }

    @Override
    public  PacienteSalidaDto ingresarPaciente(PacienteEntradaDto paciente) {
        LOGGER.info("PacienteEntradaDto : {}", JsonPrinter.toString(paciente));
        Paciente pacienteEntidad = modelMapper.map(paciente, Paciente.class);
        Paciente pacienteEntidaConId = pacienteRepositorio.save(pacienteEntidad);
        PacienteSalidaDto pacienteSalidaDto = modelMapper.map(pacienteEntidaConId, PacienteSalidaDto.class);
        LOGGER.info("PacienteSalidaDto : {}", JsonPrinter.toString(pacienteSalidaDto));
        return pacienteSalidaDto;
    }

    @Override
    public List<PacienteSalidaDto> listarTodosLosPaciente() {
        List<PacienteSalidaDto> pacientesSalidaDto = pacienteRepositorio.findAll()
                .stream()
                .map(paciente -> modelMapper.map(paciente, PacienteSalidaDto.class))
                .toList();


        LOGGER.info("Listado de los pacientes: {}", JsonPrinter.toString(pacientesSalidaDto));
        return pacientesSalidaDto;
    }

    @Override
    public PacienteSalidaDto buscarPaciente(Long id) {
        Paciente pacienteBuscado = pacienteRepositorio.findById(id).orElse(null);
        PacienteSalidaDto pacienteEncontrado = null;

        if (pacienteBuscado != null) {
            pacienteEncontrado = modelMapper.map(pacienteBuscado, PacienteSalidaDto.class);
            LOGGER.info("Paciente encontrado:  {}", JsonPrinter.toString(pacienteEncontrado));

        } else
            LOGGER.error("No se ha encontrado el paciente con id  {}", id);

        return pacienteEncontrado;
    }

    @Override
    public void eliminarPaciente(Long id) throws RecursoNoEncontradoExcepcion {
        if (buscarPaciente(id) != null){
            pacienteRepositorio.deleteById(id);
            LOGGER.warn("Se ha eliminado el paciente con id {}", id);
        } else {
            throw new RecursoNoEncontradoExcepcion("No existe registro de paciente con id " + id);
        }

    }

    @Override
    public PacienteSalidaDto actualizarPaciente(PacienteEntradaDto pacienteEntradaDto, Long id) {
        Optional<Paciente> pacienteActualizar = pacienteRepositorio.findById(id);

        if (pacienteActualizar.isPresent()) {
            Paciente pacienteExistente = pacienteActualizar.get();

            modelMapper.map(pacienteEntradaDto, pacienteExistente);

            Paciente pacienteActualizado = pacienteRepositorio.save(pacienteExistente);

            LOGGER.info("Paciente actualizado: {}", pacienteActualizado);

            return modelMapper.map(pacienteActualizado, PacienteSalidaDto.class);
        } else {
            LOGGER.info("No se encontró el paciente a actualizar con ID: {}", id);
            return null;
        }
    }


}
