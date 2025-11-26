package com.example.MutantesML_Global3k9.exception;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DnaHashCalculationExceptionTest {

    @Test
    void testExceptionMessage() {
        DnaHashCalculationException ex = new DnaHashCalculationException("Fallo al calcular hash del ADN");
        assertEquals("Fallo al calcular hash del ADN", ex.getMessage());
    }
}
