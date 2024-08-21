package org.inner.circle.o2oserver.common.config

import com.mongodb.ConnectionString
import com.mongodb.MongoClientSettings
import com.mongodb.client.MongoClient
import com.mongodb.client.MongoClients
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.mongodb.config.AbstractMongoClientConfiguration
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.core.SimpleMongoClientDatabaseFactory

@Configuration
class MongoConfig: AbstractMongoClientConfiguration() {
    @Value("\${spring.data.mongodb.uri}")
    private lateinit var uri: String

    override fun getDatabaseName(): String {
        return "o2o"
    }

    override fun mongoClient(): MongoClient {
        val connectionUrl = ConnectionString(uri)
        val settings =
            MongoClientSettings.builder()
                .applyConnectionString(connectionUrl)
                .build()
        return MongoClients.create(settings)
    }

    @Bean
    fun mongoTemplate(): MongoTemplate {
        return MongoTemplate(SimpleMongoClientDatabaseFactory(mongoClient(), databaseName))
    }
}