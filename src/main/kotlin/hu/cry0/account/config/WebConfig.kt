package hu.cry0.account.config

import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.config.annotation.CorsRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@Configuration
class WebConfig(private val apiProperty: ApiProperty) : WebMvcConfigurer {
    override fun addCorsMappings(registry: CorsRegistry) {
        registry.addMapping("/**").allowedOrigins("*")
        registry.addMapping("/api/v1/account/finalize").allowedOrigins(apiProperty.securityCheck.basePath)
    }
}