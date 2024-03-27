package hu.cry0.account.persistence.repository

import hu.cry0.account.persistence.entity.TransactionEntity
import org.springframework.data.jpa.repository.JpaRepository
import java.util.UUID

interface TransactionRepository : JpaRepository<TransactionEntity, UUID> {
    fun findAllByAccountNumber(accountNumber: Long): List<TransactionEntity>
}