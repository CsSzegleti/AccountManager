package hu.cry0.account

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.boot.runApplication

@SpringBootApplication
@ConfigurationPropertiesScan("hu.cry0.account.config")
class AccountApplication

fun main(args: Array<String>) {
	runApplication<AccountApplication>(*args)
}
