package hu.cry0.account.model.validator

import jakarta.validation.Constraint
import jakarta.validation.Payload
import kotlin.reflect.KClass

@Target(AnnotationTarget.FIELD)
@Retention(AnnotationRetention.RUNTIME)
@Constraint(validatedBy = [PendingAccountValidator::class])
annotation class AccountPending(
    val message: String = ERROR_MESSAGE,
    val groups: Array<KClass<Any>> = [],
    val payload: Array<KClass<Payload>> = []
) {
    companion object {
        private const val ERROR_MESSAGE = "error.account.not.pending"
    }
}
