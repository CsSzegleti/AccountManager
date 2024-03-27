package hu.cry0.account.persistence

import hu.cry0.account.ApplicationContextProvider
import hu.cry0.account.config.BankProperty
import hu.cry0.account.persistence.exception.BankPrefixNotSetException
import org.hibernate.engine.spi.SharedSessionContractImplementor
import org.hibernate.id.IdentifierGenerator

class AccountNumberGenerator : IdentifierGenerator {

    companion object {
        private const val ACCOUNT_NUMBER_COUNT_QUERY = "SELECT COUNT(*) where account_number = %d"
    }

    private var bankProperty: BankProperty? = ApplicationContextProvider.bankProperty

    override fun generate(session: SharedSessionContractImplementor?, `object`: Any?): Any {
        var generatedNumber: Long
        var isUnique: Boolean

        do {
            generatedNumber = generateNumber()
            isUnique =
                session?.createQuery(String.format(ACCOUNT_NUMBER_COUNT_QUERY, generateNumber()))?.stream()?.findFirst()
                    ?.get() == 0
        } while (!isUnique)

        return generatedNumber
    }

    private fun generateNumber(): Long {
        return bankProperty?.accountNumberPrefix?.let {
            return StringBuilder().run {
                append(it)
                while (length < 24) {
                    append((0..9).random().toString())
                }
                toString()
            }.toLong()
        } ?: throw BankPrefixNotSetException("error.bank.prefix.notset")
    }

}