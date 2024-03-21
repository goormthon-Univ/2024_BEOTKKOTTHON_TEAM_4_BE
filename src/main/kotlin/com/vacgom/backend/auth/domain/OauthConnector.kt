package com.vacgom.backend.auth.domain

import com.vacgom.backend.auth.application.dto.response.OauthTokenResponse
import com.vacgom.backend.auth.application.dto.response.ResourceIdResponse
import com.vacgom.backend.auth.domain.constants.ProviderType

interface OauthConnector {
    fun isSupported(provider: ProviderType): Boolean
    fun fetchOauthToken(code: String): OauthTokenResponse
    fun fetchMemberInfo(accessToken: String): ResourceIdResponse
}
