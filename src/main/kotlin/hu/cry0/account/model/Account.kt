package hu.cry0.account.model

import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonManagedReference
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull

open class Account(
    var accountNumber: String? = null,

    @NotNull
    @NotBlank
    var accountHolderName: String? = null,

    @JsonManagedReference
    @JsonIgnore
    var transactions: List<Transaction> = listOf(),

    var status: AccountStatus = AccountStatus.PENDING
)