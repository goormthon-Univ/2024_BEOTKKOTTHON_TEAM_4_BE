package com.vacgom.backend.auth.application.dto.response

import com.vacgom.backend.disease.domain.constants.HealthCondition

class HealthConditionResponse(
        val code: String,
        val description: String,
) {
    companion object {
        fun of(healthCondition: HealthCondition): HealthConditionResponse {
            return HealthConditionResponse(
                    code = healthCondition.name,
                    description = healthCondition.description,
            )
        }
    }
}
