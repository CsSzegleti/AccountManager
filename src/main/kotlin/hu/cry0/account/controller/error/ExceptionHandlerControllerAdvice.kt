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
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException
import org.springframework.web.servlet.support.ServletUriComponentsBuilder

@ControllerAdvice(assignableTypes = [AccountController::class, TransactionController::class])
class ExceptionHandlerControllerAdvice {

    @ExceptionHandler(value = [BindException::class, MethodArgumentTypeMismatchException::class, MethodArgumentNotValidException::class])
    fun handleValidationException(ex: MethodArgumentNotValidException): ResponseEntity<ApiError> {
        val apiError = ApiError().apply {
            path = ServletUriComponentsBuilder.fromCurrentRequest().toUriString()
            message = ex.localizedMessage
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiError)
    }


    @ExceptionHandler(value = [BankPrefixNotSetException::class, NoActiveSessionException::class])
    fun handleBandPrefixNotSetException(ex: BankPrefixNotSetException): ResponseEntity<ApiError> {
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