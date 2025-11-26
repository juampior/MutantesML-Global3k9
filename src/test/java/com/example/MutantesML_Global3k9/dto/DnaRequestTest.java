package com.example.MutantesML_Global3k9.dto;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class DnaRequestTest {

    private Validator validator;

    @BeforeEach
    void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void constructor_ShouldCreateDnaRequestWithAllArgs() {
        // Arrange
        String[] dna = {"ATGCGA", "CAGTGC", "TTATGT", "AGAAGG", "CCCCTA", "TCACTG"};

        // Act
        DnaRequest request = new DnaRequest(dna);

        // Assert
        assertNotNull(request);
        assertArrayEquals(dna, request.getDna());
    }

    @Test
    void constructor_ShouldCreateEmptyDnaRequest() {
        // Act
        DnaRequest request = new DnaRequest();

        // Assert
        assertNotNull(request);
        assertNull(request.getDna());
    }

    @Test
    void setDna_ShouldSetDnaCorrectly() {
        // Arrange
        DnaRequest request = new DnaRequest();
        String[] dna = {"ATGCGA", "CAGTGC"};

        // Act
        request.setDna(dna);

        // Assert
        assertArrayEquals(dna, request.getDna());
    }

    @Test
    void getDna_ShouldReturnDna() {
        // Arrange
        String[] dna = {"ATGCGA", "CAGTGC"};
        DnaRequest request = new DnaRequest(dna);

        // Act
        String[] result = request.getDna();

        // Assert
        assertArrayEquals(dna, result);
    }

    @Test
    void validation_ShouldPassForValidDna() {
        // Arrange
        String[] dna = {"ATGCGA", "CAGTGC", "TTATGT", "AGAAGG", "CCCCTA", "TCACTG"};
        DnaRequest request = new DnaRequest(dna);

        // Act
        Set<ConstraintViolation<DnaRequest>> violations = validator.validate(request);

        // Assert
        assertTrue(violations.isEmpty());
    }

    @Test
    void validation_ShouldFailWhenDnaIsNull() {
        // Arrange
        DnaRequest request = new DnaRequest(null);

        // Act
        Set<ConstraintViolation<DnaRequest>> violations = validator.validate(request);

        // Assert
        assertFalse(violations.isEmpty());
        assertTrue(violations.stream()
                .anyMatch(v -> v.getMessage().equals("DNA no puede ser null")));
    }

    @Test
    void validation_ShouldFailWhenDnaIsEmpty() {
        // Arrange
        DnaRequest request = new DnaRequest(new String[]{});

        // Act
        Set<ConstraintViolation<DnaRequest>> violations = validator.validate(request);

        // Assert
        assertFalse(violations.isEmpty());
        assertTrue(violations.stream()
                .anyMatch(v -> v.getMessage().equals("DNA no puede estar vacío")));
    }

    @Test
    void validation_ShouldFailForInvalidDnaSequence() {
        // Arrange
        String[] invalidDna = {"ATGCGA", "CAGTGC", "INVALID"};
        DnaRequest request = new DnaRequest(invalidDna);

        // Act
        Set<ConstraintViolation<DnaRequest>> violations = validator.validate(request);

        // Assert
        assertFalse(violations.isEmpty());
    }

    @Test
    void validation_ShouldFailForNonSquareMatrix() {
        // Arrange
        String[] nonSquareDna = {"ATGCGA", "CAGTGC"};
        DnaRequest request = new DnaRequest(nonSquareDna);

        // Act
        Set<ConstraintViolation<DnaRequest>> violations = validator.validate(request);

        // Assert
        assertFalse(violations.isEmpty());
    }

    @Test
    void equals_ShouldReturnTrueForSameDna() {
        // Arrange
        String[] dna = {"ATGCGA", "CAGTGC"};
        DnaRequest request1 = new DnaRequest(dna);
        DnaRequest request2 = new DnaRequest(dna);

        // Act & Assert
        assertEquals(request1, request2);
    }

    @Test
    void hashCode_ShouldBeSameForSameDna() {
        // Arrange
        String[] dna = {"ATGCGA", "CAGTGC"};
        DnaRequest request1 = new DnaRequest(dna);
        DnaRequest request2 = new DnaRequest(dna);

        // Act & Assert
        assertEquals(request1.hashCode(), request2.hashCode());
    }

    @Test
    void toString_ShouldContainDnaInformation() {
        // Arrange
        String[] dna = {"ATGCGA", "CAGTGC"};
        DnaRequest request = new DnaRequest(dna);

        // Act
        String result = request.toString();

        // Assert
        assertNotNull(result);
        assertTrue(result.contains("DnaRequest"));
    }
}
