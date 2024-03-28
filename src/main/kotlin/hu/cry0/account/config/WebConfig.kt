package hu.cry0.account.config

import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.config.annotation.CorsRegistry
import org.springframework.web.servlet.config.annotation.EnableWebMvc
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@Configuration
@EnableWebMvc
class WebConfig(private val apiProperty: ApiProperty) : WebMvcConfigurer {
    override fun addCorsMappings(registry: CorsRegistry) {
        registry.addMapping("/api/v1/account/finalize").allowedOrigins(apiProperty.securityCheck.basePath)
        registry.addMapping("/**").allowedOrigins("*")
    }
}