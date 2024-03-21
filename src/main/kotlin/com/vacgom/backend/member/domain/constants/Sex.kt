package com.vacgom.backend.member.domain.constants

import com.vacgom.backend.global.exception.error.BusinessException
import com.vacgom.backend.member.exception.MemberError

enum class Sex(
        val value: String
) {
    MALE("M"),
    FEMALE("F");

    companion object {
        fun getSexByValue(value: String): Sex {
            return entries.find { it.value == value }
                    ?: throw BusinessException(MemberError.INVALID_SEX_REQUESTED)
        }
    }
}
