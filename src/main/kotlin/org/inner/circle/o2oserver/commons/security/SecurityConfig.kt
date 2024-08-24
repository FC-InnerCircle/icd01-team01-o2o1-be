package org.inner.circle.o2oserver.commons.security

import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.http.ResponseCookie
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.AuthenticationSuccessHandler
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.CorsConfigurationSource
import org.springframework.web.cors.CorsUtils
import org.springframework.web.cors.UrlBasedCorsConfigurationSource
import org.springframework.web.util.UriComponentsBuilder

@Configuration
@EnableWebSecurity
class SecurityConfig(
    private val tokenProvider: TokenProvider,
    private val authFilter: AuthFilter,
    private val entryPoint: CustomAuthenticationEntryPoint,
    private val customAccessDemniedHandler: CustomAccessDemniedHandler
) {
    private val log = KotlinLogging.logger {}

    @Value("\${jwt.domain}")
    private lateinit var domain: String

    @Value("\${client.url}")
    private lateinit var clientUrl: String

    @Value("\${security.domain}")
    private lateinit var securityDomain: List<String>

    @Bean
    fun filterChain(http: HttpSecurity): SecurityFilterChain {
        return http
            .csrf { csrf -> csrf.disable() }
            .cors { cors -> cors.configurationSource(corsConfig()) }
            .httpBasic { httpBasic -> httpBasic.disable() }
            .sessionManagement { sessionManagement ->
                sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            }
            .formLogin { formLogin -> formLogin.disable() }
            .oauth2Login { oauth2Login ->
                oauth2Login
                    .defaultSuccessUrl("/")
                    .successHandler(sucessHandler())
                    .userInfoEndpoint { userInfoEndpoint ->
                        userInfoEndpoint.userService(oauthService)
                    }
                    .redirectionEndpoint { redirectionEndpoint ->
                        redirectionEndpoint.baseUri("/login/oauth2/code/*")
                    }
            }
            .authorizeHttpRequests { authorizeRequests ->
                authorizeRequests
                    .requestMatchers(
                        "/api-docs/**",
                        "/swagger-ui/**",
                        "/swagger-ui.html",
                        "/v3/api-docs/**",
                        "/queue/**",
                        "/auth/**",
                        "/oauth/**",
                        "/actuator/**",
                        "/error",
                        "/notification/**"
                    ).permitAll()
                    .requestMatchers(
                        HttpMethod.GET,
                        "/post/**",
                        "/mate/**"
                    ).permitAll()
                    .requestMatchers(CorsUtils::isPreFlightRequest).permitAll()
                    .anyRequest().authenticated()
            }
            .addFilterBefore(authFilter, UsernamePasswordAuthenticationFilter::class.java)
            .exceptionHandling { exceptionHandling ->
                exceptionHandling
                    .authenticationEntryPoint(entryPoint)
                    .accessDeniedHandler(customAccessDemniedHandler)
            }
            .build()
    }

    @Bean
    fun sucessHandler(): AuthenticationSuccessHandler {
        return AuthenticationSuccessHandler { request, response, authentication ->
            val accessToken = tokenProvider.generateAccessToken(authentication)
            response.addHeader("Authorization", "Bearer $accessToken")
            val refreshToken = tokenProvider.generateRefreshToken(authentication, accessToken)
            val cookie = createCookie(refreshToken)
            response.addHeader("Set-Cookie", cookie.toString())
            log.info { "success login : ${authentication.name}" }
            val principal: PrincipalDetails = authentication.principal as PrincipalDetails
            val redirectPath = if (principal.isCredentialsNonExpired) "/" else "/"
            val uriComponents =
                UriComponentsBuilder.fromUriString(clientUrl)
                    .path(redirectPath)
                    .build()
                    .also { response.sendRedirect(it.toString()) }
            log.info { "redirect to : ${uriComponents.path}" }
        }
    }

    private fun createCookie(refreshToken: String): ResponseCookie {
        return ResponseCookie.from("refreshToken", refreshToken)
            .httpOnly(true)
            .domain(domain)
            .sameSite("None")
            .secure(true)
            .path("/")
            .maxAge(604800)
            .build()
    }

    @Bean
    fun passwordEncoder() = BCryptPasswordEncoder()

    fun corsConfig(): CorsConfigurationSource {
        val config =
            CorsConfiguration().apply {
                allowCredentials = true
                allowedMethods = listOf("HEAD", "GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS")
                allowedHeaders =
                    listOf(
                        "Content-Type",
                        "Cache-Control",
                        "X-Requested-With",
                        "Accept",
                        "Origin",
                        "Access-Control-Request-Method",
                        "Access-Control-Request-Headers",
                        "Authorization",
                        "Set-Cookie"
                    )
                exposedHeaders = listOf("Authorization", "Set-Cookie")
                allowedOrigins = securityDomain
                allowedOriginPatterns = listOf("http://localhost:3000", "https://*.wavemate.lol")
            }
        return UrlBasedCorsConfigurationSource().apply {
            registerCorsConfiguration("/**", config)
        }
    }
}
