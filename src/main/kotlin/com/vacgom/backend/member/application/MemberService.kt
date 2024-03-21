package com.vacgom.backend.member.application

import com.vacgom.backend.disease.domain.constants.HealthCondition
import com.vacgom.backend.global.exception.error.BusinessException
import com.vacgom.backend.member.domain.HealthProfile
import com.vacgom.backend.member.domain.Member
import com.vacgom.backend.member.exception.MemberError
import com.vacgom.backend.member.infrastructure.persistence.MemberRepository
import jakarta.transaction.Transactional
import org.springframework.stereotype.Component
import java.util.*

@Component
@Transactional
class MemberService(
        private val memberRepository: MemberRepository,
) {
    fun findMember(id: UUID): Member? {
        return memberRepository.findById(id).orElseThrow()
    }

    fun updateHealthCondition(
            id: UUID,
            healthProfiles: List<HealthCondition>,
    ): Member {
        val member =
                memberRepository.findById(id).orElseThrow {
                    BusinessException(MemberError.NOT_FOUND)
                }

        member.healthProfiles.clear()
        member.healthProfiles.addAll(healthProfiles.map { HealthProfile(member, it) })
        return memberRepository.save(member)
    }

    fun withdrawMember(id: UUID) {
        val member =
                memberRepository.findById(id).orElseThrow {
                    BusinessException(MemberError.NOT_FOUND)
                }
        memberRepository.delete(member)
    }
}
