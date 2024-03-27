package hu.cry0.account.controller

import hu.cry0.account.client.security.model.SecurityCheckOutputDto
import hu.cry0.account.model.Account
import hu.cry0.account.model.AccountInitRequest
import hu.cry0.account.service.AccountService
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
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

    @GetMapping(produces = [MediaType.APPLICATION_JSON_VALUE])
    fun getAll(): ResponseEntity<List<Account>> = ResponseEntity.ok(accountService.getAll())

    @GetMapping(path = ["/{accountNumber}"], produces = [MediaType.APPLICATION_JSON_VALUE])
    fun getByAccountNumber(@PathVariable accountNumber: Long): ResponseEntity<Account> =
        ResponseEntity.ok(accountService.getAccountByNumber(accountNumber))

    @DeleteMapping(path = ["/{accountNumber}"])
    fun deleteAccount(@PathVariable accountNumber: Long): ResponseEntity<*> {
        accountService.deleteAccountByNumber(accountNumber)
        return ResponseEntity<HttpStatus>(HttpStatus.OK)
    }

    @PostMapping(path = ["/init"])
    fun initAccount(@RequestBody initRequest: AccountInitRequest): ResponseEntity<*> {
        val account =  accountService.initiateAccountRegistration(initRequest.accountHolderName!!)
        val location: URI =
            ServletUriComponentsBuilder.fromCurrentRequest().path("/{number}").buildAndExpand(account.accountNumber).toUri()

        return ResponseEntity.created(location).body(account)
    }

    @PostMapping(path = ["/finalize"])
    fun finalizeAccount(@RequestBody securityCheckOutputDto: SecurityCheckOutputDto) =
        accountService.finalizeAccountRegistration(securityCheckOutputDto)
}