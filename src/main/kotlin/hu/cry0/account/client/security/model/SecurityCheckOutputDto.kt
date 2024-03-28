package hu.cry0.account.client.security.model

import hu.cry0.account.model.validator.AccountPending

data class SecurityCheckOutputDto(
    @field:AccountPending
    var accountNumber: String,
    var isSecurityCheckSuccess: Boolean,
)