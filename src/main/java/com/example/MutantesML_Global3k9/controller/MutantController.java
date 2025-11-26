package com.example.MutantesML_Global3k9.controller;

import com.example.MutantesML_Global3k9.dto.DnaRequest;
import com.example.MutantesML_Global3k9.dto.ErrorResponse;
import com.example.MutantesML_Global3k9.dto.StatsResponse;
import com.example.MutantesML_Global3k9.service.MutantService;
import com.example.MutantesML_Global3k9.service.StatsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller principal para la API de detección de mutantes.
 *
 * ENDPOINTS:
 * - POST /mutant → Verificar si un ADN es mutante
 * - GET /stats → Obtener estadísticas
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@Validated
@Tag(name = "Mutant Detector", description = "API para detección de mutantes mediante análisis de ADN")
public class MutantController {

    private final MutantService mutantService;
    private final StatsService statsService;

    /**
     * POST /mutant
     *
     * Verifica si una secuencia de ADN corresponde a un mutante.
     *
     * RESPONSES:
     * - 200 OK → Es mutante
     * - 403 Forbidden → No es mutante (es humano)
     * - 400 Bad Request → DNA inválido
     *
     * @param request DnaRequest con la secuencia de ADN
     * @return ResponseEntity vacío con status apropiado
     */
    @PostMapping("/mutant")
    @Operation(
            summary = "Verificar si un ADN es mutante",
            description = "Analiza una secuencia de ADN y determina si pertenece a un mutante. " +
                    "Un humano es mutante si tiene más de una secuencia de 4 letras iguales " +
                    "en dirección horizontal, vertical o diagonal."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Es mutante - Se encontraron 2 o más secuencias de 4 letras iguales"
            ),
            @ApiResponse(
                    responseCode = "403",
                    description = "No es mutante - Se encontraron 0 o 1 secuencia"
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "DNA inválido - Formato incorrecto, caracteres no permitidos o matriz no cuadrada",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))
            )
    })
    public ResponseEntity<Void> checkMutant(@Valid @RequestBody DnaRequest request) {
        log.info("POST /mutant - Analizando DNA");

        boolean isMutant = mutantService.analyzeDna(request.getDna());

        if (isMutant) {
            log.info("POST /mutant - Resultado: MUTANTE (200 OK)");
            return ResponseEntity.ok().build();
        } else {
            log.info("POST /mutant - Resultado: HUMANO (403 Forbidden)");
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
    }

    /**
     * GET /stats
     *
     * Obtiene estadísticas de las verificaciones de ADN realizadas.
     *
     * Ejemplo de respuesta:
     * RESPONSE:
     * {
     *   "count_mutant_dna": 40,
     *   "count_human_dna": 100,
     *   "ratio": 0.4
     * }
     *
     * @return ResponseEntity con StatsResponse
     */
    @GetMapping("/stats")
    @Operation(
            summary = "Obtener estadísticas de verificaciones",
            description = "Retorna las estadísticas de todas las verificaciones de ADN realizadas, " +
                    "incluyendo cantidad de mutantes, humanos y el ratio entre ellos."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Estadísticas obtenidas exitosamente",
                    content = @Content(schema = @Schema(implementation = StatsResponse.class))
            )
    })
    public ResponseEntity<StatsResponse> getStats() {
        log.info("GET /stats - Obteniendo estadísticas");

        StatsResponse stats = statsService.getStats();

        log.info("GET /stats - Estadísticas: Mutantes={}, Humanos={}, Ratio={}",
                stats.getCountMutantDna(), stats.getCountHumanDna(), stats.getRatio());

        return ResponseEntity.ok(stats);
    }
}