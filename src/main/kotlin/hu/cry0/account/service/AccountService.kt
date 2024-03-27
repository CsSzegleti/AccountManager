package hu.cry0.account.service

import hu.cry0.account.client.security.SecurityCheckClient
import hu.cry0.account.client.security.model.SecurityCheckInputDto
import hu.cry0.account.client.security.model.SecurityCheckOutputDto
import hu.cry0.account.config.BankProperty
import hu.cry0.account.config.CallbackProperty
import hu.cry0.account.model.Account
import hu.cry0.account.model.AccountStatus
import hu.cry0.account.persistence.entity.AccountEntity
import hu.cry0.account.persistence.repository.AccountRepository
import org.modelmapper.ModelMapper
import org.springframework.stereotype.Service

@Service
class AccountService(
    private val accountRepository: AccountRepository,
    private val securityCheckClient: SecurityCheckClient,
    private val callbackProperty: CallbackProperty,
    private val modelMapper: ModelMapper,
) {
    fun getAll(): List<Account> {
        val results = accountRepository.findAll()

        return results.map { modelMapper.map(it, Account::class.java) }
    }

    fun getAccountByNumber(accountNumber: Long): Account {
        val result = accountRepository.getReferenceById(accountNumber)

        return modelMapper.map(result, Account::class.java)
    }

    fun deleteAccountByNumber(accountNumber: Long) = accountRepository.deleteById(accountNumber)

    fun initiateAccountRegistration(accountHolderName: String): Account {
        val accountToSave = Account().apply {
            this.accountHolderName = accountHolderName
        }

        val savedAccountEntity = accountRepository.save(modelMapper.map(accountToSave, AccountEntity::class.java) )

        securityCheckClient.performCheck(
            SecurityCheckInputDto(
                accountHolderName = savedAccountEntity.accountHolderName!!,
                accountNumber = savedAccountEntity.accountNumber.toString(),
                callbackUrl = callbackProperty.url
            )
        )
        return modelMapper.map(savedAccountEntity, Account::class.java)
    }

    fun finalizeAccountRegistration(securityCheckOutputDto: SecurityCheckOutputDto) {
        val existingAccount = modelMapper.map(accountRepository.getReferenceById(securityCheckOutputDto.accountNumber.toLong()), Account::class.java)

        if (securityCheckOutputDto.isSecurityCheckSuccess) {
            existingAccount.status = AccountStatus.ACTIVE
        } else {
            existingAccount.status = AccountStatus.REJECTED
        }

        accountRepository.save(modelMapper.map(existingAccount, AccountEntity::class.java))
    }

}