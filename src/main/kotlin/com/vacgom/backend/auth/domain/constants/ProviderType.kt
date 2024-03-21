package com.vacgom.backend.auth.domain.constants

import com.vacgom.backend.global.exception.error.BusinessException
import com.vacgom.backend.global.security.exception.AuthError

enum class ProviderType(val provider: String) {
    KAKAO("kakao");

    companion object {
        fun from(provider: String): ProviderType {
            return entries.firstOrNull { it.provider == provider }
                    ?: throw BusinessException(AuthError.UNSUPPORTED_PROVIDER)
        }
    }

    fun isKakao(): Boolean {
        return this == KAKAO
    }
}
