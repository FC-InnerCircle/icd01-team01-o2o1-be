package org.inner.circle.o2oserver.order.application

import com.fasterxml.jackson.databind.ObjectMapper
import org.inner.circle.o2oserver.commons.config.RedisMessageListener
import org.inner.circle.o2oserver.commons.enums.TopicType
import org.inner.circle.o2oserver.order.presentation.dto.OrderDeliveryResponse
import org.slf4j.LoggerFactory
import org.springframework.data.redis.connection.Message
import org.springframework.data.redis.listener.RedisMessageListenerContainer
import org.springframework.stereotype.Component
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter
import java.util.concurrent.ConcurrentHashMap

@Component
class DeliveryListenerFacade(
    redisMessageListenerContainer: RedisMessageListenerContainer,
) : RedisMessageListener(redisMessageListenerContainer) {
    private val chatEmitters = ConcurrentHashMap<String, ConcurrentHashMap<SseEmitter, Long>>()
    private val log = LoggerFactory.getLogger(this::class.java)
    private val objectMapper = ObjectMapper()

    override fun getTopicPattern(): String = TopicType.ORDER.typeName

    override fun deserializeMessage(message: Message): OrderDeliveryResponse.OrderDelivery {
        return objectMapper.readValue(message.body, OrderDeliveryResponse.OrderDelivery::class.java)
    }

    override fun handleMessage(key: String, message: Any) {
        val roomEmitters = chatEmitters[key] ?: return
        roomEmitters.keys.forEach { emitter ->
            try {
                log.info("Send message to $key: $message")
                emitter.send(SseEmitter.event().name("deliveryLocationUpdate").data(message))
            } catch (e: Exception) {
                emitter.completeWithError(e)
            }
        }
    }

    override fun getKeyFromPattern(pattern: ByteArray?): String {
        val patternString = String(pattern ?: return "")
        return patternString.removePrefix(TopicType.ORDER.typeName + ":")
    }

    fun deliverySubscribe(orderId: Long): SseEmitter {
        subscribe(":$orderId")

        val emitter = SseEmitter(Long.MAX_VALUE)

        val newEmitterMap = ConcurrentHashMap<SseEmitter, Long>()
        newEmitterMap[emitter] = orderId

        chatEmitters[orderId.toString()] = newEmitterMap

        emitter.onCompletion { unsubscribe("chatroom:$orderId", emitter) }
        emitter.onTimeout { unsubscribe("chatroom:$orderId", emitter) }
        emitter.onError { unsubscribe("chatroom:$orderId", emitter) }
        emitter.send(SseEmitter.event().name("connected").data("Connected"))
        return emitter
    }

    private fun unsubscribe(orderId: String, emitter: SseEmitter) {
        val roomEmitters = chatEmitters[orderId] ?: return
        roomEmitters.remove(emitter)
        if (roomEmitters.isEmpty()) {
            chatEmitters.remove(orderId)
            unsubscribe(orderId)
        }
    }
}
