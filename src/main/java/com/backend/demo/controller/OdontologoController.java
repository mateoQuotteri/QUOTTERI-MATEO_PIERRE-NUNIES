package com.backend.demo.controller;
import com.backend.demo.dto.entrada.OdontologoEntradaDto;
import com.backend.demo.dto.salida.OdontologoSalidaDto;
import com.backend.demo.service.impl.OdontologoServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/odontologos")
public class OdontologoController {

    private final OdontologoServicio odontologoServicio;

    public OdontologoController(OdontologoServicio odontologoServicio) {
        this.odontologoServicio = odontologoServicio;
    }

    @PostMapping("registrar")

    public ResponseEntity<OdontologoSalidaDto> registrarOdontologo(@Valid @RequestBody OdontologoEntradaDto odontologo) {
        return new ResponseEntity<>(odontologoServicio.ingresarOdontologo(odontologo), HttpStatus.CREATED);
    }

    //PUT

    @PutMapping("actualizar/{id}")
    public ResponseEntity<OdontologoSalidaDto> actualizarOdontologo(@Valid @RequestBody OdontologoEntradaDto odontologo, @PathVariable Long id) {
        return new ResponseEntity<>(odontologoServicio.actualizarOdontologo(odontologo, id), HttpStatus.OK);
    }

    //GET

    @GetMapping("{id}")
    public ResponseEntity<OdontologoSalidaDto> obtenerOdontologoPorId(@PathVariable Long id) {
        return new ResponseEntity<>(odontologoServicio.buscarOdontologo(id), HttpStatus.OK);
    }


    @GetMapping()

    public ResponseEntity<List<OdontologoSalidaDto>> listarOdontologos() {
        return new ResponseEntity<>(odontologoServicio.listarTodosLosOdonotologos(), HttpStatus.OK);
    }

    //DELETE

    @DeleteMapping("eliminar/{id}")
    public ResponseEntity<?> eliminarOdontologo(@PathVariable Long id) {
        odontologoServicio.eliminarOdontologo(id);
        return new ResponseEntity<>("Odontologo eliminado", HttpStatus.NO_CONTENT);

    }


}