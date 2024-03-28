package hu.cry0.account.controller.dto

import hu.cry0.account.model.TransactionType
import org.jetbrains.annotations.NotNull

class TransactionModifyRequest {
    @NotNull
    var type: TransactionType? = null
    @NotNull
    var amount: Long? = null
}