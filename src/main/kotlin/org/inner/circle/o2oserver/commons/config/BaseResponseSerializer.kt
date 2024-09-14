package org.inner.circle.o2oserver.commons.config

import com.fasterxml.jackson.annotation.JsonAutoDetect
import com.fasterxml.jackson.annotation.PropertyAccessor
import com.fasterxml.jackson.core.JsonGenerator
import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializerProvider
import com.fasterxml.jackson.databind.ser.std.StdSerializer
import com.fasterxml.jackson.module.kotlin.KotlinModule
import org.inner.circle.o2oserver.commons.response.BaseResponse
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class BaseResponseSerializer : StdSerializer<BaseResponse<*>>(BaseResponse::class.java) {
    override fun serialize(value: BaseResponse<*>, gen: JsonGenerator, provider: SerializerProvider) {
        gen.writeStartObject()
        gen.writeObjectField("response", value.response)
        gen.writeNumberField("statusCode", value.statusCode)
        gen.writeStringField("msg", value.msg)
        gen.writeEndObject()
    }

    @Bean
    fun objectMapper(): ObjectMapper {
        return ObjectMapper().apply {
            registerModule(KotlinModule.Builder().build())
            configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
            setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY)
        }
    }
}
