package hu.cry0.account.model

import com.fasterxml.jackson.annotation.JsonBackReference
import com.fasterxml.jackson.annotation.JsonIgnore
import java.time.Instant
import java.util.*

open class Transaction(
    var id: UUID? = null,

    @JsonIgnore
    var accountNumber: String? = null,

    var type: TransactionType? = null,

    var amount: Long? = null,

    var timeStamp: Instant? = Instant.now(),

    @JsonBackReference
    @JsonIgnore
    var account: Account? = null,
) {
    fun merge(other: Transaction) {
        type = other.type
        amount = other.amount
    }
}