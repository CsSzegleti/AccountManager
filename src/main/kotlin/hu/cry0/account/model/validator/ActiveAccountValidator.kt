package hu.cry0.account.model.validator

import hu.cry0.account.persistence.repository.AccountRepository
import jakarta.validation.ConstraintValidator
import jakarta.validation.ConstraintValidatorContext

class ActiveAccountValidator(
    private val accountRepository: AccountRepository
) : ConstraintValidator<AccountActive, Long> {

    override fun isValid(accountNumber: Long?, validationContext: ConstraintValidatorContext?): Boolean {
        return accountNumber?.let {
            accountRepository.existsById(accountNumber)
        } ?: false
    }
}