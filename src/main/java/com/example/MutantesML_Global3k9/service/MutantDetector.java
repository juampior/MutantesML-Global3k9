package com.example.MutantesML_Global3k9.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Set;

/**
 * Servicio que detecta si un ADN es mutante.
 *
 * Un humano es mutante si tiene MÁS DE UNA secuencia de 4 letras iguales
 * en dirección horizontal, vertical o diagonal.
 *
 * OPTIMIZACIONES IMPLEMENTADAS:
 * 1. Early Termination - Retorna true apenas encuentra 2 secuencias
 * 2. Conversión a char[][] - Acceso O(1) más rápido que String.charAt()
 * 3. Boundary Checking - Solo busca donde hay espacio suficiente
 * 4. Comparaciones Directas - Sin loops adicionales
 * 5. Validación Set O(1) - Para caracteres válidos
 */
@Slf4j
@Service
public class MutantDetector {

    private static final int SEQUENCE_LENGTH = 4;
    private static final Set<Character> VALID_BASES = Set.of('A', 'T', 'C', 'G');

    /**
     * Determina si un ADN es mutante.
     *
     * @param dna Array de strings representando la matriz NxN del ADN
     * @return true si es mutante (2+ secuencias), false si no lo es o es inválido
     */
    public boolean isMutant(String[] dna) {
        // Validación inicial
        if (!isValidDna(dna)) {
            log.debug("DNA inválido detectado");
            return false;
        }

        final int n = dna.length;

        // OPTIMIZACIÓN 1: Conversión a char[][] para acceso O(1)
        char[][] matrix = convertToMatrix(dna, n);

        int sequenceCount = 0;

        // OPTIMIZACIÓN 2: Single Pass - Un solo recorrido
        for (int row = 0; row < n; row++) {
            for (int col = 0; col < n; col++) {

                // OPTIMIZACIÓN 3: Boundary Checking - Solo buscar donde hay espacio

                // Horizontal →
                if (col <= n - SEQUENCE_LENGTH) {
                    if (checkHorizontal(matrix, row, col)) {
                        sequenceCount++;
                        log.debug("Secuencia horizontal encontrada en ({}, {})", row, col);
                        // OPTIMIZACIÓN 4: Early Termination
                        if (sequenceCount > 1) {
                            log.info("Mutante detectado con {} secuencias", sequenceCount);
                            return true;
                        }
                    }
                }

                // Vertical ↓
                if (row <= n - SEQUENCE_LENGTH) {
                    if (checkVertical(matrix, row, col)) {
                        sequenceCount++;
                        log.debug("Secuencia vertical encontrada en ({}, {})", row, col);
                        if (sequenceCount > 1) {
                            log.info("Mutante detectado con {} secuencias", sequenceCount);
                            return true;
                        }
                    }
                }

                // Diagonal Descendente ↘
                if (row <= n - SEQUENCE_LENGTH && col <= n - SEQUENCE_LENGTH) {
                    if (checkDiagonalDescending(matrix, row, col)) {
                        sequenceCount++;
                        log.debug("Secuencia diagonal descendente encontrada en ({}, {})", row, col);
                        if (sequenceCount > 1) {
                            log.info("Mutante detectado con {} secuencias", sequenceCount);
                            return true;
                        }
                    }
                }

                // Diagonal Ascendente ↗
                if (row >= SEQUENCE_LENGTH - 1 && col <= n - SEQUENCE_LENGTH) {
                    if (checkDiagonalAscending(matrix, row, col)) {
                        sequenceCount++;
                        log.debug("Secuencia diagonal ascendente encontrada en ({}, {})", row, col);
                        if (sequenceCount > 1) {
                            log.info("Mutante detectado con {} secuencias", sequenceCount);
                            return true;
                        }
                    }
                }
            }
        }

        log.info("DNA analizado: {} secuencia(s) encontrada(s) - {}",
                sequenceCount, sequenceCount > 1 ? "MUTANTE" : "HUMANO");
        return false;
    }

    /**
     * OPTIMIZACIÓN 5: Comparación directa sin loops
     * Verifica secuencia horizontal desde la posición dada
     */
    private boolean checkHorizontal(char[][] matrix, int row, int col) {
        final char base = matrix[row][col];
        return matrix[row][col + 1] == base &&
                matrix[row][col + 2] == base &&
                matrix[row][col + 3] == base;
    }

    /**
     * Verifica secuencia vertical desde la posición dada
     */
    private boolean checkVertical(char[][] matrix, int row, int col) {
        final char base = matrix[row][col];
        return matrix[row + 1][col] == base &&
                matrix[row + 2][col] == base &&
                matrix[row + 3][col] == base;
    }

    /**
     * Verifica secuencia diagonal descendente ↘ desde la posición dada
     */
    private boolean checkDiagonalDescending(char[][] matrix, int row, int col) {
        final char base = matrix[row][col];
        return matrix[row + 1][col + 1] == base &&
                matrix[row + 2][col + 2] == base &&
                matrix[row + 3][col + 3] == base;
    }

    /**
     * Verifica secuencia diagonal ascendente ↗ desde la posición dada
     */
    private boolean checkDiagonalAscending(char[][] matrix, int row, int col) {
        final char base = matrix[row][col];
        return matrix[row - 1][col + 1] == base &&
                matrix[row - 2][col + 2] == base &&
                matrix[row - 3][col + 3] == base;
    }

    /**
     * Convierte String[] a char[][] para acceso más rápido
     */
    private char[][] convertToMatrix(String[] dna, int n) {
        char[][] matrix = new char[n][];
        for (int i = 0; i < n; i++) {
            matrix[i] = dna[i].toCharArray();
        }
        return matrix;
    }

    /**
     * Valida que el DNA sea correcto:
     * - No null ni vacío
     * - Matriz cuadrada NxN
     * - Tamaño mínimo 4x4
     * - Solo caracteres A, T, C, G
     */
    private boolean isValidDna(String[] dna) {
        // Null o vacío
        if (dna == null || dna.length == 0) {
            return false;
        }

        final int n = dna.length;

        // Tamaño mínimo 4x4
        if (n < SEQUENCE_LENGTH) {
            return false;
        }

        // Verificar cada fila
        for (String row : dna) {
            // Fila null
            if (row == null) {
                return false;
            }

            // No es cuadrada (row.length != n)
            if (row.length() != n) {
                return false;
            }

            // Caracteres inválidos
            for (char c : row.toCharArray()) {
                if (!VALID_BASES.contains(c)) {
                    return false;
                }
            }
        }

        return true;
    }
}