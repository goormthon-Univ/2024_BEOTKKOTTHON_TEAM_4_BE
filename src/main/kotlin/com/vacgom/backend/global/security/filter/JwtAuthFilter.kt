package com.vacgom.backend.global.security.filter

import com.vacgom.backend.auth.domain.constants.Role.ROLE_GUEST
import com.vacgom.backend.global.security.jwt.JwtFactory
import com.vacgom.backend.global.security.model.CustomUser
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.slf4j.Logger
import org.springframework.security.authentication.AnonymousAuthenticationToken
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.User
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter

@Component
class JwtAuthFilter(
        private val jwtFactory: JwtFactory,
        private val log: Logger
) : OncePerRequestFilter() {
    companion object {
        private const val HEADER_AUTHORIZATION = "Authorization"
        private const val TOKEN_PREFIX = "Bearer "
    }

    override fun doFilterInternal(
            request: HttpServletRequest,
            response: HttpServletResponse,
            chain: FilterChain
    ) {
        val token = getAccessToken(request)
        if (token == null) {
            val authority = SimpleGrantedAuthority(ROLE_GUEST.toString())
            val principal: User = CustomUser(
                    null,
                    "guest",
                    "", setOf(authority))
            val authenticationToken = AnonymousAuthenticationToken("guest", principal, setOf(authority))
            SecurityContextHolder.getContext().authentication = authenticationToken
            chain.doFilter(request, response)
            log.info("{}({}) - ({}) {}", principal.username, principal.authorities, request.method, request.requestURL)
            return
        }
        val authentication = jwtFactory.getAuthentication(token)
        SecurityContextHolder.getContext().authentication = authentication
        log.info("{}", SecurityContextHolder.getContext())
        chain.doFilter(request, response)
    }

    private fun getAccessToken(
            request: HttpServletRequest
    ): String? {
        val headerValue = request.getHeader(HEADER_AUTHORIZATION)
                ?: return null
        return if (headerValue.startsWith(TOKEN_PREFIX)) {
            headerValue.substring(TOKEN_PREFIX.length)
        } else null
    }
}


