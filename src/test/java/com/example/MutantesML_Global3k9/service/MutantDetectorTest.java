package com.example.MutantesML_Global3k9.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Tests unitarios para MutantDetector.
 *
 * Cubre:
 * - Casos mutantes (horizontal, vertical, diagonal)
 * - Casos humanos (0-1 secuencias)
 * - Validaciones (null, empty, non-square, invalid chars)
 * - Edge cases
 *
 * OBJETIVO: 17+ tests, >80% cobertura
 */
@DisplayName("MutantDetector - Tests Unitarios")
class MutantDetectorTest {

    private MutantDetector mutantDetector;

    @BeforeEach
    void setUp() {
        mutantDetector = new MutantDetector();
    }

    // ==================== CASOS MUTANTES ====================

    @Test
    @DisplayName("Debe detectar mutante con secuencias horizontal y diagonal")
    void testMutantWithHorizontalAndDiagonalSequences() {
        String[] dna = {
                "ATGCGA",
                "CAGTGC",
                "TTATGT",
                "AGAAGG",
                "CCCCTA",  // Horizontal: CCCC
                "TCACTG"
        };
        assertTrue(mutantDetector.isMutant(dna));
    }

    @Test
    @DisplayName("Debe detectar mutante con secuencias verticales")
    void testMutantWithVerticalSequences() {
        String[] dna = {
                "CTGCGA",
                "CTGTGC",
                "CTATGT",
                "CGAAGG",
                "ACCCTA",
                "GCACTG"
        };
        assertTrue(mutantDetector.isMutant(dna));
    }

    @Test
    @DisplayName("Debe detectar mutante con múltiples secuencias horizontales")
    void testMutantWithMultipleHorizontalSequences() {
        String[] dna = {
                "TTTTGA",
                "CAGTGC",
                "GGGGGT",
                "AGAACC",
                "CACCTA",
                "TCACTG"
        };
        assertTrue(mutantDetector.isMutant(dna));
    }

    @Test
    @DisplayName("Debe detectar mutante con diagonales ascendentes y descendentes")
    void testMutantWithBothDiagonals() {
        String[] dna = {
                "ATGCGA",
                "CAGTGC",
                "TTATGT",
                "AGAAGG",
                "CCCCTA",
                "TCACTG"
        };
        assertTrue(mutantDetector.isMutant(dna));
    }

    @Test
    @DisplayName("Debe detectar mutante en matriz grande 10x10")
    void testMutantWithLargeDna() {
        String[] dna = {
                "CTGCGATGCA",
                "CGGTGCATGC",
                "CCATGTATGC",
                "CGAAGGATGC",
                "ATCCTAATGC",
                "GCACTGATGC",
                "TTGCGATGCA",
                "AAGTGCATGC",
                "GTATGTATGC",
                "TGAAGGATGC"
        };
        assertTrue(mutantDetector.isMutant(dna));
    }

    @Test
    @DisplayName("Debe detectar mutante cuando toda la matriz es igual")
    void testMutantAllSameCharacter() {
        String[] dna = {
                "GGGG",
                "GGGG",
                "GGGG",
                "GGGG"
        };
        assertTrue(mutantDetector.isMutant(dna));
    }

    @Test
    @DisplayName("Debe detectar mutante con diagonal en esquina")
    void testMutantDiagonalInCorner() {
        String[] dna = {
                "AAAA",
                "TATG",
                "GTAT",
                "CGTA"
        };
        assertTrue(mutantDetector.isMutant(dna));
    }

    // ==================== CASOS HUMANOS ====================

    @Test
    @DisplayName("No debe detectar mutante con solo una secuencia")
    void testNotMutantWithOnlyOneSequence() {
        String[] dna = {
                "CTGCGA",
                "TAGTGC",
                "GGGGTT",  // Solo 1 secuencia: GGGG
                "ATACGG",
                "GCATCA",
                "ACACTG"
        };
        assertFalse(mutantDetector.isMutant(dna));
    }

    @Test
    @DisplayName("No debe detectar mutante sin secuencias")
    void testNotMutantWithNoSequences() {
        String[] dna = {
                "CTGA",
                "AGTC",
                "GCAT",
                "TCAG"
        };
        assertFalse(mutantDetector.isMutant(dna));
    }

    @Test
    @DisplayName("No debe detectar mutante en matriz 4x4 sin secuencias")
    void testNotMutantSmallDna() {
        String[] dna = {
                "CTAG",
                "GATC",
                "TACG",
                "AGCT"
        };
        assertFalse(mutantDetector.isMutant(dna));
    }

    // ==================== VALIDACIONES ====================

    @Test
    @DisplayName("Debe retornar false para DNA null")
    void testNotMutantWithNullDna() {
        assertFalse(mutantDetector.isMutant(null));
    }

    @Test
    @DisplayName("Debe retornar false para array vacío")
    void testNotMutantWithEmptyDna() {
        String[] dna = {};
        assertFalse(mutantDetector.isMutant(dna));
    }

    @Test
    @DisplayName("Debe retornar false para matriz no cuadrada")
    void testNotMutantWithNonSquareDna() {
        String[] dna = {
                "TGCA",
                "GA",      // Longitud diferente
                "CGAT",
                "TACG"
        };
        assertFalse(mutantDetector.isMutant(dna));
    }

    @Test
    @DisplayName("Debe retornar false para caracteres inválidos")
    void testNotMutantWithInvalidCharacters() {
        String[] dna = {
                "CTYZ",  // Y y Z no son válidos
                "AGTC",
                "GCAT",
                "TCAG"
        };
        assertFalse(mutantDetector.isMutant(dna));
    }

    @Test
    @DisplayName("Debe retornar false si alguna fila es null")
    void testNotMutantWithNullRow() {
        String[] dna = {
                "CTGA",
                null,    // Fila null
                "GCAT",
                "TCAG"
        };
        assertFalse(mutantDetector.isMutant(dna));
    }

    @Test
    @DisplayName("Debe retornar false para matriz muy pequeña (menor a 4x4)")
    void testNotMutantWithTooSmallDna() {
        String[] dna = {
                "CTG",
                "AGT",
                "GCA"
        };
        assertFalse(mutantDetector.isMutant(dna));
    }

    @Test
    void testNotMutantWithSequenceLongerThanFour() {
        String[] dna = {
                "GGGGGG",
                "CAGTGC",
                "TTATGT",
                "AGAACC",
                "TTTTCA",
                "ACACTG"
        };

        // "GGGGGG" y "TTTTCA" contienen múltiples secuencias de 4
        // Es mutante según las reglas de Magneto.
        assertTrue(mutantDetector.isMutant(dna));
    }
}