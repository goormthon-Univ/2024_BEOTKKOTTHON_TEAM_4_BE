package com.vacgom.backend.search.application.dto

import com.vacgom.backend.disease.domain.Disease

class DiseaseSearchResponse(
        val id: Long?,
        val name: String,
        val iconImage: String?,
        val description: String?,
) {
    companion object {
        fun of(disease: Disease): DiseaseSearchResponse {
            return DiseaseSearchResponse(
                    id = disease.id,
                    name = disease.name,
                    iconImage = disease.iconImage,
                    description = disease.description,
            )
        }
    }
}
