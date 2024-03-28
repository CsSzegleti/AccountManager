package hu.cry0.account.controller.error

import io.swagger.v3.oas.annotations.media.Schema

class ApiError {
    @Schema(description = "url")
    var path: String? = null

    @Schema(description = "error message", example = "error.business")
    var message: String? = null
}