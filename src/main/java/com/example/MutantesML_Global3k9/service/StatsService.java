package com.example.MutantesML_Global3k9.service;

import com.example.MutantesML_Global3k9.dto.StatsResponse;
import com.example.MutantesML_Global3k9.repository.DnaRecordRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Servicio para obtener estadísticas de verificaciones de ADN.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class StatsService {

    private final DnaRecordRepository repository;

    /**
     * Obtiene las estadísticas de verificaciones.
     *
     * Calcula:
     * - count_mutant_dna: Cantidad de mutantes
     * - count_human_dna: Cantidad de humanos
     * - ratio: mutantes / humanos
     *
     * CASOS ESPECIALES:
     * - Si no hay humanos y hay mutantes → ratio = count_mutant_dna
     * - Si no hay ninguno → ratio = 0
     *
     * @return StatsResponse con las estadísticas
     */
    @Transactional(readOnly = true)
    public StatsResponse getStats() {
        long countMutant = repository.countByIsMutant(true);
        long countHuman = repository.countByIsMutant(false);

        double ratio = calculateRatio(countMutant, countHuman);

        log.info("Stats calculadas - Mutantes: {}, Humanos: {}, Ratio: {}",
                countMutant, countHuman, ratio);

        return new StatsResponse(countMutant, countHuman, ratio);
    }

    /**
     * Calcula el ratio mutantes/humanos.
     *
     * @param countMutant Cantidad de mutantes
     * @param countHuman Cantidad de humanos
     * @return Ratio calculado
     */
    private double calculateRatio(long countMutant, long countHuman) {
        if (countHuman == 0) {
            // Caso especial: no hay humanos
            return countMutant > 0 ? (double) countMutant : 0.0;
        }
        return (double) countMutant / countHuman;
    }
}