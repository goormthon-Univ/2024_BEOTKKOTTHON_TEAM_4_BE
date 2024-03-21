package com.vacgom.backend.member.exception

import com.vacgom.backend.global.exception.error.ErrorCode
import org.springframework.http.HttpStatus


enum class NicknameError(
        override val message: String,
        override val status: HttpStatus,
        override val code: String
) : ErrorCode {
    INVALID_VACGOM_ID_PATTERN("닉네임은 영문 소문자, 숫자, 언더바(_)로만 구성되어야 합니다.", HttpStatus.BAD_REQUEST, "VI_001"),
    NOT_START_WITH_LOWER_CASE("닉네임은 영문 소문자로 시작해야 합니다", HttpStatus.BAD_REQUEST, "VI_002"),
    CONSECUTIVE_UNDERSCORES("언더바(_)는 최대 3개 까지 연속으로 사용할 수 있습니다.", HttpStatus.BAD_REQUEST, "VI_003"),
    DUPLICATED("이미 사용중인 닉네임입니다.", HttpStatus.BAD_REQUEST, "VI_004"),
    INVALID_LENGTH("닉네임은 4 ~ 20자 이하여야 합니다.", HttpStatus.BAD_REQUEST, "VI_005"),
    MINIMUM_LOWERCASE_REQUIRED("최소 4개의 소문자 영어를 포함해야 합니다.", HttpStatus.BAD_REQUEST, "VI_006"),
}

