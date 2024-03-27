package hu.cry0.account.model.validator

import hu.cry0.account.model.AccountStatus
import hu.cry0.account.service.AccountService
import jakarta.validation.ConstraintValidator
import jakarta.validation.ConstraintValidatorContext

class ActiveAccountValidator(
    private val accountService: AccountService
) : ConstraintValidator<AccountActive, String> {

    override fun isValid(accountNumber: String?, validationContext: ConstraintValidatorContext?): Boolean {
        return accountNumber?.let {
            accountService.getAccountByNumber(accountNumber).status == AccountStatus.ACTIVE
        } ?: false
    }
}