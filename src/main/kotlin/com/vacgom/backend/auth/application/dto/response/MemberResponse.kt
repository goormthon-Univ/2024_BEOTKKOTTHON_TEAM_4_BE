package com.vacgom.backend.auth.application.dto.response

import com.vacgom.backend.auth.domain.constants.Role
import java.util.*

data class MemberResponse(
        val memberId: UUID,
        val role: Role
)
