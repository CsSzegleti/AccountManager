package hu.cry0.account.service

import hu.cry0.account.model.Transaction
import hu.cry0.account.persistence.entity.TransactionEntity
import hu.cry0.account.persistence.repository.TransactionRepository
import org.modelmapper.ModelMapper
import org.springframework.stereotype.Service
import java.util.*

@Service
class TransactionService(
    private val transactionRepository: TransactionRepository,
    private val modelMapper: ModelMapper,
) {
    fun getAllByAccountNumber(accountNumber: Long): List<Transaction> {
        val result = transactionRepository.findAllByAccountNumber(accountNumber)

        return result.mapNotNull { modelMapper.map(it, Transaction::class.java) }
    }

    fun getById(transactionId: UUID, accountNumber: Long): Transaction =
        modelMapper.map(transactionRepository.findByIdAndAccountNumber(transactionId, accountNumber), Transaction::class.java)

    fun deleteById(transactionId: UUID) = transactionRepository.deleteById(transactionId)

    fun addTransaction(transaction: Transaction): Transaction {

        transaction.apply {
            id = null
        }

        val saveResult = transactionRepository.save(modelMapper.map(transaction, TransactionEntity::class.java))

        return modelMapper.map(saveResult, Transaction::class.java)
    }

    fun updateTransaction(transactionId: UUID, accountNumber: Long,  transaction: Transaction): Transaction {
        val existingTransaction = getById(transactionId, accountNumber)
        existingTransaction.merge(transaction)

        val savedEntity =
            transactionRepository.save(modelMapper.map(existingTransaction, TransactionEntity::class.java))

        return modelMapper.map(savedEntity, Transaction::class.java)
    }
}