package org.inner.circle.o2oserver.commons.config

import io.swagger.v3.oas.models.Components
import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.info.Info
import io.swagger.v3.oas.models.security.SecurityRequirement
import io.swagger.v3.oas.models.security.SecurityScheme
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class APIDocConfig {
    @Bean
    fun openApi(): OpenAPI =
        OpenAPI()
            .components(Components().addSecuritySchemes("bearerAuth", securityScheme()))
            .security(listOf(securityRequirement()))
            .info(apiInfo())

    private fun apiInfo(): Info =
        Info()
            .title("Wavemate API 문서")
            .description("API 문서 종류 , Auth, Post, Comment, Mate, Member, Reaction")
            .version("v1.0.0")

    private fun securityScheme() =
        SecurityScheme()
            .type(SecurityScheme.Type.HTTP).scheme("bearer").bearerFormat("JWT")
            .`in`(SecurityScheme.In.HEADER).name("Authorization")

    private fun securityRequirement() =
        SecurityRequirement()
            .addList("bearerAuth")
}
