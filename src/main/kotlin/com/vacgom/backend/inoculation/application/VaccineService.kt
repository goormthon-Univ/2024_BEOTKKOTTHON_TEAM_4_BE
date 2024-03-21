package com.vacgom.backend.inoculation.application

import com.vacgom.backend.disease.application.DiseaseService
import com.vacgom.backend.global.exception.error.BusinessException
import com.vacgom.backend.inoculation.application.dto.response.VaccineResponse
import com.vacgom.backend.inoculation.exception.VaccineError
import com.vacgom.backend.inoculation.infrastructure.persistence.VaccinationRepository
import org.springframework.stereotype.Service
import java.util.*

@Service
class VaccineService(
        val vaccineRepository: VaccinationRepository,
        val diseaseService: DiseaseService,
) {
    fun getVaccine(id: String): VaccineResponse {
        val vaccine =
                vaccineRepository.findById(UUID.fromString(id))
                        .orElseThrow { throw BusinessException(VaccineError.UNKNOWN_VACCINE_REQUESTED) }

        val diseases = diseaseService.findAll()

        return VaccineResponse(
                id = vaccine.id?.toString(),
                name = vaccine.vaccineName,
                diseases =
                diseases.filter { vaccine.diseaseName.contains(it.name) }
                        .mapNotNull { it.id }
                        .toList(),
        )
    }
}
