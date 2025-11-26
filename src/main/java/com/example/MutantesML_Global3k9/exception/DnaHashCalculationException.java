package com.example.MutantesML_Global3k9.exception;

/**
 * Excepción lanzada cuando falla el cálculo del hash SHA-256 del DNA.
 */
public class DnaHashCalculationException extends RuntimeException {

    public DnaHashCalculationException(String message) {
        super(message);
    }

    public DnaHashCalculationException(String message, Throwable cause) {
        super(message, cause);
    }

}