package org.inner.circle.o2oserver.commons.security

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.CorsConfigurationSource
import org.springframework.web.cors.CorsUtils
import org.springframework.web.cors.UrlBasedCorsConfigurationSource

@Configuration
@EnableWebSecurity
class SecurityConfig(
    private val authFilter: AuthFilter,
    private val entryPoint: CustomAuthenticationEntryPoint,
    private val customAccessDemniedHandler: CustomAccessDemniedHandler,
) {
    @Bean
    fun filterChain(http: HttpSecurity): SecurityFilterChain {
        return http.csrf { csrf -> csrf.disable() }.cors { cors -> cors.configurationSource(corsConfig()) }
            .httpBasic { httpBasic -> httpBasic.disable() }.sessionManagement { sessionManagement ->
                sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            }.formLogin { formLogin -> formLogin.disable() }.authorizeHttpRequests { authorizeRequests ->
                authorizeRequests.requestMatchers(
                        "/api-docs/**",
                        "/swagger-ui/**",
                        "/swagger-ui.html",
                        "/v3/api-docs/**",
                        "/queue/**",
                        "/auth/**",
                        "/oauth/**",
                        "/actuator/**",
                        "/error",
                        "/notification/**",
                        "/api/v1/login",
                        "/api/v1/store/**",
                    ).permitAll().requestMatchers(
                        HttpMethod.GET,
                        "/post/**",
                        "/mate/**",
                    ).permitAll().requestMatchers(CorsUtils::isPreFlightRequest).permitAll().anyRequest().authenticated()
            }.addFilterBefore(authFilter, UsernamePasswordAuthenticationFilter::class.java).exceptionHandling { exceptionHandling ->
                exceptionHandling.authenticationEntryPoint(entryPoint).accessDeniedHandler(customAccessDemniedHandler)
            }.build()
    }

    /**
     * CORS 설정은 임시 보류
     */
    fun corsConfig(): CorsConfigurationSource {
        val config = CorsConfiguration().apply {
            allowCredentials = true
            allowedMethods = listOf("HEAD", "GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS")
            allowedHeaders = listOf(
                "Content-Type",
                "Cache-Control",
                "X-Requested-With",
                "Accept",
                "Origin",
                "Access-Control-Request-Method",
                "Access-Control-Request-Headers",
                "Authorization",
                "Set-Cookie",
                "RefreshAuth",
                "refreshToken",
            )
            exposedHeaders = listOf("Authorization", "Set-Cookie")
            allowedOriginPatterns = listOf(
                "http://localhost:3000",
                "https://icd01-team01-o2o1-fe.vercel.app",
            )
        }
        return UrlBasedCorsConfigurationSource().apply {
            registerCorsConfiguration("/**", config)
        }
    }

    @Bean
    fun passwordEncoder() = BCryptPasswordEncoder()
}
