package org.inner.circle.o2oserver.commons.config

import org.slf4j.LoggerFactory
import org.springframework.data.redis.connection.Message
import org.springframework.data.redis.connection.MessageListener
import org.springframework.data.redis.listener.PatternTopic
import org.springframework.data.redis.listener.RedisMessageListenerContainer

abstract class RedisMessageListener (
    private val redisMessageListenerContainer: RedisMessageListenerContainer
) : MessageListener {
    private val log = LoggerFactory.getLogger(this::class.java)

    abstract fun getTopicPattern(): String

    fun subscribe(key: String) {
        log.info ( "Subscribe to: $key" )
        log.info ( "Topic pattern: ${getTopicPattern() + key}" )
        redisMessageListenerContainer.addMessageListener(this, PatternTopic(getTopicPattern() + key))
    }

    fun unsubscribe(key: String) {
        log.info ( "Unsubscribe from: $key" )
        redisMessageListenerContainer.removeMessageListener(null, PatternTopic(getTopicPattern() + key))
    }

    override fun onMessage(
        message: Message,
        pattern: ByteArray?
    ) {
        try {
            val deserializedMessage = deserializeMessage(message)
            val key = getKeyFromPattern(pattern)
            handleMessage(key, deserializedMessage)
        } catch (e: Exception) {
            log.error( "Failed to handle message : {}", e.message )
        }
    }

    abstract fun deserializeMessage(message: Message): Any

    abstract fun handleMessage(
        key: String,
        message: Any
    )

    abstract fun getKeyFromPattern(pattern: ByteArray?): String
}
