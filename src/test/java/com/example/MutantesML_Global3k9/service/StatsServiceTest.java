// ==================== StatsServiceTest ====================
package com.example.MutantesML_Global3k9.service;

import com.example.MutantesML_Global3k9.dto.StatsResponse;
import com.example.MutantesML_Global3k9.repository.DnaRecordRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

/**
 * Tests unitarios para StatsService.
 *
 * OBJETIVO: 6 tests, >80% cobertura
 */
@ExtendWith(MockitoExtension.class)
@DisplayName("StatsService - Tests Unitarios")
class StatsServiceTest {

    @Mock
    private DnaRecordRepository repository;

    @InjectMocks
    private StatsService statsService;

    @Test
    @DisplayName("Debe calcular estadísticas correctamente con mutantes y humanos")
    void testGetStats_WithMutantsAndHumans() {
        // Arrange
        when(repository.countByIsMutant(true)).thenReturn(25L);
        when(repository.countByIsMutant(false)).thenReturn(75L);

        // Act
        StatsResponse stats = statsService.getStats();

        // Assert
        assertEquals(25, stats.getCountMutantDna());
        assertEquals(75, stats.getCountHumanDna());
        assertEquals(0.33, stats.getRatio(), 0.01);
        verify(repository, times(1)).countByIsMutant(true);
        verify(repository, times(1)).countByIsMutant(false);
    }

    @Test
    @DisplayName("Debe calcular ratio = 1.0 cuando hay igual cantidad")
    void testGetStats_EqualCounts() {
        // Arrange
        when(repository.countByIsMutant(true)).thenReturn(60L);
        when(repository.countByIsMutant(false)).thenReturn(60L);

        // Act
        StatsResponse stats = statsService.getStats();

        // Assert
        assertEquals(1.0, stats.getRatio(), 0.001);
    }

    @Test
    @DisplayName("Debe retornar ratio = 0 cuando no hay registros")
    void testGetStats_NoRecords() {
        // Arrange
        when(repository.countByIsMutant(true)).thenReturn(0L);
        when(repository.countByIsMutant(false)).thenReturn(0L);

        // Act
        StatsResponse stats = statsService.getStats();

        // Assert
        assertEquals(0, stats.getCountMutantDna());
        assertEquals(0, stats.getCountHumanDna());
        assertEquals(0.0, stats.getRatio());
    }

    @Test
    @DisplayName("Debe manejar caso especial: solo mutantes, sin humanos")
    void testGetStats_OnlyMutants() {
        // Arrange
        when(repository.countByIsMutant(true)).thenReturn(35L);
        when(repository.countByIsMutant(false)).thenReturn(0L);

        // Act
        StatsResponse stats = statsService.getStats();

        // Assert
        assertEquals(35, stats.getCountMutantDna());
        assertEquals(0, stats.getCountHumanDna());
        assertEquals(35.0, stats.getRatio());  // Caso especial
    }

    @Test
    @DisplayName("Debe manejar caso especial: solo humanos, sin mutantes")
    void testGetStats_OnlyHumans() {
        // Arrange
        when(repository.countByIsMutant(true)).thenReturn(0L);
        when(repository.countByIsMutant(false)).thenReturn(80L);

        // Act
        StatsResponse stats = statsService.getStats();

        // Assert
        assertEquals(0, stats.getCountMutantDna());
        assertEquals(80, stats.getCountHumanDna());
        assertEquals(0.0, stats.getRatio());
    }

    @Test
    @DisplayName("Debe calcular ratio > 1 cuando hay más mutantes que humanos")
    void testGetStats_MoreMutantsThanHumans() {
        // Arrange
        when(repository.countByIsMutant(true)).thenReturn(90L);
        when(repository.countByIsMutant(false)).thenReturn(30L);

        // Act
        StatsResponse stats = statsService.getStats();

        // Assert
        assertEquals(90, stats.getCountMutantDna());
        assertEquals(30, stats.getCountHumanDna());
        assertEquals(3.0, stats.getRatio(), 0.001);
    }
}