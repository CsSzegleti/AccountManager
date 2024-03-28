package hu.cry0.account.controller

import hu.cry0.account.controller.error.ApiError
import hu.cry0.account.model.Transaction
import hu.cry0.account.model.validator.AccountActive
import hu.cry0.account.service.TransactionService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.ArraySchema
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
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

    @Operation(
        summary = "Get all transactions.",
        description = "Get all transactions of an account",
    )
    @ApiResponses(
        value = [ApiResponse(
            description = "Successful operation", responseCode = "200", content = [Content(
                mediaType = MediaType.APPLICATION_JSON_VALUE,
                array = ArraySchema(schema = Schema(implementation = Transaction::class)),
            )]
        ), ApiResponse(
            description = "Bad request", responseCode = "400", content = [Content(
                mediaType = MediaType.APPLICATION_JSON_VALUE,
                schema = Schema(implementation = ApiError::class),
            )]
        ), ApiResponse(
            description = "Internal server error", responseCode = "500", content = [Content(
                mediaType = MediaType.APPLICATION_JSON_VALUE,
                schema = Schema(implementation = ApiError::class),
            )]
        )]
    )
    @GetMapping(path = ["/"], produces = [MediaType.APPLICATION_JSON_VALUE])
    @Validated
    fun getAllByAccountNumber(
        @PathVariable @AccountActive accountNumber: String
    ) = ResponseEntity.ok(transactionService.getAllByAccountNumber(accountNumber))

    @Operation(
        summary = "Get an account's transaction by id",
        description = "Get an account's transaction by id",
    )
    @ApiResponses(
        value = [ApiResponse(
            description = "Successful operation", responseCode = "200", content = [Content(
                mediaType = MediaType.APPLICATION_JSON_VALUE,
                schema = Schema(implementation = Transaction::class),
            )]
        ), ApiResponse(
            description = "Bad request", responseCode = "400", content = [Content(
                mediaType = MediaType.APPLICATION_JSON_VALUE,
                schema = Schema(implementation = ApiError::class),
            )]
        ), ApiResponse(
            description = "Resource not found", responseCode = "404", content = [Content(
                mediaType = MediaType.APPLICATION_JSON_VALUE,
                schema = Schema(implementation = ApiError::class),
            )]
        ), ApiResponse(
            description = "Internal server error", responseCode = "500", content = [Content(
                mediaType = MediaType.APPLICATION_JSON_VALUE,
                schema = Schema(implementation = ApiError::class),
            )]
        )]
    )
    @GetMapping(path = ["/{transactionId}"], produces = [MediaType.APPLICATION_JSON_VALUE])
    @Validated
    fun getById(
        @PathVariable @AccountActive accountNumber: String,
        @PathVariable transactionId: UUID,
    ) = ResponseEntity.ok(transactionService.getById(transactionId, accountNumber))

    @Operation(
        summary = "Delete transaction.",
        description = "Delete transaction.",
    )
    @ApiResponses(
        value = [ApiResponse(
            description = "Successful operation", responseCode = "204"
        ), ApiResponse(
            description = "Bad request", responseCode = "400", content = [Content(
                mediaType = MediaType.APPLICATION_JSON_VALUE,
                schema = Schema(implementation = ApiError::class),
            )]
        ), ApiResponse(
            description = "Resource not found", responseCode = "404", content = [Content(
                mediaType = MediaType.APPLICATION_JSON_VALUE,
                schema = Schema(implementation = ApiError::class),
            )]
        ), ApiResponse(
            description = "Internal server error", responseCode = "500", content = [Content(
                mediaType = MediaType.APPLICATION_JSON_VALUE,
                schema = Schema(implementation = ApiError::class),
            )]
        )]
    )
    @DeleteMapping(path = ["/{transactionId}"], produces = [MediaType.APPLICATION_JSON_VALUE])
    @Validated
    fun deleteById(
        @PathVariable @AccountActive accountNumber: String,
        @PathVariable transactionId: UUID,
    ): ResponseEntity<*> {
        transactionService.deleteById(transactionId)
        return ResponseEntity.status(204).build<HttpStatus>()
    }

    @Operation(
        summary = "Add transaction to an account.",
        description = "Add transaction to an account.",
    )
    @ApiResponses(
        value = [ApiResponse(
            description = "Successful operation", responseCode = "201", content = [Content(
                mediaType = MediaType.APPLICATION_JSON_VALUE,
                schema = Schema(implementation = Transaction::class),
            )]
        ), ApiResponse(
            description = "Bad request", responseCode = "400", content = [Content(
                mediaType = MediaType.APPLICATION_JSON_VALUE,
                schema = Schema(implementation = ApiError::class),
            )]
        ), ApiResponse(
            description = "Resource not found", responseCode = "404", content = [Content(
                mediaType = MediaType.APPLICATION_JSON_VALUE,
                schema = Schema(implementation = ApiError::class),
            )]
        ), ApiResponse(
            description = "Internal server error", responseCode = "500", content = [Content(
                mediaType = MediaType.APPLICATION_JSON_VALUE,
                schema = Schema(implementation = ApiError::class),
            )]
        )]
    )
    @PostMapping(path = ["/"], consumes = [MediaType.APPLICATION_JSON_VALUE], produces = [MediaType.APPLICATION_JSON_VALUE])
    @Validated
    fun addTransaction(
        @PathVariable @AccountActive accountNumber: String,
        @RequestBody transaction: Transaction,
    ): ResponseEntity<Transaction> {
        val saveResult = transactionService.addTransaction(accountNumber, transaction)

        val location: URI =
            ServletUriComponentsBuilder.fromCurrentRequest().path("/{transactionId}").buildAndExpand(saveResult.id)
                .toUri()

        return ResponseEntity.created(location).body(saveResult)
    }

    @Operation(
        summary = "Modify transaction on an account.",
        description = "Modify transaction of an account.",
    )
    @ApiResponses(
        value = [ApiResponse(
            description = "Successful operation", responseCode = "200", content = [Content(
                mediaType = MediaType.APPLICATION_JSON_VALUE,
                schema = Schema(implementation = Transaction::class),
            )]
        ), ApiResponse(
            description = "Bad request", responseCode = "400", content = [Content(
                mediaType = MediaType.APPLICATION_JSON_VALUE,
                schema = Schema(implementation = ApiError::class),
            )]
        ), ApiResponse(
            description = "Resource not found", responseCode = "404", content = [Content(
                mediaType = MediaType.APPLICATION_JSON_VALUE,
                schema = Schema(implementation = ApiError::class),
            )]
        ), ApiResponse(
            description = "Internal server error", responseCode = "500", content = [Content(
                mediaType = MediaType.APPLICATION_JSON_VALUE,
                schema = Schema(implementation = ApiError::class),
            )]
        )]
    )
    @PutMapping(
        path = ["/{transactionId}"],
        consumes = [MediaType.APPLICATION_JSON_VALUE],
        produces = [MediaType.APPLICATION_JSON_VALUE]
    )
    @Validated
    fun updateTransaction(
        @PathVariable @AccountActive accountNumber: String,
        @PathVariable transactionId: UUID,
        @RequestBody @Valid transaction: Transaction,
    ): ResponseEntity<Transaction> {
        val updateResult = transactionService.updateTransaction(transactionId, accountNumber, transaction)

        return ResponseEntity.ok(updateResult)
    }
}