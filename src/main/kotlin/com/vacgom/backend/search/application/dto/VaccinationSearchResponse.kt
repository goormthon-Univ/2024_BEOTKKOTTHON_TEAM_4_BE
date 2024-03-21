package com.vacgom.backend.search.application.dto

import com.vacgom.backend.inoculation.domain.Vaccination

class VaccinationSearchResponse(
        val id: String?,
        val vaccineName: String,
        val diseaseName: String,
        val iconImage: String?,
) {
    companion object {
        fun of(vaccine: Vaccination): VaccinationSearchResponse {
            return VaccinationSearchResponse(
                    id = vaccine.id?.toString(),
                    vaccineName = vaccine.vaccineName,
                    diseaseName = vaccine.diseaseName,
                    iconImage = "iconImage",
            )
        }
    }
}
