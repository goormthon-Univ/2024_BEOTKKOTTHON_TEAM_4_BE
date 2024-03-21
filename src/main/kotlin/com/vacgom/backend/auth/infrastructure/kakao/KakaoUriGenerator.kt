package com.vacgom.backend.auth.infrastructure.kakao

import com.vacgom.backend.auth.domain.OauthUriGenerator
import com.vacgom.backend.auth.domain.constants.ProviderType
import com.vacgom.backend.auth.infrastructure.kakao.model.KakaoProperties
import org.springframework.stereotype.Component
import org.springframework.web.util.UriComponentsBuilder
import java.net.URI

@Component
class KakaoUriGenerator(
        private val kakaoProperties: KakaoProperties
) : OauthUriGenerator {

    override fun isSupported(provider: ProviderType): Boolean {
        return provider.isKakao()
    }

    override fun generate(): URI {
        return UriComponentsBuilder
                .fromUriString(kakaoProperties.authorizationEndpoint)
                .queryParam("response_type", "code")
                .queryParam("client_id", kakaoProperties.clientId)
                .queryParam("redirect_uri", kakaoProperties.redirectUri)
                .build()
                .toUri()
    }
}
