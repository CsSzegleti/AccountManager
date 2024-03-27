package hu.cry0.account.controller.error

import hu.cry0.account.controller.AccountController
import hu.cry0.account.controller.TransactionController
import org.springframework.web.bind.annotation.ControllerAdvice

@ControllerAdvice(assignableTypes = [AccountController::class, TransactionController::class])
class ExceptionHandlerControllerAdvice {

}