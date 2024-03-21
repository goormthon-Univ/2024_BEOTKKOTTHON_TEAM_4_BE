package com.vacgom.backend.auth.infrastructure.kakao.model

import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component

@Component
data class KakaoProperties(
        @Value("\${spring.security.oauth2.client.registration.kakao.client-id}") val clientId: String,
        @Value("\${spring.security.oauth2.client.registration.kakao.client-secret}") val clientSecret: String,
        @Value("\${spring.security.oauth2.client.registration.kakao.redirect-uri}") val redirectUri: String,
        @Value("\${spring.security.oauth2.client.registration.kakao.authorization-grant-type}") val authorizationGrantType: String,
        @Value("\${spring.security.oauth2.client.provider.kakao.authorization-endpoint}") val authorizationEndpoint: String,
        @Value("\${spring.security.oauth2.client.provider.kakao.token-endpoint}") val tokenEndpoint: String,
        @Value("\${spring.security.oauth2.client.provider.kakao.user-info-endpoint}") val userinfoEndpoint: String
)
