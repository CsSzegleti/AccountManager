package hu.cry0.account.persistence.repository

import hu.cry0.account.persistence.entity.AccountEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param

interface AccountRepository : JpaRepository<AccountEntity, String> {
    @Query(
        """
        SELECT
            ((SELECT COALESCE(SUM(t.amount), 0) FROM transaction t WHERE t.transaction_type='DEPOSIT' AND t.account_number=:accountNumber AND t.time_stamp <= CURRENT_TIMESTAMP AND status <> 'DELETED')
                -
            (SELECT COALESCE(SUM(t.amount), 0) FROM transaction t WHERE t.transaction_type='WITHDRAWAL' AND t.account_number=:accountNumber AND t.time_stamp <= CURRENT_TIMESTAMP AND status <> 'DELETED'))
        """, nativeQuery = true
    )
    fun getAccountBalance(@Param("accountNumber") accountNumber: String): Long
}