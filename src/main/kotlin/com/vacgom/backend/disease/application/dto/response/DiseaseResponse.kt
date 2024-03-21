package com.vacgom.backend.disease.application.dto.response

import com.vacgom.backend.disease.domain.Disease

class DiseaseResponse(
        val id: Long?,
        val name: String,
        val iconImage: String?,
        val description: String?,
) {
    companion object {
        fun of(disease: Disease): DiseaseResponse {
            return DiseaseResponse(
                    id = disease.id,
                    name = disease.name,
                    iconImage = disease.iconImage,
                    description = disease.description,
            )
        }
    }
}
