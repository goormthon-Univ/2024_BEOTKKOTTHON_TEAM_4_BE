package com.vacgom.backend.inoculation.exception

import com.vacgom.backend.global.exception.error.ErrorCode
import org.springframework.http.HttpStatus

enum class VaccineError(
        override val message: String,
        override val status: HttpStatus,
        override val code: String
) : ErrorCode {
    UNKNOWN_VACCINE_REQUESTED("확인되지 않은 백신이 요청되었습니다.", HttpStatus.BAD_REQUEST, "VI_001")
}

