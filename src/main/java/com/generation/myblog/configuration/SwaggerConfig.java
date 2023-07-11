package com.generation.myblog.configuration;

import org.springdoc.core.customizers.OpenApiCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.responses.ApiResponse;
import io.swagger.v3.oas.models.responses.ApiResponses;

@Configuration
public class SwaggerConfig {

	@Bean
    OpenAPI springMyBlogOpenAPI() {
        return new OpenAPI()
            .info(new Info()
                .title("My Blog Project")
                .description("Projeto Blog Pessoal - Generation Brasil")
                .version("v0.0.1")
                .contact(new Contact()
                        .name("Caroline Braz")
                        .url("https://github.com/carolinebraz"))
                .license(new License()
                    .name("Generation Brasil")
                    .url("https://brazil.generation.org/")))
            .externalDocs(new ExternalDocumentation()
                .description("Repositório do Projeto no GitHub")
                .url("https://github.com/carolinebraz/my-blog"));
    }

	@Bean
	public OpenApiCustomizer customerGlobalHeaderOpenApiCustomiser() {

		return openApi -> {
			openApi.getPaths().values().forEach(pathItem -> pathItem.readOperations().forEach(operation -> {

				ApiResponses apiResponses = operation.getResponses();

				apiResponses.addApiResponse("200", createApiResponse("✅ Sucesso"));
				apiResponses.addApiResponse("201", createApiResponse("😁 Objeto criado"));
				apiResponses.addApiResponse("204", createApiResponse("👻 Objeto apagado"));
				apiResponses.addApiResponse("400", createApiResponse("❗ Falha na requisição"));
				apiResponses.addApiResponse("401", createApiResponse("🚫 Acesso não autorizado"));
				apiResponses.addApiResponse("403", createApiResponse("⛔ Acesso não permitido"));
				apiResponses.addApiResponse("404", createApiResponse("❌ Objeto não encontrado"));
				apiResponses.addApiResponse("500", createApiResponse("😵 Falha na aplicação"));

			}));
		};
	}

	private ApiResponse createApiResponse(String message) {

		return new ApiResponse().description(message);

	}
}
