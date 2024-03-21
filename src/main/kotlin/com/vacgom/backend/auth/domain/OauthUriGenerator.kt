package com.vacgom.backend.auth.domain

import com.vacgom.backend.auth.domain.constants.ProviderType
import java.net.URI

interface OauthUriGenerator {
    fun isSupported(provider: ProviderType): Boolean
    fun generate(): URI
}
