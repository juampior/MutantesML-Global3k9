package com.example.MutantesML_Global3k9.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.regex.Pattern;

/**
 * Validador custom para secuencias de ADN.
 */
public class ValidDnaSequenceValidator implements ConstraintValidator<ValidDnaSequence, String[]> {

    private static final int MIN_SIZE = 4;
    private static final Pattern DNA_PATTERN = Pattern.compile("^[ATCG]+$");

    @Override
    public void initialize(ValidDnaSequence constraintAnnotation) {
        // No necesita inicialización
    }

    @Override
    public boolean isValid(String[] dna, ConstraintValidatorContext context) {
        // Null o vacío
        if (dna == null || dna.length == 0) {
            addViolation(context, "DNA array cannot be null or empty");
            return false;
        }

        final int n = dna.length;

        // Tamaño mínimo
        if (n < MIN_SIZE) {
            addViolation(context, "DNA matrix must be at least 4x4. Current size: " + n + "x" + n);
            return false;
        }

        // Validar cada fila
        for (int i = 0; i < n; i++) {
            String row = dna[i];

            // Fila null
            if (row == null) {
                addViolation(context, "Row " + i + " is null");
                return false;
            }

            // No es cuadrada
            if (row.length() != n) {
                addViolation(context,
                        "DNA matrix must be square NxN. Expected length: " + n + ", got: " + row.length() + " at row " + i);
                return false;
            }

            // Caracteres inválidos
            if (!DNA_PATTERN.matcher(row).matches()) {
                addViolation(context,
                        "Row " + i + " contains invalid characters. Only A, T, C, G are allowed. Row: " + row);
                return false;
            }
        }

        return true;
    }

    /**
     * Agrega mensaje de violación customizado
     */
    private void addViolation(ConstraintValidatorContext context, String message) {
        context.disableDefaultConstraintViolation();
        context.buildConstraintViolationWithTemplate(message)
                .addConstraintViolation();
    }
}
