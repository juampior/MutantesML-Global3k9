package com.example.MutantesML_Global3k9.dto;

import com.example.MutantesML_Global3k9.validation.ValidDnaSequence;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Request para verificar si un ADN es mutante")
public class DnaRequest {

    @Schema(
            description = "Secuencia de ADN representada como matriz NxN. Solo caracteres A, T, C, G permitidos.",
            example = "[\"ATGCGA\",\"CAGTGC\",\"TTATGT\",\"AGAAGG\",\"CCCCTA\",\"TCACTG\"]",
            required = true
    )
    @NotNull(message = "DNA no puede ser null")
    @NotEmpty(message = "DNA no puede estar vacío")
    @ValidDnaSequence
    private String[] dna;
}
