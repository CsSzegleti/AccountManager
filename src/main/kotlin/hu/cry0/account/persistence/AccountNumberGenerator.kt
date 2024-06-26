package hu.cry0.account.persistence

import hu.cry0.account.ApplicationContextProvider
import hu.cry0.account.persistence.exception.BankPrefixNotSetException
import hu.cry0.account.persistence.exception.NoActiveSessionException
import org.hibernate.engine.spi.SharedSessionContractImplementor
import org.hibernate.id.IdentifierGenerator

class AccountNumberGenerator : IdentifierGenerator {

    companion object {
        private const val ACCOUNT_NUMBER_PROPERTY = "accountNumber"
        private const val ACCOUNT_NUMBER_COUNT_QUERY = "SELECT COUNT(*) FROM account WHERE account_number = :accountNumber"
    }

    override fun generate(session: SharedSessionContractImplementor?, `object`: Any?): Any {
        session?: throw NoActiveSessionException("error.no.active.session")

        var generatedNumber: String

        do {
            generatedNumber = generateNumber()
        } while (isAccountNumberUnique(session, generatedNumber))

        return generatedNumber
    }

    private fun generateNumber(): String {
        return ApplicationContextProvider.bankProperty?.accountNumberPrefix?.let {
            return StringBuilder().run {
                append(it)
                while (length < 24) {
                    append((0..9).random().toString())
                }
                toString()
            }
        } ?: throw BankPrefixNotSetException("error.bank.prefix.notset")
    }

    private fun isAccountNumberUnique(session: SharedSessionContractImplementor, accountNumber: String): Boolean {
        val query = session.createNativeQuery(ACCOUNT_NUMBER_COUNT_QUERY) // have to use native query, because of the soft delete
        query.setParameter(ACCOUNT_NUMBER_PROPERTY, accountNumber)

        val result = query.singleResult as Long

        return result > 0L
    }
}