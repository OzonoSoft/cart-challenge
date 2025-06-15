package ar.com.ozonosoft.taller;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.retry.annotation.EnableRetry;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;

@SpringBootApplication
@ConfigurationProperties
@EnableScheduling
@EnableRetry
@EnableAsync
public class CartApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(CartApiApplication.class, args);
    }

    @Bean
    public OpenAPI customOpenAPI() {
        final String securitySchemeName = "BearerAuthentication";

        return new OpenAPI().info(new Info().title("Cart API")
                        .description("Proyecto Api Carrito")
                        .contact(new Contact().name("Gabriel").email("gabriel.ogh@gmail.com"))
                        .license(new License().name("Apache 2.0").url("http://springdoc.org")))
                        .addSecurityItem(new SecurityRequirement().addList(securitySchemeName)).components(
					new Components()
						.addSecuritySchemes(securitySchemeName, 
							new SecurityScheme()
								.name(securitySchemeName)
								.type(SecurityScheme.Type.HTTP)
								.scheme("bearer")
								.bearerFormat("JWT")
						)
				);
    }
}
