package com.example.MutantesML_Global3k9.repository;

import com.example.MutantesML_Global3k9.entity.DnaRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository para acceso a datos de DnaRecord.
 *
 * Spring Data JPA genera automáticamente las implementaciones
 * de estos métodos basándose en sus nombres.
 *
 * PERFORMANCE:
 * - findByDnaHash: O(log N) con índice
 * - countByIsMutant: O(1) con índice
 */
@Repository
public interface DnaRecordRepository extends JpaRepository<DnaRecord, Long> {

    /**
     * Busca un registro por su hash de DNA.
     * Usado para verificar si un DNA ya fue analizado (deduplicación).
     *
     * @param dnaHash Hash SHA-256 del DNA
     * @return Optional con el registro si existe
     */
    Optional<DnaRecord> findByDnaHash(String dnaHash);

    /**
     * Cuenta cuántos registros hay de mutantes o humanos.
     *
     * @param isMutant true para contar mutantes, false para humanos
     * @return Cantidad de registros
     */
    long countByIsMutant(boolean isMutant);
}