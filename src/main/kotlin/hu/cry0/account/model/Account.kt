package hu.cry0.account.model

import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonManagedReference
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull

data class Account(
    var accountNumber: Long? = null,

    @NotNull
    @NotBlank
    var accountHolderName: String? = null,

    var balance: Long = 0,

    @JsonManagedReference
    var transactions: List<Transaction> = listOf(),

    @JsonIgnore
    var status: AccountStatus = AccountStatus.PENDING
)