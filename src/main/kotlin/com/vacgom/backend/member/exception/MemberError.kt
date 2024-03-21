package com.vacgom.backend.member.exception

import com.vacgom.backend.global.exception.error.ErrorCode
import org.springframework.http.HttpStatus


enum class MemberError(
        override val message: String,
        override val status: HttpStatus,
        override val code: String
) : ErrorCode {
    NOT_FOUND("사용자를 찾을 수 없습니다.", HttpStatus.NOT_FOUND, "M_001"),
    INVALID_SEX_REQUESTED("사용자를 찾을 수 없습니다.", HttpStatus.NOT_FOUND, "M_002")
}
