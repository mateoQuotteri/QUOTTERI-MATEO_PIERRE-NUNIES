package com.backend.demo.service.impl;


import com.backend.demo.dto.entrada.OdontologoEntradaDto;
import com.backend.demo.dto.entrada.TurnoEntradaDto;
import com.backend.demo.dto.salida.OdontologoSalidaDto;
import com.backend.demo.dto.salida.PacienteSalidaDto;
import com.backend.demo.dto.salida.TurnoSalidaDto;
import com.backend.demo.entity.Odontologo;
import com.backend.demo.entity.Paciente;
import com.backend.demo.entity.Turno;
import com.backend.demo.exceptions.RecursoNoEncontradoExcepcion;
import com.backend.demo.exceptions.SolicitudIncorrectaExcepcion;
import com.backend.demo.repository.TurnoRepositorio;
import com.backend.demo.service.ITurnoServicio;
import com.backend.demo.utils.JsonPrinter;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TurnoServicio implements ITurnoServicio {
    private final Logger LOGGER = LoggerFactory.getLogger(TurnoServicio.class);
    private final TurnoRepositorio turnoRepositorio;
    private final ModelMapper modelMapper;

    private final PacienteServicio pacienteServicio;
    private final OdontologoServicio odontologoServicio;

    public TurnoServicio(TurnoRepositorio turnoRepositorio, ModelMapper modelMapper, PacienteServicio pacienteServicio, OdontologoServicio odontologoServicio) {
        this.turnoRepositorio = turnoRepositorio;
        this.modelMapper = modelMapper;
        this.pacienteServicio = pacienteServicio;
        this.odontologoServicio = odontologoServicio;
    }
    private TurnoSalidaDto entidadADtoSal(Turno turno, PacienteSalidaDto pacienteSalidaDto, OdontologoSalidaDto odontologoSalidaDto){
        TurnoSalidaDto turnoSalidaDto = modelMapper.map(turno, TurnoSalidaDto.class);
        turnoSalidaDto.setPacienteSalidaDto(pacienteSalidaDto);
        turnoSalidaDto.setOdontologoSalidaDto(odontologoSalidaDto);
        return turnoSalidaDto;
    }
    @Override
    public TurnoSalidaDto ingresarTurno(TurnoEntradaDto turnoEntradaDto) throws SolicitudIncorrectaExcepcion, RecursoNoEncontradoExcepcion {
        TurnoSalidaDto turnoSalidaDto;
        PacienteSalidaDto paciente = pacienteServicio.buscarPaciente(turnoEntradaDto.getPacienteId());
        OdontologoSalidaDto odontologo = odontologoServicio.buscarOdontologo(turnoEntradaDto.getOdontologoId());

        String pacienteNoEnBBDD = "El paciente no se encuentra en la base de datos";
        String odontologoNoEnBDDD = "El odontologo no se encuentra en la base de datos";
        String ambosNulos = "Ni el pac, ni el odon se encuentran en nuestra base de datos";

        if(paciente == null || odontologo == null){
            if(paciente == null && odontologo == null){
                LOGGER.error(ambosNulos);
                throw new SolicitudIncorrectaExcepcion(ambosNulos);
            } else if (paciente == null) {
                LOGGER.error(pacienteNoEnBBDD);
                throw new SolicitudIncorrectaExcepcion(pacienteNoEnBBDD);
            } else {
                LOGGER.error(odontologoNoEnBDDD);
                throw new SolicitudIncorrectaExcepcion(odontologoNoEnBDDD);
            }

        } else {
            Turno turnoNuevo = turnoRepositorio.save(modelMapper.map(turnoEntradaDto, Turno.class));
            turnoSalidaDto = entidadADtoSal(turnoNuevo, paciente, odontologo);
            LOGGER.info("Turno registrado : {}", turnoSalidaDto);
        }


        return turnoSalidaDto;
    }

    @Override
    public List<TurnoSalidaDto> listarTodosLosTurnos() {
        List<TurnoSalidaDto> turnos = turnoRepositorio.findAll().
                stream().
                map(t -> modelMapper.
                        map(entidadADtoSal(t, modelMapper.map(t.getPaciente() , PacienteSalidaDto.class), modelMapper.map(t.getOdontologo(),  OdontologoSalidaDto.class)), TurnoSalidaDto.class)).toList();

        LOGGER.info("Listado de los turnos: {}", turnos);

        return turnos;
    }

    @Override
    public TurnoSalidaDto buscarTurno(Long id) {
        Turno turnoBuscado = turnoRepositorio.findById(id).orElse(null);
        TurnoSalidaDto turnoSalidaDto = null;
        if (turnoBuscado != null) {
            turnoSalidaDto = entidadADtoSal(turnoBuscado , modelMapper.map( turnoBuscado.getPaciente() , PacienteSalidaDto.class) , modelMapper.map( turnoBuscado.getOdontologo() ,  OdontologoSalidaDto.class));
            LOGGER.info("turno encontrado x id: {}", turnoSalidaDto);
        } else LOGGER.error("El id no se encuentra registrado en la base de datos, no existe tal turnoo");

        return turnoSalidaDto;
    }

    @Override
    public void eliminarTurno(Long id) {
        if (buscarTurno(id) != null) {
            turnoRepositorio.deleteById(id);
            LOGGER.warn("Se ha eliminado el turnop cuyo id era: {}", id);
        } else {
            LOGGER.error("No existe el turno con id {}", id);

        }

    }

    @Override
    public TurnoSalidaDto actualizarTurno(TurnoEntradaDto turnoEntradaDto, Long id) throws RecursoNoEncontradoExcepcion {
        TurnoSalidaDto turnoSalidaDto;
        Turno turnoExistente = turnoRepositorio.findById(id).orElse(null);
        PacienteSalidaDto paciente = pacienteServicio.buscarPaciente(turnoEntradaDto.getPacienteId());
        OdontologoSalidaDto odontologo = odontologoServicio.buscarOdontologo(turnoEntradaDto.getOdontologoId());

        String turnoNoExiste = "No se existe el turno con ID: " + id;
        String pacienteNoExiste = "No se existe el paciente con ID: " + turnoEntradaDto.getPacienteId();
        String odontologoNoExiste = "No se existe el odontólogo con ID: " + turnoEntradaDto.getOdontologoId();

        if(turnoExistente == null || paciente == null || odontologo == null){
            if(turnoExistente == null){
                LOGGER.error(turnoNoExiste);
                throw new RecursoNoEncontradoExcepcion(turnoNoExiste);
            } else if (paciente == null) {
                LOGGER.error(pacienteNoExiste);
                throw new RecursoNoEncontradoExcepcion(pacienteNoExiste);
            } else {
                LOGGER.error(odontologoNoExiste);
                throw new RecursoNoEncontradoExcepcion(odontologoNoExiste);
            }
        } else {
            turnoExistente.setPaciente(modelMapper.map(paciente, Paciente.class));
            turnoExistente.setOdontologo(modelMapper.map(odontologo, Odontologo.class));
            turnoExistente.setFechaHora(turnoEntradaDto.getFechaHora());
            Turno turnoActualizado = turnoRepositorio.save(turnoExistente);
            turnoSalidaDto = entidadADtoSalida(turnoActualizado);

            LOGGER.info("Turno actualizado con éxito: {}", JsonPrinter.toString(turnoActualizado));
        }
        return turnoSalidaDto;
    }


    private TurnoSalidaDto entidadADtoSalida(Turno turno){
        TurnoSalidaDto turnoSalidaDto = modelMapper.map(turno, TurnoSalidaDto.class);
        turnoSalidaDto.setPacienteSalidaDto(pacienteServicio.buscarPaciente(turno.getPaciente().getId()));
        turnoSalidaDto.setOdontologoSalidaDto(odontologoServicio.buscarOdontologo(turno.getOdontologo().getId()));
        return turnoSalidaDto;
    }

}