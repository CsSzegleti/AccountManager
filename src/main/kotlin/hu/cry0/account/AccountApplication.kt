package hu.cry0.account

import io.swagger.v3.oas.annotations.OpenAPIDefinition
import io.swagger.v3.oas.annotations.info.Contact
import io.swagger.v3.oas.annotations.info.Info
import io.swagger.v3.oas.annotations.servers.Server
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.boot.runApplication

@SpringBootApplication
@ConfigurationPropertiesScan("hu.cry0.account.config")
@OpenAPIDefinition(
    servers = [Server(
        url = "http://localhost:8080", description = "local dev"
    ), Server(
        url = "http://172.17.0.1:8080", description = "docker dev"
    )], info = Info(
        title = "Account management REST API",
        version = "v1",
        description = "Take-home assignment for account management REST API",
        contact = Contact(
            name = "Csongor Szegleti", email = "szegleti_csongor@4dsoft.hu"
        )
    )
)
class AccountApplication

fun main(args: Array<String>) {
    runApplication<AccountApplication>(*args)
}
