package hu.cry0.account.model.validator

import hu.cry0.account.model.AccountStatus
import jakarta.validation.Constraint
import jakarta.validation.Payload
import kotlin.reflect.KClass

@Target(AnnotationTarget.VALUE_PARAMETER, AnnotationTarget.FIELD)
@Retention(AnnotationRetention.RUNTIME)
@Constraint(validatedBy = [AccountStatusValidator::class])
annotation class AllowedStatus(
    val status: Array<AccountStatus> = [],
    val message: String = ERROR_MESSAGE,
    val groups: Array<KClass<Any>> = [],
    val payload: Array<KClass<Payload>> = []
) {
    companion object {
        private const val ERROR_MESSAGE = "error.account.state.notallowed"
    }
}
