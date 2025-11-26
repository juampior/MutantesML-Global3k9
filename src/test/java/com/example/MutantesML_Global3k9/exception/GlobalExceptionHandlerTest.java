package com.example.MutantesML_Global3k9.exception;

import com.example.MutantesML_Global3k9.dto.ErrorResponse;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;

class GlobalExceptionHandlerTest {

    private final GlobalExceptionHandler handler = new GlobalExceptionHandler();

    @Test
    void testHandleInvalidDna() {
        IllegalArgumentException ex = new IllegalArgumentException("Secuencia de ADN no válida");

        ResponseEntity<ErrorResponse> response = handler.handleInvalidDna(ex);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Secuencia de ADN no válida", response.getBody().getMessage());
    }
}
