package com.food_delivery.g1_a_order.config;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.info.BuildProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

        @Autowired
        BuildProperties buildProperties;

        @Bean
        public OpenAPI customOpenAPI() {
                // final String securitySchemeName = "bearerAuth";
                return new OpenAPI()
                                .info(new Info().title("Order Service")
                                                .description("This is order service use for managing order of food delivery application.")
                                                .version(buildProperties.getVersion())
                                                .license(new License().name("Apache 2.0").url("http://springdoc.org")))

                                .externalDocs(new ExternalDocumentation()
                                                .description("SpringBoot Wiki Documentation")
                                                .url("https://springboot.wiki.github.org/docs"))

                                .externalDocs(
                                                new ExternalDocumentation()
                                                                .description("DJIV-ITEP camp training")
                                                                .url("https://github.com/DJIV-ITEP/G1_A_Order"))

                // .addSecurityItem(new SecurityRequirement().addList(securitySchemeName))
                // .components(
                // new Components()
                // .addSecuritySchemes(securitySchemeName,
                // new SecurityScheme()
                // .name(securitySchemeName)
                // .type(SecurityScheme.Type.HTTP)
                // .scheme("bearer")
                // .bearerFormat("JWT")))
                ;
        }
}