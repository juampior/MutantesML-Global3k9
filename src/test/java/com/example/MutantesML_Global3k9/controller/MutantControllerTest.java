package com.example.MutantesML_Global3k9.controller;

import com.example.MutantesML_Global3k9.dto.StatsResponse;
import com.example.MutantesML_Global3k9.service.MutantService;
import com.example.MutantesML_Global3k9.service.StatsService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Tests de integración para MutantController.
 *
 * Usa @WebMvcTest para probar el controller completo
 * con todas las validaciones y manejo de errores.
 *
 * OBJETIVO: 8 tests de integración
 */
@WebMvcTest(MutantController.class)
@DisplayName("MutantController - Tests de Integración")
class MutantControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MutantService mutantService;

    @MockBean
    private StatsService statsService;

    // ==================== POST /mutant ====================

    @Test
    @DisplayName("POST /mutant debe retornar 200 OK cuando es mutante")
    void testCheckMutant_ReturnOk_WhenIsMutant() throws Exception {
        // Arrange
        String jsonRequest = """
            {
              "dna": ["AAAATG","TGCAGT","GCTTCC","CCCCTG","GATTCA","CTCATG"]
            }
            """;

        when(mutantService.analyzeDna(any())).thenReturn(true);

        // Act & Assert
        mockMvc.perform(post("/mutant")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("POST /mutant debe retornar 403 Forbidden cuando es humano")
    void testCheckMutant_ReturnForbidden_WhenIsHuman() throws Exception {
        // Arrange
        String jsonRequest = """
            {
              "dna": ["TGCAGT","ACGTCA","GTCATG","CAGTAC","TGACTG","ACGTGA"]
            }
            """;

        when(mutantService.analyzeDna(any())).thenReturn(false);

        // Act & Assert
        mockMvc.perform(post("/mutant")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest))
                .andExpect(status().isForbidden());
    }

    @Test
    @DisplayName("POST /mutant debe retornar 400 Bad Request cuando DNA es null")
    void testCheckMutant_ReturnBadRequest_WhenDnaIsNull() throws Exception {
        // Arrange
        String jsonRequest = """
            {
              "dna": null
            }
            """;

        // Act & Assert
        mockMvc.perform(post("/mutant")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("POST /mutant debe retornar 400 Bad Request cuando DNA es vacío")
    void testCheckMutant_ReturnBadRequest_WhenDnaIsEmpty() throws Exception {
        // Arrange
        String jsonRequest = """
            {
              "dna": []
            }
            """;

        // Act & Assert
        mockMvc.perform(post("/mutant")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("POST /mutant debe retornar 400 Bad Request cuando matriz no es cuadrada")
    void testCheckMutant_ReturnBadRequest_WhenDnaIsNotSquare() throws Exception {
        // Arrange
        String jsonRequest = """
            {
              "dna": ["TGCA","GCT","ATCG"]
            }
            """;

        // Act & Assert
        mockMvc.perform(post("/mutant")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").exists());
    }

    @Test
    @DisplayName("POST /mutant debe retornar 400 Bad Request cuando hay caracteres inválidos")
    void testCheckMutant_ReturnBadRequest_WhenInvalidCharacters() throws Exception {
        // Arrange
        String jsonRequest = """
            {
              "dna": ["TGBZ","ACTG","GTCA","CAGT"]
            }
            """;

        // Act & Assert
        mockMvc.perform(post("/mutant")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").exists());
    }

    // ==================== GET /stats ====================

    @Test
    @DisplayName("GET /stats debe retornar 200 OK con estadísticas")
    void testGetStats_ReturnOk_WithStats() throws Exception {
        // Arrange
        StatsResponse stats = new StatsResponse(25L, 75L, 0.33);
        when(statsService.getStats()).thenReturn(stats);

        // Act & Assert
        mockMvc.perform(get("/stats"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.count_mutant_dna").value(25))
                .andExpect(jsonPath("$.count_human_dna").value(75))
                .andExpect(jsonPath("$.ratio").value(0.33));
    }

    @Test
    @DisplayName("GET /stats debe retornar 200 OK cuando no hay registros")
    void testGetStats_ReturnOk_WhenNoRecords() throws Exception {
        // Arrange
        StatsResponse stats = new StatsResponse(0L, 0L, 0.0);
        when(statsService.getStats()).thenReturn(stats);

        // Act & Assert
        mockMvc.perform(get("/stats"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.count_mutant_dna").value(0))
                .andExpect(jsonPath("$.count_human_dna").value(0))
                .andExpect(jsonPath("$.ratio").value(0.0));
    }
}