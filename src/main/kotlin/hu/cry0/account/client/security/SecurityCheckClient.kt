package hu.cry0.account.client.security

import hu.cry0.account.client.security.model.SecurityCheckInputDto
import hu.cry0.account.client.security.model.SecurityCheckOutputDto
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.web.reactive.function.client.WebClient
import reactor.core.publisher.Mono

open class SecurityCheckClient(private val webClient: WebClient) {

    companion object {
        private const val BACKGROUND_SECURITY_CHECK_URL = "/background-security-check"
    }

    fun performCheck(securityCheckInputDto: SecurityCheckInputDto): Mono<SecurityCheckOutputDto> {
        return webClient.post().uri(BACKGROUND_SECURITY_CHECK_URL).bodyValue(securityCheckInputDto)
            .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE).accept(MediaType.APPLICATION_JSON)
            .retrieve().bodyToMono(SecurityCheckOutputDto::class.java)
    }
}