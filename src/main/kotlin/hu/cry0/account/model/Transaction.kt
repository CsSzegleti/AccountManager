package hu.cry0.account.model

import com.fasterxml.jackson.annotation.JsonBackReference
import java.time.Instant
import java.util.*

data class Transaction(
    var id: UUID? = null,

    var accountNumber: Long? = null,

    var type: TransactionType? = null,

    var amount: Long? = null,

    val timeStamp: Instant? = null,

    @JsonBackReference
    var account: Account? = null,
)