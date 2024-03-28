package hu.cry0.account.controller.dto

import hu.cry0.account.model.TransactionType
import org.jetbrains.annotations.NotNull
import java.time.Instant

open class NewTransactionRequest {
    @NotNull
    var type: TransactionType? = null
    @NotNull
    var amount: Long? = null
    var timeStamp: Instant? = null
}