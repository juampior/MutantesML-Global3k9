package com.example.MutantesML_Global3k9.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

/**
 * Anotación custom para validar secuencias de ADN.
 *
 * Valida segun los siguientes criterios:
 * - Matriz cuadrada NxN
 * - Tamaño mínimo 4x4
 * - Solo caracteres A, T, C, G
 */
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = ValidDnaSequenceValidator.class)
@Documented
public @interface ValidDnaSequence {
    String message() default "Invalid DNA sequence: must be a square NxN matrix (minimum 4x4) with only A, T, C, G characters";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
