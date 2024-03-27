package hu.cry0.account.config

import hu.cry0.account.client.security.SecurityCheckClient
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.reactive.function.client.WebClient

@Configuration
class ClientConfig(private val apiProperties: ApiProperty) {

    @Bean
    fun securityCheckClient(): SecurityCheckClient {
        val webClient = WebClient.create(apiProperties.securityCheck.basePath)
        return SecurityCheckClient(webClient)
    }
}