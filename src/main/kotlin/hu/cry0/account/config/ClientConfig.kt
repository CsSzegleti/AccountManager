package hu.cry0.account.config

import hu.cry0.account.client.security.SecurityCheckClient
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Scope
import org.springframework.context.annotation.ScopedProxyMode
import org.springframework.web.context.WebApplicationContext

@Configuration
class ClientConfig(private val apiProperties: ApiProperty) {

    @Bean
    @Scope(WebApplicationContext.SCOPE_REQUEST, proxyMode = ScopedProxyMode.TARGET_CLASS)
    fun securityCheckClient(): SecurityCheckClient {
        return SecurityCheckClient(apiProperties.securityCheck.basePath)
    }
}