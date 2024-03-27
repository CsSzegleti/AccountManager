package hu.cry0.account.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConfigurationPropertiesScan

@ConfigurationProperties(prefix = "bank")
@ConfigurationPropertiesScan
class BankProperty(val accountNumberPrefix: Long?)