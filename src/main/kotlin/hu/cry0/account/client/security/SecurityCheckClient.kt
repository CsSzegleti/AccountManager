package hu.cry0.account.client.security

import hu.cry0.account.client.security.model.SecurityCheckInputDto
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.web.reactive.function.client.WebClient

class SecurityCheckClient(baseUrl: String) {

    companion object {
        private const val BACKGROUND_SECURITY_CHECK_URL = "/background-security-check"
    }

    private val webclient: WebClient = WebClient.create(baseUrl)

    fun performCheck(securityCheckInputDto: SecurityCheckInputDto) {
        val uriSpec = webclient.post()
        val bodySpec = uriSpec.uri(BACKGROUND_SECURITY_CHECK_URL)
        val headersSpec = bodySpec.bodyValue(securityCheckInputDto)
        val responseSpec = headersSpec.header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
            .accept(MediaType.APPLICATION_JSON).retrieve()

//        val responseFlux: Flux<SecurityCheckOutputDto> = responseSpec.retrieve().bodyToFlux(SecurityCheckOutputDto::class.java)

    }
}