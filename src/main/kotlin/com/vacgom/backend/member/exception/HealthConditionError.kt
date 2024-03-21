package com.vacgom.backend.member.exception

import com.vacgom.backend.global.exception.error.ErrorCode
import org.springframework.http.HttpStatus

enum class HealthConditionError(
        override val message: String,
        override val status: HttpStatus,
        override val code: String,
) : ErrorCode {
    INVALID_HEALTH_CONDITION("올바르지 않은 Health Condition입니다.", HttpStatus.BAD_REQUEST, "HC_001"),
}
