package com.checkout.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class OpenApiConfig {

    @Value("${local.server.url}")
    private String url;


    @Bean
    public OpenAPI myOpenAPI() {
        Server devServer = new Server();
        devServer.setUrl(url);
        devServer.setDescription("Server URL in Development environment");

        Contact contact = new Contact();
        contact.setEmail("prashgec@gmail.com");
        contact.setName("Prashant Sharma");

        Info info = new Info()
                .title("Payment Gateway API")
                .version("1.0")
                .contact(contact)
                .description("This API simulates simple payment-gateway's merchant wrapper.");

        return new OpenAPI().info(info).servers(List.of(devServer));
    }
}
