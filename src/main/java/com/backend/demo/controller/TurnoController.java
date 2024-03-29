package com.backend.demo.controller;


import com.backend.demo.dto.entrada.PacienteEntradaDto;
import com.backend.demo.dto.entrada.TurnoEntradaDto;
import com.backend.demo.dto.salida.PacienteSalidaDto;
import com.backend.demo.dto.salida.TurnoSalidaDto;
import com.backend.demo.exceptions.RecursoNoEncontradoExcepcion;
import com.backend.demo.exceptions.SolicitudIncorrectaExcepcion;
import com.backend.demo.service.ITurnoServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/turnos")
public class TurnoController {


    private ITurnoServicio turnoServicio;

    public TurnoController(ITurnoServicio turnoServicio) {
        this.turnoServicio = turnoServicio;
    }

    //POST
    @PostMapping("/registrar")
    public ResponseEntity<TurnoSalidaDto> registrarTruno(@RequestBody @Valid TurnoEntradaDto turnoEntradaDto) throws SolicitudIncorrectaExcepcion, RecursoNoEncontradoExcepcion {
        return new ResponseEntity<>(turnoServicio.ingresarTurno(turnoEntradaDto), HttpStatus.CREATED);
    }

    //GET
    @GetMapping()
    public ResponseEntity<List<TurnoSalidaDto>> listarTurnos() {
        return new ResponseEntity<>(turnoServicio.listarTodosLosTurnos(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TurnoSalidaDto> buscarTurnoPorId(@PathVariable Long id) {
        return new ResponseEntity<>(turnoServicio.buscarTurno(id), HttpStatus.OK);
    }

    //DELETE
    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<?> eliminarTurno(@PathVariable Long id) {
        turnoServicio.eliminarTurno(id);
        return new ResponseEntity<>("Turno eliminado", HttpStatus.NO_CONTENT);

    }
//PUT
    @PutMapping("/actualizar/{id}")
    public ResponseEntity<TurnoSalidaDto> actualizarTurno(@RequestBody @Valid TurnoEntradaDto turnoEntradaDTO, @PathVariable Long id) throws RecursoNoEncontradoExcepcion {
        return new ResponseEntity<>(turnoServicio.actualizarTurno(turnoEntradaDTO, id), HttpStatus.OK);        //  return new ResponseEntity<>(turnoServicio.actualizarTurno(turnoEntradaDTO,id), HttpStatus.OK);
    }

}