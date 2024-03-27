package hu.cry0.account.client.security.model

data class SecurityCheckInputDto(
    var accountHolderName: String,
    var accountNumber: String,
    var callbackUrl: String,
)