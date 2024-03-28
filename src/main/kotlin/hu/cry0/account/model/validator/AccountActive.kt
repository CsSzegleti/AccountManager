package hu.cry0.account.model.validator

import jakarta.validation.Constraint
import jakarta.validation.Payload
import kotlin.reflect.KClass

@Target(AnnotationTarget.VALUE_PARAMETER, AnnotationTarget.PROPERTY)
@Retention(AnnotationRetention.RUNTIME)
@Constraint(validatedBy = [ActiveAccountValidator::class])
annotation class AccountActive(
    val message: String = ERROR_MESSAGE,
    val groups: Array<KClass<Any>> = [],
    val payload: Array<KClass<Payload>> = []
) {
    companion object {
        private const val ERROR_MESSAGE = "error.account.not.active"
    }
}
