package com.vacgom.backend.member.infrastructure.persistence

import com.vacgom.backend.auth.domain.constants.ProviderType
import com.vacgom.backend.member.domain.Member
import com.vacgom.backend.member.domain.Nickname
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface MemberRepository : JpaRepository<Member, UUID> {
    fun findByProviderIdAndProviderType(
            providerId: Long,
            providerType: ProviderType
    ): Member?

    fun existsMemberByNickname(nickname: Nickname): Boolean
}
