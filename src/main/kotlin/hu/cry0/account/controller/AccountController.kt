package hu.cry0.account.controller

import hu.cry0.account.client.security.model.SecurityCheckOutputDto
import hu.cry0.account.controller.error.ApiError
import hu.cry0.account.model.Account
import hu.cry0.account.model.AccountInitRequest
import hu.cry0.account.model.BalanceResponse
import hu.cry0.account.model.validator.AccountActive
import hu.cry0.account.service.AccountService
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
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.servlet.support.ServletUriComponentsBuilder
import java.net.URI

@RestController
@RequestMapping(value = ["/api/v1/account"])
class AccountController(private val accountService: AccountService) {

    @Operation(
        summary = "Get all accounts.",
        description = "Get all accounts.",
    )
    @ApiResponses(
        value = [ApiResponse(
            description = "Successful operation", responseCode = "200", content = [Content(
                mediaType = MediaType.APPLICATION_JSON_VALUE,
                array = ArraySchema(schema = Schema(implementation = Account::class)),
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
    fun getAll(): ResponseEntity<List<Account>> = ResponseEntity.ok(accountService.getAll())


    @Operation(
        summary = "Get account by account number",
        description = "Get account by account number",
    )
    @ApiResponses(
        value = [ApiResponse(
            description = "Successful operation", responseCode = "200", content = [Content(
                mediaType = MediaType.APPLICATION_JSON_VALUE,
                schema = Schema(implementation = Account::class),
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
    @GetMapping(path = ["/{accountNumber}"], produces = [MediaType.APPLICATION_JSON_VALUE])
    fun getByAccountNumber(@PathVariable accountNumber: String): ResponseEntity<Account> =
        ResponseEntity.ok(accountService.getAccountByNumber(accountNumber))


    @Operation(
        summary = "Get account balance.",
        description = "Get account balance.",
    )
    @ApiResponses(
        value = [ApiResponse(
            description = "Successful operation", responseCode = "200", content = [Content(
                mediaType = MediaType.APPLICATION_JSON_VALUE,
                schema = Schema(implementation = BalanceResponse::class),
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
    @GetMapping(path = ["/{accountNumber}/balance"])
    @Validated
    fun getAccountBalance(@AccountActive @PathVariable accountNumber: String) =
        ResponseEntity.ok(BalanceResponse(accountService.getBalance(accountNumber)))


    @Operation(
        summary = "Delete account",
        description = "Soft delete account from the database",
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
    @DeleteMapping(path = ["/{accountNumber}"])
    @Validated
    fun deleteAccount(@AccountActive @PathVariable accountNumber: String): ResponseEntity<*> {
        accountService.deleteAccountByNumber(accountNumber)
        return ResponseEntity.status(204).build<HttpStatus>()
    }

    @Operation(
        summary = "Init account creation.",
        description = "Initialize account creation. This creates the account with pending status. The account will be active after the security check",
    )
    @ApiResponses(
        value = [ApiResponse(
            description = "Successful operation", responseCode = "201", content = [Content(
                mediaType = MediaType.APPLICATION_JSON_VALUE,
                schema = Schema(implementation = Account::class),
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
    @PostMapping(path = ["/init"])
    fun initAccount(@RequestBody initRequest: AccountInitRequest): ResponseEntity<*> {
        val account = accountService.initiateAccountRegistration(initRequest.accountHolderName!!)
        val location: URI =
            ServletUriComponentsBuilder.fromCurrentRequest().path("/{number}").buildAndExpand(account.accountNumber)
                .toUri()

        return ResponseEntity.created(location).body(account)
    }

    @Operation(
        summary = "Finalize account creation.",
        description = "Sets the account status ACTIVE, or DENIED. Only for background security check.",
    )
    @ApiResponses(
        value = [ApiResponse(
            description = "Successful operation", responseCode = "200"
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
    @PostMapping(path = ["/finalize"])
    @Validated
    fun finalizeAccount(@Valid @RequestBody securityCheckOutputDto: SecurityCheckOutputDto) =
        accountService.finalizeAccountRegistration(securityCheckOutputDto)
}