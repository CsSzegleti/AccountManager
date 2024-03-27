package hu.cry0.account.persistence

import hu.cry0.account.ApplicationContextProvider
import hu.cry0.account.config.BankProperty
import hu.cry0.account.persistence.entity.AccountEntity
import hu.cry0.account.persistence.exception.BankPrefixNotSetException
import hu.cry0.account.persistence.exception.NoActiveSessionException
import org.hibernate.engine.spi.SharedSessionContractImplementor
import org.hibernate.id.IdentifierGenerator

class AccountNumberGenerator : IdentifierGenerator {

    companion object {
        private const val ACCOUNT_NUMBER_PROPERTY = "account_number"
    }

    private var bankProperty: BankProperty? = ApplicationContextProvider.bankProperty

    override fun generate(session: SharedSessionContractImplementor?, `object`: Any?): Any {
        session?: throw NoActiveSessionException("error.no.active.session")

        var generatedNumber: Long

        do {
            generatedNumber = generateNumber()
        } while (isAccountNumberUnique(session, generatedNumber))

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

    private fun isAccountNumberUnique(session: SharedSessionContractImplementor, accountNumber: Long): Boolean {
        val criteriaBuilder = session.criteriaBuilder
        val criteriaQuery = criteriaBuilder.createQuery(Long::class.java)
        val root = criteriaQuery.from(AccountEntity::class.java)

        criteriaQuery.select(criteriaBuilder.count(root)).where(criteriaBuilder.equal(root?.get<Long>(ACCOUNT_NUMBER_PROPERTY), accountNumber))
        val query = session.createQuery(criteriaQuery)

        return query.singleResult > 0
    }

}