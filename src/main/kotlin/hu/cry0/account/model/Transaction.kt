package hu.cry0.account.model

import com.fasterxml.jackson.annotation.JsonBackReference
import com.fasterxml.jackson.annotation.JsonFormat
import com.fasterxml.jackson.annotation.JsonIgnore
import hu.cry0.account.model.validator.AccountActive
import java.time.DateTimeException
import java.time.Instant
import java.time.InstantSource
import java.util.*

data class Transaction(
    var id: UUID? = null,

    @AccountActive
    var accountNumber: String? = null,

    var type: TransactionType? = null,

    var amount: Long? = null,

    var timeStamp: Instant = Instant.now(),

    @JsonBackReference
    @JsonIgnore
    var account: Account? = null,
) {
    fun merge(other: Transaction) {
        accountNumber = other.accountNumber
        type = other.type
        amount = other.amount
    }
}