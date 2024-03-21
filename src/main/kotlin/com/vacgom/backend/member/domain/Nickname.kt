package com.vacgom.backend.member.domain

import com.vacgom.backend.global.exception.error.BusinessException
import com.vacgom.backend.member.exception.NicknameError
import jakarta.persistence.Column
import jakarta.persistence.Embeddable

@Embeddable
class Nickname(
        @Column(unique = true) var nickname: String
) {
    companion object {
        private const val VACGOM_ID_REGEX = "[a-z0-9_]+"
        private const val START_WITH_LOWER_CASE_REGEX = "^[a-z].*"
        private const val CONSECUTIVE_UNDERSCORES = "____"
        private const val MINIMUM_LENGTH = 4
        private const val MAXIMUM_LENGTH = 10
    }

    init {
        validatePattern(nickname)
    }

    private fun validatePattern(id: String) {
        require(id.matches(START_WITH_LOWER_CASE_REGEX.toRegex())) {
            throw BusinessException(NicknameError.NOT_START_WITH_LOWER_CASE)
        }
        require(id.matches(VACGOM_ID_REGEX.toRegex())) {
            throw BusinessException(NicknameError.INVALID_VACGOM_ID_PATTERN)
        }
        require(CONSECUTIVE_UNDERSCORES !in id) {
            throw BusinessException(NicknameError.CONSECUTIVE_UNDERSCORES)
        }
        require(id.length in MINIMUM_LENGTH..MAXIMUM_LENGTH) {
            throw BusinessException(NicknameError.INVALID_LENGTH)
        }

        val lowerCaseCount = id.count { it.isLowerCase() }
        require(lowerCaseCount >= 4) {
            throw BusinessException(NicknameError.MINIMUM_LOWERCASE_REQUIRED)
        }
    }
}
