package com.example.MutantesML_Global3k9.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * Entidad JPA que representa un registro de ADN analizado.
 *
 * ESTRATEGIA DE DEDUPLICACIÓN:
 * - Usa dnaHash (SHA-256) como clave de búsqueda
 * - Campo unique garantiza 1 registro por ADN
 * - Índices para búsquedas rápidas O(log N)
 */
@Entity
@Table(
        name = "dna_records",
        indexes = {
                @Index(name = "idx_dna_hash", columnList = "dnaHash"),
                @Index(name = "idx_is_mutant", columnList = "isMutant")
        }
)
@Getter
@Setter
@NoArgsConstructor
public class DnaRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Hash SHA-256 del DNA para deduplicación
     * UNIQUE garantiza 1 solo registro por DNA
     */
    @Column(name = "dnaHash", unique = true, nullable = false, length = 64)
    private String dnaHash;

    /**
     * Resultado del análisis: true = mutante, false = humano
     */
    @Column(name = "isMutant", nullable = false)
    private boolean isMutant;

    /**
     * Fecha y hora del análisis
     */
    @Column(name = "createdAt", nullable = false)
    private LocalDateTime createdAt;

    /**
     * Constructor con campos principales
     */
    public DnaRecord(String dnaHash, boolean isMutant) {
        this.dnaHash = dnaHash;
        this.isMutant = isMutant;
        this.createdAt = LocalDateTime.now();
    }
}
