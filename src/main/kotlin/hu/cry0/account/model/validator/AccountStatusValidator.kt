package hu.cry0.account.model.validator

import hu.cry0.account.model.AccountStatus
import hu.cry0.account.service.AccountService
import jakarta.validation.ConstraintValidator
import jakarta.validation.ConstraintValidatorContext

class AccountStatusValidator(
    private val accountService: AccountService
) : ConstraintValidator<AllowedStatus, String> {

    private var allowedStatuses: List<AccountStatus> = mutableListOf()

    override fun initialize(constraintAnnotation: AllowedStatus?) {
        allowedStatuses = constraintAnnotation?.status?.toMutableList() ?: mutableListOf()
    }

    override fun isValid(
        accountNumber: String?,
        context: ConstraintValidatorContext?,
    ): Boolean {
        return accountNumber?.let {
            allowedStatuses.contains(
                accountService.getAccountByNumber(accountNumber).status
            )
        } ?: false
    }
}