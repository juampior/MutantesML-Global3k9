package com.example.MutantesML_Global3k9.dto;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StatsResponseTest {

    @Test
    void constructor_ShouldCreateStatsResponseWithAllArgs() {
        // Arrange
        long countMutant = 40;
        long countHuman = 100;
        double ratio = 0.4;

        // Act
        StatsResponse response = new StatsResponse(countMutant, countHuman, ratio);

        // Assert
        assertNotNull(response);
        assertEquals(countMutant, response.getCountMutantDna());
        assertEquals(countHuman, response.getCountHumanDna());
        assertEquals(ratio, response.getRatio());
    }

    @Test
    void constructor_ShouldCreateEmptyStatsResponse() {
        // Act
        StatsResponse response = new StatsResponse();

        // Assert
        assertNotNull(response);
        assertEquals(0, response.getCountMutantDna());
        assertEquals(0, response.getCountHumanDna());
        assertEquals(0.0, response.getRatio());
    }

    @Test
    void setCountMutantDna_ShouldSetValueCorrectly() {
        // Arrange
        StatsResponse response = new StatsResponse();

        // Act
        response.setCountMutantDna(50);

        // Assert
        assertEquals(50, response.getCountMutantDna());
    }

    @Test
    void setCountHumanDna_ShouldSetValueCorrectly() {
        // Arrange
        StatsResponse response = new StatsResponse();

        // Act
        response.setCountHumanDna(150);

        // Assert
        assertEquals(150, response.getCountHumanDna());
    }

    @Test
    void setRatio_ShouldSetValueCorrectly() {
        // Arrange
        StatsResponse response = new StatsResponse();

        // Act
        response.setRatio(0.33);

        // Assert
        assertEquals(0.33, response.getRatio(), 0.0001);
    }

    @Test
    void allSetters_ShouldWorkTogether() {
        // Arrange
        StatsResponse response = new StatsResponse();

        // Act
        response.setCountMutantDna(25);
        response.setCountHumanDna(75);
        response.setRatio(0.33);

        // Assert
        assertEquals(25, response.getCountMutantDna());
        assertEquals(75, response.getCountHumanDna());
        assertEquals(0.33, response.getRatio(), 0.0001);
    }

    @Test
    void equals_ShouldReturnTrueForSameContent() {
        // Arrange
        StatsResponse response1 = new StatsResponse(40, 100, 0.4);
        StatsResponse response2 = new StatsResponse(40, 100, 0.4);

        // Act & Assert
        assertEquals(response1, response2);
    }

    @Test
    void equals_ShouldReturnFalseForDifferentContent() {
        // Arrange
        StatsResponse response1 = new StatsResponse(40, 100, 0.4);
        StatsResponse response2 = new StatsResponse(50, 100, 0.5);

        // Act & Assert
        assertNotEquals(response1, response2);
    }

    @Test
    void hashCode_ShouldBeSameForSameContent() {
        // Arrange
        StatsResponse response1 = new StatsResponse(40, 100, 0.4);
        StatsResponse response2 = new StatsResponse(40, 100, 0.4);

        // Act & Assert
        assertEquals(response1.hashCode(), response2.hashCode());
    }

    @Test
    void toString_ShouldContainStatsInformation() {
        // Arrange
        StatsResponse response = new StatsResponse(40, 100, 0.4);

        // Act
        String result = response.toString();

        // Assert
        assertNotNull(result);
        assertTrue(result.contains("StatsResponse") ||
                result.contains("40") ||
                result.contains("100"));
    }

    @Test
    void ratio_ShouldHandleZeroHumans() {
        // Arrange & Act
        StatsResponse response = new StatsResponse(10, 0, 0.0);

        // Assert
        assertEquals(10, response.getCountMutantDna());
        assertEquals(0, response.getCountHumanDna());
        assertEquals(0.0, response.getRatio());
    }

    @Test
    void ratio_ShouldHandleZeroMutants() {
        // Arrange & Act
        StatsResponse response = new StatsResponse(0, 100, 0.0);

        // Assert
        assertEquals(0, response.getCountMutantDna());
        assertEquals(100, response.getCountHumanDna());
        assertEquals(0.0, response.getRatio());
    }

    @Test
    void ratio_ShouldHandleDecimalValues() {
        // Arrange & Act
        StatsResponse response = new StatsResponse(33, 100, 0.33);

        // Assert
        assertEquals(0.33, response.getRatio(), 0.0001);
    }

    @Test
    void jsonSerialization_ShouldUseCorrectPropertyNames() throws Exception {
        // Arrange
        StatsResponse response = new StatsResponse(40, 100, 0.4);
        ObjectMapper mapper = new ObjectMapper();

        // Act
        String json = mapper.writeValueAsString(response);

        // Assert
        assertTrue(json.contains("count_mutant_dna"));
        assertTrue(json.contains("count_human_dna"));
        assertTrue(json.contains("ratio"));
        assertTrue(json.contains("40"));
        assertTrue(json.contains("100"));
        assertTrue(json.contains("0.4"));
    }

    @Test
    void jsonDeserialization_ShouldWorkWithUnderscoreProperties() throws Exception {
        // Arrange
        String json = "{\"count_mutant_dna\":40,\"count_human_dna\":100,\"ratio\":0.4}";
        ObjectMapper mapper = new ObjectMapper();

        // Act
        StatsResponse response = mapper.readValue(json, StatsResponse.class);

        // Assert
        assertEquals(40, response.getCountMutantDna());
        assertEquals(100, response.getCountHumanDna());
        assertEquals(0.4, response.getRatio(), 0.0001);
    }

    @Test
    void constructor_ShouldHandleLargeNumbers() {
        // Arrange
        long countMutant = 1_000_000;
        long countHuman = 5_000_000;
        double ratio = 0.2;

        // Act
        StatsResponse response = new StatsResponse(countMutant, countHuman, ratio);

        // Assert
        assertEquals(1_000_000, response.getCountMutantDna());
        assertEquals(5_000_000, response.getCountHumanDna());
        assertEquals(0.2, response.getRatio(), 0.0001);
    }
}
