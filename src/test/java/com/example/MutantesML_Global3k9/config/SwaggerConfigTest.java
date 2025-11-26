package com.example.MutantesML_Global3k9.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

import static org.junit.jupiter.api.Assertions.*;

class SwaggerConfigTest {

    private SwaggerConfig swaggerConfig;

    @BeforeEach
    void setUp() {
        swaggerConfig = new SwaggerConfig();
        ReflectionTestUtils.setField(swaggerConfig, "serverPort", "8080");
    }

    @Test
    void customOpenAPI_ShouldReturnConfiguredOpenAPI() {
        // Act
        OpenAPI openAPI = swaggerConfig.customOpenAPI();

        // Assert
        assertNotNull(openAPI);
        assertNotNull(openAPI.getInfo());
        assertNotNull(openAPI.getServers());
        assertEquals(1, openAPI.getServers().size());
    }

    @Test
    void customOpenAPI_ShouldConfigureServerCorrectly() {
        // Act
        OpenAPI openAPI = swaggerConfig.customOpenAPI();
        Server server = openAPI.getServers().get(0);

        // Assert
        assertEquals("http://localhost:8080", server.getUrl());
        assertEquals("Local Development Server", server.getDescription());
    }

    @Test
    void customOpenAPI_ShouldConfigureInfoCorrectly() {
        // Act
        OpenAPI openAPI = swaggerConfig.customOpenAPI();
        Info info = openAPI.getInfo();

        // Assert
        assertEquals("Mutant Detector API", info.getTitle());
        assertEquals("1.0.0", info.getVersion());
        assertNotNull(info.getDescription());
        assertTrue(info.getDescription().contains("Magneto"));
        assertTrue(info.getDescription().contains("mutantes"));
    }

    @Test
    void customOpenAPI_ShouldConfigureContactCorrectly() {
        // Act
        OpenAPI openAPI = swaggerConfig.customOpenAPI();
        Contact contact = openAPI.getInfo().getContact();

        // Assert
        assertNotNull(contact);
        assertEquals("Ortega Rivero, Oscar Juan Pablo", contact.getName());
        assertEquals("juampiortega28@gmail.com", contact.getEmail());
    }

    @Test
    void customOpenAPI_ShouldConfigureLicenseCorrectly() {
        // Act
        OpenAPI openAPI = swaggerConfig.customOpenAPI();
        License license = openAPI.getInfo().getLicense();

        // Assert
        assertNotNull(license);
        assertEquals("MIT License", license.getName());
        assertEquals("https://opensource.org/licenses/MIT", license.getUrl());
    }

    @Test
    void customOpenAPI_ShouldUseCustomPort() {
        // Arrange
        ReflectionTestUtils.setField(swaggerConfig, "serverPort", "9090");

        // Act
        OpenAPI openAPI = swaggerConfig.customOpenAPI();
        Server server = openAPI.getServers().get(0);

        // Assert
        assertEquals("http://localhost:9090", server.getUrl());
    }
}
