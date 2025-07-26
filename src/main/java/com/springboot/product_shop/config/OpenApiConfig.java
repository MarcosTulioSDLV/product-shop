package com.springboot.product_shop.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(info = @Info(
        title = "Product Shop System Rest API",
        description = "This REST API manages products, customers, and their orders in an e-commerce product shop, providing full CRUD operations to support typical online shopping functionalities.",
        version = "1.0",
        contact = @Contact(
                name = "Marcos Soto",
                url = "https://ar.linkedin.com/in/marcos-tulio-soto-de-la-vega-8a6b9668",
                email = "mtsotodelavega@gmail.com"
        ),
        license = @License(name = "GNU General Public License", url = "https://www.gnu.org/licenses/gpl-3.0.html")
))
public class OpenApiConfig {

        //For Basic Authentication
        /*@Bean
        public OpenAPI customOpenAPI() {
                return new OpenAPI()
                        .addSecurityItem(new SecurityRequirement().addList("basicAuth"))
                        .components(new io.swagger.v3.oas.models.Components()
                                .addSecuritySchemes("basicAuth", new SecurityScheme()
                                        .type(SecurityScheme.Type.HTTP)
                                        .scheme("basic")));
        }*/

        //Only For JWT
        @Bean
        public OpenAPI customOpenAPI() {
                return new OpenAPI()
                        .addSecurityItem(new SecurityRequirement().addList("Bearer Authentication"))
                        .components(new io.swagger.v3.oas.models.Components()
                                .addSecuritySchemes("Bearer Authentication", new SecurityScheme()
                                        .type(SecurityScheme.Type.HTTP)
                                        .scheme("bearer")
                                        .bearerFormat("JWT")));
        }
}
