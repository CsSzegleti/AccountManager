package hu.cry0.account.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConfigurationPropertiesScan

@ConfigurationProperties(prefix = "api")
@ConfigurationPropertiesScan
class ApiProperties(
    val securityCheck: PathProperty,
) {
    class PathProperty(val basePath: String)
}