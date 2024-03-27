package hu.cry0.account.client.security.model

data class SecurityCheckOutputDto(
    var accountNumber: String,
    var isSecurityCheckSuccess: Boolean,
)