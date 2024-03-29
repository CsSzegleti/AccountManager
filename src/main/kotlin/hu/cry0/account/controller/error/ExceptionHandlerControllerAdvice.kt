package hu.cry0.account.controller.error

import hu.cry0.account.controller.AccountController
import hu.cry0.account.controller.TransactionController
import hu.cry0.account.persistence.exception.BankPrefixNotSetException
import hu.cry0.account.persistence.exception.NoActiveSessionException
import hu.cry0.account.service.exception.NotFoundException
import jakarta.persistence.EntityNotFoundException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.validation.BindException
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.method.annotation.HandlerMethodValidationException
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException
import org.springframework.web.servlet.support.ServletUriComponentsBuilder

@ControllerAdvice(assignableTypes = [AccountController::class, TransactionController::class])
class ExceptionHandlerControllerAdvice {

    @ExceptionHandler(
        value = [HandlerMethodValidationException::class]
    )
    fun handleValidationException(ex: HandlerMethodValidationException): ResponseEntity<ApiError> {
        val apiError = ApiError().apply {
            path = ServletUriComponentsBuilder.fromCurrentRequest().toUriString()
            message = ex.allValidationResults.firstOrNull()?.resolvableErrors?.firstOrNull()?.defaultMessage

        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiError)
    }

    @ExceptionHandler(
        value = [
            BindException::class,
            MethodArgumentNotValidException::class,
        ]
    )
    fun handleBindException(ex: BindException): ResponseEntity<ApiError> {
        val apiError = ApiError().apply {
            path = ServletUriComponentsBuilder.fromCurrentRequest().toUriString()
            message = ex.bindingResult.toString()
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiError)
    }

    @ExceptionHandler(
        value = [
            MethodArgumentTypeMismatchException::class,
        ]
    )
    fun handleMethodArgumentTypeMismatchException(ex: MethodArgumentTypeMismatchException): ResponseEntity<ApiError> {
        val apiError = ApiError().apply {
            path = ServletUriComponentsBuilder.fromCurrentRequest().toUriString()
            message = ex.message
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiError)
    }

    @ExceptionHandler(value = [BankPrefixNotSetException::class, NoActiveSessionException::class])
    fun handleBandPrefixNotSetException(ex: Exception): ResponseEntity<ApiError> {
        val apiError = ApiError().apply {
            path = ServletUriComponentsBuilder.fromCurrentRequest().toUriString()
            message = ex.message
        }

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(apiError)
    }


    @ExceptionHandler(value = [EntityNotFoundException::class, NotFoundException::class])
    fun handleNotFoundException(ex: NotFoundException): ResponseEntity<ApiError> {
        val apiError = ApiError().apply {
            path = ServletUriComponentsBuilder.fromCurrentRequest().toUriString()
            message = ex.message
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(apiError)
    }

    @ExceptionHandler(value = [Exception::class])
    fun handleOtherException(ex: Exception): ResponseEntity<ApiError> {
        val apiError = ApiError().apply {
            path = ServletUriComponentsBuilder.fromCurrentRequest().toUriString()
            message = ex.message
        }

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(apiError)
    }
}