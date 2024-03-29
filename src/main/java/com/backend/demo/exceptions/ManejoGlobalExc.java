package com.backend.demo.exceptions;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class ManejoGlobalExc {

    @ExceptionHandler({RecursoNoEncontradoExcepcion.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Map<String, String> manejarResourceNotFound(RecursoNoEncontradoExcepcion recursoNoEncontradoExcepcion){
        Map<String, String> mensaje = new HashMap<>();
        mensaje.put("mensaje", "¡Recurso no encontrado!: " + recursoNoEncontradoExcepcion.getMessage());
        return mensaje;
    }







}