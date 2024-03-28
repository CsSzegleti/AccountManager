package hu.cry0.account.config

import com.fasterxml.jackson.core.JsonGenerator
import com.fasterxml.jackson.databind.JsonSerializer
import com.fasterxml.jackson.databind.SerializerProvider
import java.time.Instant
import java.time.format.DateTimeFormatter

class InstantSerializer : JsonSerializer<Instant>() {
    private val formatter: DateTimeFormatter = DateTimeFormatter.ISO_INSTANT

    override fun serialize(value: Instant, gen: JsonGenerator, serializers: SerializerProvider) {
        gen.writeString(formatter.format(value))
    }
}