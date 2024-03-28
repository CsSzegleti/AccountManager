package hu.cry0.account.config

import hu.cry0.account.controller.dto.NewTransactionRequest
import hu.cry0.account.model.Transaction
import org.modelmapper.Converter
import org.modelmapper.spi.MappingContext
import java.time.Instant

class NewTransactionRequestToTransactionConverter : Converter<NewTransactionRequest, Transaction> {
    override fun convert(context: MappingContext<NewTransactionRequest, Transaction>?): Transaction {
        return Transaction().apply {
            type = context?.source?.type
            amount = context?.source?.amount
            timeStamp = context?.source?.timeStamp?: Instant.now()
        }
    }
}