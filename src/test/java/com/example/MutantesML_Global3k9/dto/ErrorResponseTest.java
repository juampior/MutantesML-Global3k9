package com.example.MutantesML_Global3k9.dto;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class ErrorResponseTest {

    @Test
    void constructor_ShouldCreateErrorResponseWithAllArgs() {
        // Arrange
        LocalDateTime timestamp = LocalDateTime.now();
        int status = 400;
        String error = "Bad Request";
        String message = "Error de validación";
        String path = "/api/mutant";

        // Act
        ErrorResponse response = new ErrorResponse(timestamp, status, error, message, path);

        // Assert
        assertNotNull(response);
        assertEquals(timestamp, response.getTimestamp());
        assertEquals(status, response.getStatus());
        assertEquals(error, response.getError());
        assertEquals(message, response.getMessage());
        assertEquals(path, response.getPath());
    }

    @Test
    void constructor_ShouldCreateEmptyErrorResponse() {
        // Act
        ErrorResponse response = new ErrorResponse();

        // Assert
        assertNotNull(response);
        assertNull(response.getTimestamp());
        assertEquals(0, response.getStatus());
        assertNull(response.getError());
        assertNull(response.getMessage());
        assertNull(response.getPath());
    }

    @Test
    void setTimestamp_ShouldSetTimestampCorrectly() {
        // Arrange
        ErrorResponse response = new ErrorResponse();
        LocalDateTime timestamp = LocalDateTime.of(2025, 11, 25, 10, 30);

        // Act
        response.setTimestamp(timestamp);

        // Assert
        assertEquals(timestamp, response.getTimestamp());
    }

    @Test
    void setStatus_ShouldSetStatusCorrectly() {
        // Arrange
        ErrorResponse response = new ErrorResponse();
        int status = 404;

        // Act
        response.setStatus(status);

        // Assert
        assertEquals(status, response.getStatus());
    }

    @Test
    void setError_ShouldSetErrorCorrectly() {
        // Arrange
        ErrorResponse response = new ErrorResponse();
        String error = "Not Found";

        // Act
        response.setError(error);

        // Assert
        assertEquals(error, response.getError());
    }

    @Test
    void setMessage_ShouldSetMessageCorrectly() {
        // Arrange
        ErrorResponse response = new ErrorResponse();
        String message = "Recurso no encontrado";

        // Act
        response.setMessage(message);

        // Assert
        assertEquals(message, response.getMessage());
    }

    @Test
    void setPath_ShouldSetPathCorrectly() {
        // Arrange
        ErrorResponse response = new ErrorResponse();
        String path = "/api/stats";

        // Act
        response.setPath(path);

        // Assert
        assertEquals(path, response.getPath());
    }

    @Test
    void equals_ShouldReturnTrueForSameContent() {
        // Arrange
        LocalDateTime timestamp = LocalDateTime.of(2025, 11, 25, 10, 30);
        ErrorResponse response1 = new ErrorResponse(timestamp, 400, "Bad Request", "Error", "/api");
        ErrorResponse response2 = new ErrorResponse(timestamp, 400, "Bad Request", "Error", "/api");

        // Act & Assert
        assertEquals(response1, response2);
    }

    @Test
    void equals_ShouldReturnFalseForDifferentContent() {
        // Arrange
        LocalDateTime timestamp = LocalDateTime.now();
        ErrorResponse response1 = new ErrorResponse(timestamp, 400, "Bad Request", "Error", "/api");
        ErrorResponse response2 = new ErrorResponse(timestamp, 404, "Not Found", "Error", "/api");

        // Act & Assert
        assertNotEquals(response1, response2);
    }

    @Test
    void hashCode_ShouldBeSameForSameContent() {
        // Arrange
        LocalDateTime timestamp = LocalDateTime.of(2025, 11, 25, 10, 30);
        ErrorResponse response1 = new ErrorResponse(timestamp, 400, "Bad Request", "Error", "/api");
        ErrorResponse response2 = new ErrorResponse(timestamp, 400, "Bad Request", "Error", "/api");

        // Act & Assert
        assertEquals(response1.hashCode(), response2.hashCode());
    }

    @Test
    void toString_ShouldContainErrorInformation() {
        // Arrange
        LocalDateTime timestamp = LocalDateTime.now();
        ErrorResponse response = new ErrorResponse(timestamp, 500, "Internal Server Error", "Error interno", "/api/mutant");

        // Act
        String result = response.toString();

        // Assert
        assertNotNull(result);
        assertTrue(result.contains("ErrorResponse") ||
                result.contains("500") ||
                result.contains("Internal Server Error"));
    }

    @Test
    void allFields_ShouldBeSettableAndGettable() {
        // Arrange
        ErrorResponse response = new ErrorResponse();
        LocalDateTime timestamp = LocalDateTime.now();

        // Act
        response.setTimestamp(timestamp);
        response.setStatus(403);
        response.setError("Forbidden");
        response.setMessage("Acceso denegado");
        response.setPath("/api/protected");

        // Assert
        assertEquals(timestamp, response.getTimestamp());
        assertEquals(403, response.getStatus());
        assertEquals("Forbidden", response.getError());
        assertEquals("Acceso denegado", response.getMessage());
        assertEquals("/api/protected", response.getPath());
    }

    @Test
    void constructor_ShouldHandleNullValues() {
        // Act
        ErrorResponse response = new ErrorResponse(null, 0, null, null, null);

        // Assert
        assertNotNull(response);
        assertNull(response.getTimestamp());
        assertNull(response.getError());
        assertNull(response.getMessage());
        assertNull(response.getPath());
    }
}
