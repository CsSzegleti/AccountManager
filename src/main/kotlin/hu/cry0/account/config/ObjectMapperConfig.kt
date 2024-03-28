package hu.cry0.account.config

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary
import org.springframework.core.annotation.Order
import java.time.Instant


@Configuration
class ObjectMapperConfig {

    @Bean
    @Primary
    @Order(value = 0)
    fun objectMapper(): ObjectMapper {
        val objectMapper = ObjectMapper()
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
        objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false)
        objectMapper.setSerializationInclusion(JsonInclude.Include.ALWAYS)
        val javaTimeModule = JavaTimeModule()
        javaTimeModule.addSerializer(Instant::class.java, InstantSerializer())
        objectMapper.registerModule(javaTimeModule)
        return objectMapper
    }
}