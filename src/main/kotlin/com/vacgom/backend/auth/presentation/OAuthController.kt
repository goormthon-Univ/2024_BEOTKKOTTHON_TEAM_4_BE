package com.vacgom.backend.auth.presentation

import com.vacgom.backend.auth.application.AuthService
import com.vacgom.backend.auth.application.dto.response.AuthResponse
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.net.URI

@RestController
@RequestMapping("/api/v1/oauth")
class OAuthController(private val authService: AuthService) {

    @GetMapping("/{provider}")
    fun redirectToAuthorization(
            @PathVariable provider: String
    ): ResponseEntity<Unit> {
        val authorizationUri: URI = authService.getAuthorizationUri(provider)
        val headers = authService.createRedirectHeaders(authorizationUri)
        return ResponseEntity(headers, HttpStatus.FOUND)
    }

    @GetMapping("/{provider}/login")
    fun login(
            @PathVariable provider: String,
            @RequestParam code: String
    ): ResponseEntity<AuthResponse> {
        val loginResponse = authService.login(provider, code)
        return ResponseEntity.ok(loginResponse)
    }
}
