package org.inner.circle.o2oserver.commons.config

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.client.SimpleClientHttpRequestFactory
import org.springframework.web.client.RestClient
import org.springframework.web.client.RestTemplate
import org.springframework.web.client.support.RestClientAdapter
import org.springframework.web.service.invoker.HttpServiceProxyFactory
import java.net.HttpURLConnection
import java.security.SecureRandom
import java.security.cert.X509Certificate
import javax.net.ssl.HttpsURLConnection
import javax.net.ssl.SSLContext
import javax.net.ssl.TrustManager
import javax.net.ssl.X509TrustManager

@Configuration
class RestClientConfig {
    @Value("\${2team.base.url}")
    lateinit var baseUrl: String

    @Bean
    fun createWebClient(): RestClient {
        return RestClient.create(createRestTemplate())
    }

    @Bean
    fun createWebInterface(): WebInterface {
        // 2팀 url이 정해지면 넣어야합니다
        println("baseUrl: $baseUrl")
        val createWebClient = RestClient.builder().baseUrl(baseUrl).build()
        val adapter = RestClientAdapter.create(createWebClient)
        val factory = HttpServiceProxyFactory.builderFor(adapter).build()
        return factory.createClient(WebInterface::class.java)
    }

    fun createRestTemplate(): RestTemplate {
        val sslContext =
            SSLContext.getInstance("TLS").apply {
                init(null, arrayOf<TrustManager>(TrustAllManager()), SecureRandom())
            }
        HttpsURLConnection.setDefaultSSLSocketFactory(sslContext.socketFactory)
        HttpsURLConnection.setDefaultHostnameVerifier { _, _ -> true }

        return RestTemplate(getFactory(sslContext))
    }

    private fun getFactory(sslContext: SSLContext): CustomClientHttpRequestFactory {
        return CustomClientHttpRequestFactory(sslContext).apply {
            setConnectTimeout(2000)
            setReadTimeout(2000)
        }
    }

    class CustomClientHttpRequestFactory(private val sslContext: SSLContext) : SimpleClientHttpRequestFactory() {
        override fun prepareConnection(connection: HttpURLConnection, httpMethod: String) {
            if (connection is HttpsURLConnection) {
                connection.sslSocketFactory = sslContext.socketFactory
            }
            super.prepareConnection(connection, httpMethod)
        }
    }

    class TrustAllManager : X509TrustManager {
        override fun getAcceptedIssuers(): Array<X509Certificate> = emptyArray()

        override fun checkClientTrusted(certs: Array<X509Certificate>, authType: String) {}

        override fun checkServerTrusted(certs: Array<X509Certificate>, authType: String) {}
    }
}
