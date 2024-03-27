package hu.cry0.account.persistence.repository

import hu.cry0.account.persistence.entity.AccountEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param

interface AccountRepository : JpaRepository<AccountEntity, Long> {
    @Query(
        """
        SELECT
            ((SELECT COALESCE(SUM(t.amount), 0) FROM transaction t WHERE t.type='DEPOSIT' AND t.account_number=:accountNumber AND t.time_stamp <= CURRENT_TIMESTAMP)
                -
            (SELECT COALESCE(SUM(t.amount), 0) FROM transsaction t WHERE t.type='WITHDRAWAL' AND t.account_number=:accountNumber AND t.time_stamp <= CURRENT_TIMESTAMP))
        """, nativeQuery = true
    )
    fun getAccountBalance(@Param("accountNumber") accountNumber: Long): Long
}