package hu.cry0.account.controller.dto

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull

class AccountInitRequest {
    @NotNull
    @NotBlank
    val accountHolderName: String? = null
}