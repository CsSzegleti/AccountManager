package hu.cry0.account.controller

import hu.cry0.account.model.Transaction
import hu.cry0.account.service.TransactionService
import jakarta.validation.constraints.NotNull
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.servlet.support.ServletUriComponentsBuilder
import java.net.URI
import java.util.UUID

@RestController
@RequestMapping(value = ["/api/v1/account/{accountNumber}/transaction"])
class TransactionController(private val transactionService: TransactionService) {

    @GetMapping(produces = [MediaType.APPLICATION_JSON_VALUE])
    fun getAllByAccountNumber(
        @PathVariable(required = true) @NotNull accountNumber: Long?
    ) = ResponseEntity.ok(transactionService.getAllByAccountNumber(accountNumber!!))

    @GetMapping(path = ["/{transactionId}"], produces = [MediaType.APPLICATION_JSON_VALUE])
    fun getById(
        @PathVariable @NotNull accountNumber: Long,
        @PathVariable transactionId: UUID,
    ) = ResponseEntity.ok(transactionService.getById(transactionId, accountNumber))

    @DeleteMapping(path = ["/{transactionId}"], produces = [MediaType.APPLICATION_JSON_VALUE])
    fun deleteById(
        @PathVariable @NotNull accountNumber: Long,
        @PathVariable transactionId: UUID,
    ): ResponseEntity<*> {
        transactionService.deleteById(transactionId)
        return ResponseEntity.status(204).build<HttpStatus>()
    }

    @PostMapping(consumes = [MediaType.APPLICATION_JSON_VALUE], produces = [MediaType.APPLICATION_JSON_VALUE])
    fun addTransaction(
        @PathVariable @NotNull accountNumber: Long,
        @RequestBody transaction: Transaction,
    ): ResponseEntity<Transaction> {
        val saveResult = transactionService.addTransaction(transaction)

        val location: URI =
            ServletUriComponentsBuilder.fromCurrentRequest().path("/{transactionId}").buildAndExpand(saveResult.id)
                .toUri()

        return ResponseEntity.created(location).body(saveResult)
    }

    @PutMapping(
        path = ["/{transactionId}"],
        consumes = [MediaType.APPLICATION_JSON_VALUE],
        produces = [MediaType.APPLICATION_JSON_VALUE]
    )
    fun updateTransaction(
        @PathVariable @NotNull accountNumber: Long,
        @PathVariable transactionId: UUID,
        @RequestBody transaction: Transaction,
    ): ResponseEntity<Transaction> {
        val updateResult = transactionService.updateTransaction(transactionId, accountNumber, transaction)

        return ResponseEntity.ok(updateResult)
    }
}