package hu.cry0.account.client.security.model

import hu.cry0.account.model.AccountStatus
import hu.cry0.account.model.validator.AllowedStatus

data class SecurityCheckOutputDto(
    @field:AllowedStatus(status = [AccountStatus.PENDING], message = "error.state.not.pending")
    var accountNumber: String,
    var isSecurityCheckSuccess: Boolean,
)