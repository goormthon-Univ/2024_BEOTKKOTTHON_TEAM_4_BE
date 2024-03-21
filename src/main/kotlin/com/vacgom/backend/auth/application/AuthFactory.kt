package com.vacgom.backend.auth.application

import com.vacgom.backend.auth.domain.OauthConnector
import com.vacgom.backend.auth.domain.OauthUriGenerator
import com.vacgom.backend.auth.domain.constants.ProviderType
import com.vacgom.backend.global.exception.error.BusinessException
import com.vacgom.backend.global.security.exception.AuthError
import org.springframework.stereotype.Component

@Component
class AuthFactory(
        private val connectors: List<OauthConnector>,
        private val uriProviders: List<OauthUriGenerator>
) {
    fun getAuthConnector(provider: String): OauthConnector {
        val providerType = ProviderType.from(provider)

        return connectors.firstOrNull {
            it.isSupported(providerType)
        } ?: throw BusinessException(AuthError.UNSUPPORTED_PROVIDER)
    }

    fun getAuthUriGenerator(provider: String): OauthUriGenerator {
        val providerType = ProviderType.from(provider)

        return uriProviders.firstOrNull {
            it.isSupported(providerType)
        } ?: throw BusinessException(AuthError.UNSUPPORTED_PROVIDER)
    }
}
