package com.example.MutantesML_Global3k9.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * Configuración de Swagger/OpenAPI para documentación de la API.
 *
 * Acceso: http://localhost:8080/swagger-ui.html
 */
@Configuration
public class SwaggerConfig {

    @Value("${server.port:8080}")
    private String serverPort;

    @Bean
    public OpenAPI customOpenAPI() {
        Server localServer = new Server();
        localServer.setUrl("http://localhost:" + serverPort);
        localServer.setDescription("Local Development Server");

        Contact contact = new Contact();
        contact.setName("Ortega Rivero, Oscar Juan Pablo");
        contact.setEmail("juampiortega28@gmail.com");

        License license = new License();
        license.setName("MIT License");
        license.setUrl("https://opensource.org/licenses/MIT");

        Info info = new Info()
                .title("Mutant Detector API")
                .version("1.0.0")
                .description("API REST para detectar mutantes mediante análisis de secuencias de ADN. " +
                        "Magneto quiere reclutar mutantes para luchar contra los X-Men y necesita " +
                        "una forma automatizada de identificarlos.\n\n" +
                        "**Criterio de Detección:**\n" +
                        "Un humano es mutante si se encuentran **más de una secuencia** de 4 letras iguales " +
                        "(A, T, C, G) en dirección horizontal, vertical o diagonal.\n\n" +
                        "**Ejemplo de ADN Mutante:**\n" +
                        "```\n" +
                        "A T G C G A\n" +
                        "C A G T G C\n" +
                        "T T A T G T\n" +
                        "A G A A G G  ← Diagonal: A-A-A-A\n" +
                        "C C C C T A  ← Horizontal: C-C-C-C\n" +
                        "T C A C T G\n" +
                        "```")
                .contact(contact)
                .license(license);

        return new OpenAPI()
                .info(info)
                .servers(List.of(localServer));
    }
}