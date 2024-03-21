package com.vacgom.backend.global.security.jwt

import com.vacgom.backend.global.exception.error.BusinessException
import com.vacgom.backend.global.security.exception.AuthError
import com.vacgom.backend.global.security.model.CustomUser
import com.vacgom.backend.member.domain.Member
import io.jsonwebtoken.*
import io.jsonwebtoken.io.Decoders
import io.jsonwebtoken.security.Keys
import io.jsonwebtoken.security.SecurityException
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.User
import org.springframework.stereotype.Component
import java.security.Key
import java.util.*


@Component
class JwtFactory(
        @Value("\${jwt.secret.key}") secret: String,
        @Value("\${jwt.access-token-validity}") private val accessTokenValidity: Long
) {
    private val key: Key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(secret))

    fun createAccessToken(member: Member): String {
        val now = Date()
        val expireDate = Date(now.time + accessTokenValidity)

        return Jwts.builder()
                .setSubject(member.id.toString())
                .setIssuedAt(now)
                .claim("role", member.role)
                .signWith(key, SignatureAlgorithm.HS256)
                .setExpiration(expireDate)
                .compact()
    }

    fun getTokenClaims(token: String): Jws<Claims> {
        return try {
            Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token)
        } catch (exception: SecurityException) {
            throw BusinessException(AuthError.INVALID_JWT_SIGNATURE)
        } catch (exception: MalformedJwtException) {
            throw BusinessException(AuthError.INVALID_JWT_TOKEN)
        } catch (exception: ExpiredJwtException) {
            throw BusinessException(AuthError.EXPIRED_JWT_TOKEN)
        } catch (exception: UnsupportedJwtException) {
            throw BusinessException(AuthError.UNSUPPORTED_JWT_TOKEN)
        } catch (exception: IllegalArgumentException) {
            throw BusinessException(AuthError.UNSUPPORTED_JWT_TOKEN)
        }
    }

    fun getAuthentication(token: String): Authentication? {
        val claims: Claims = getTokenClaims(token).body
        val authorities = Arrays.stream<String>(arrayOf<String>(claims["role"].toString()))
                .map { role: String? -> SimpleGrantedAuthority(role) }
                .toList()
        val principal: User = CustomUser(UUID.fromString(claims.subject), claims.subject, "", authorities)
        return UsernamePasswordAuthenticationToken(principal, this, authorities)
    }
}
