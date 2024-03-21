package com.vacgom.backend.auth.application.dto.request

import com.vacgom.backend.disease.domain.constants.HealthCondition

data class MemberInfoRequest(
        val nickname: String,
        val healthConditions: MutableList<HealthCondition>,
)
