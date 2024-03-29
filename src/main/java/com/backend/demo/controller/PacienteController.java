package com.backend.demo.controller;

import com.backend.demo.dto.entrada.OdontologoEntradaDto;
import com.backend.demo.dto.entrada.PacienteEntradaDto;
import com.backend.demo.dto.salida.OdontologoSalidaDto;
import com.backend.demo.dto.salida.PacienteSalidaDto;
import com.backend.demo.exceptions.RecursoNoEncontradoExcepcion;
import com.backend.demo.service.IPacienteServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/pacientes")
public class PacienteController {

    private IPacienteServicio pacienteServicio;

    public PacienteController(IPacienteServicio pacienteServicio) {
        this.pacienteServicio = pacienteServicio;
    }

    //POST
    @PostMapping("/ingresar")
    public ResponseEntity<PacienteSalidaDto> registrarPaciente(@RequestBody @Valid PacienteEntradaDto paciente) {
        return new ResponseEntity<>(pacienteServicio.ingresarPaciente(paciente), HttpStatus.CREATED);
    }

    //GET
    @GetMapping()
    public ResponseEntity<List<PacienteSalidaDto>> listarPacientes() {
        return new ResponseEntity<>(pacienteServicio.listarTodosLosPaciente(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PacienteSalidaDto> buscarPacientePorId(@PathVariable Long id) {
        return new ResponseEntity<>(pacienteServicio.buscarPaciente(id), HttpStatus.OK);
    }




    //PUT
    @PutMapping("actualizar/{id}")
    public ResponseEntity<PacienteSalidaDto> actualizarPaciente(@Valid @RequestBody PacienteEntradaDto pac, @PathVariable Long id) {
        return new ResponseEntity<>(pacienteServicio.actualizarPaciente(pac, id), HttpStatus.OK);
    }

    //DELETE
    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<?> eliminarPaciente(@PathVariable Long id) throws RecursoNoEncontradoExcepcion {
        pacienteServicio.eliminarPaciente(id);
        return new ResponseEntity<>("pac eliminado", HttpStatus.NO_CONTENT);

    }


}