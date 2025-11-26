package com.example.MutantesML_Global3k9.service;

import com.example.MutantesML_Global3k9.entity.DnaRecord;
import com.example.MutantesML_Global3k9.repository.DnaRecordRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

/**
 * Tests unitarios para MutantService.
 *
 * OBJETIVO: 5 tests, >80% cobertura
 */
@ExtendWith(MockitoExtension.class)
@DisplayName("MutantService - Tests Unitarios")
class MutantServiceTest {

    @Mock
    private MutantDetector mutantDetector;

    @Mock
    private DnaRecordRepository repository;

    @InjectMocks
    private MutantService mutantService;

    private String[] mutantDna;
    private String[] humanDna;

    @BeforeEach
    void setUp() {
        mutantDna = new String[]{
                "CTGCGA",
                "GGGGCA",
                "ACATGT",
                "TGAAGG",
                "CGTCTA",
                "ATCACT"
        };

        humanDna = new String[]{
                "CTGCGA",
                "TAGTGC",
                "GGGGTT",
                "ATACGG",
                "GCATCA",
                "ACACTG"
        };
    }

    @Test
    @DisplayName("Debe analizar y guardar DNA mutante cuando no existe en BD")
    void testAnalyzeDna_MutantNotInDatabase() {
        // Arrange
        when(repository.findByDnaHash(anyString())).thenReturn(Optional.empty());
        when(mutantDetector.isMutant(mutantDna)).thenReturn(true);
        when(repository.save(any(DnaRecord.class))).thenReturn(new DnaRecord());

        // Act
        boolean result = mutantService.analyzeDna(mutantDna);

        // Assert
        assertTrue(result);
        verify(repository, times(1)).findByDnaHash(anyString());
        verify(mutantDetector, times(1)).isMutant(mutantDna);
        verify(repository, times(1)).save(any(DnaRecord.class));
    }

    @Test
    @DisplayName("Debe retornar resultado cacheado cuando DNA ya existe en BD")
    void testAnalyzeDna_AlreadyInDatabase() {
        // Arrange
        DnaRecord cachedRecord = new DnaRecord();
        cachedRecord.setDnaHash("xyz789");
        cachedRecord.setMutant(true);

        when(repository.findByDnaHash(anyString())).thenReturn(Optional.of(cachedRecord));

        // Act
        boolean result = mutantService.analyzeDna(mutantDna);

        // Assert
        assertTrue(result);
        verify(repository, times(1)).findByDnaHash(anyString());
        verify(mutantDetector, never()).isMutant(any());  // No debe llamar al detector
        verify(repository, never()).save(any());  // No debe guardar
    }

    @Test
    @DisplayName("Debe analizar y guardar DNA humano cuando no existe en BD")
    void testAnalyzeDna_HumanNotInDatabase() {
        // Arrange
        when(repository.findByDnaHash(anyString())).thenReturn(Optional.empty());
        when(mutantDetector.isMutant(humanDna)).thenReturn(false);
        when(repository.save(any(DnaRecord.class))).thenReturn(new DnaRecord());

        // Act
        boolean result = mutantService.analyzeDna(humanDna);

        // Assert
        assertFalse(result);
        verify(repository, times(1)).findByDnaHash(anyString());
        verify(mutantDetector, times(1)).isMutant(humanDna);
        verify(repository, times(1)).save(any(DnaRecord.class));
    }

    @Test
    @DisplayName("Debe calcular hash SHA-256 correctamente")
    void testCalculateDnaHash() {
        // Act
        String hash1 = mutantService.calculateDnaHash(mutantDna);
        String hash2 = mutantService.calculateDnaHash(mutantDna);

        // Assert
        assertNotNull(hash1);
        assertEquals(64, hash1.length());  // SHA-256 = 64 caracteres hex
        assertEquals(hash1, hash2);  // Mismo DNA → mismo hash
    }

    @Test
    @DisplayName("Debe generar hashes diferentes para DNAs diferentes")
    void testCalculateDnaHash_DifferentDnas() {
        // Act
        String hash1 = mutantService.calculateDnaHash(mutantDna);
        String hash2 = mutantService.calculateDnaHash(humanDna);

        // Assert
        assertNotEquals(hash1, hash2);
    }
}
