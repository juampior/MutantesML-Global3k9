package com.example.MutantesML_Global3k9.service;

import com.example.MutantesML_Global3k9.entity.DnaRecord;
import com.example.MutantesML_Global3k9.exception.DnaHashCalculationException;
import com.example.MutantesML_Global3k9.repository.DnaRecordRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Optional;

/**
 * Servicio principal para análisis de ADN.
 *
 * ESTRATEGIA:
 * 1. Calcular hash del DNA
 * 2. Buscar en BD (caché)
 * 3. Si existe → retornar resultado
 * 4. Si no existe → analizar + guardar + retornar
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class MutantService {

    private final MutantDetector mutantDetector;
    private final DnaRecordRepository repository;

    /**
     * Analiza un DNA y determina si es mutante.
     * Usa caché (BD) para evitar re-análisis.
     *
     * @param dna Secuencia de ADN
     * @return true si es mutante, false si es humano
     */
    @Transactional
    public boolean analyzeDna(String[] dna) {
        // 1. Calcular hash del DNA
        String dnaHash = calculateDnaHash(dna);
        log.debug("DNA hash calculado: {}", dnaHash);

        // 2. Buscar en BD (caché)
        Optional<DnaRecord> existingRecord = repository.findByDnaHash(dnaHash);

        if (existingRecord.isPresent()) {
            boolean isMutant = existingRecord.get().isMutant();
            log.info("DNA encontrado en caché - isMutant: {}", isMutant);
            return isMutant;
        }

        // 3. No existe → analizar con el detector
        log.debug("DNA no encontrado en caché, analizando...");
        boolean isMutant = mutantDetector.isMutant(dna);

        // 4. Guardar resultado en BD
        DnaRecord newRecord = new DnaRecord(dnaHash, isMutant);
        repository.save(newRecord);
        log.info("DNA analizado y guardado - isMutant: {}", isMutant);

        return isMutant;
    }

    /**
     * Calcula el hash SHA-256 de un DNA.
     *
     * ESTRATEGIA:
     * - Concatena todas las filas: "ATGCGACAGTGC..."
     * - Aplica SHA-256
     * - Convierte a hexadecimal
     *
     * @param dna Secuencia de ADN
     * @return Hash hexadecimal de 64 caracteres
     * @throws DnaHashCalculationException si falla el cálculo
     */
    protected String calculateDnaHash(String[] dna) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");

            // Concatenar todas las filas
            String dnaString = String.join("", dna);

            // Calcular hash
            byte[] hashBytes = digest.digest(dnaString.getBytes(StandardCharsets.UTF_8));

            // Convertir a hexadecimal
            StringBuilder hexString = new StringBuilder();
            for (byte b : hashBytes) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);
            }

            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            log.error("Error calculando hash SHA-256", e);
            throw new DnaHashCalculationException("Error calculating DNA hash", e);
        }
    }
}