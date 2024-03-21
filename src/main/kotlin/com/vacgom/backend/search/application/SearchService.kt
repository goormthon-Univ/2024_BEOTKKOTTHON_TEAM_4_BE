package com.vacgom.backend.search.application

import com.vacgom.backend.disease.application.DiseaseService
import com.vacgom.backend.disease.domain.Disease
import com.vacgom.backend.disease.domain.constants.AgeCondition
import com.vacgom.backend.disease.domain.constants.HealthCondition
import com.vacgom.backend.inoculation.domain.Vaccination
import com.vacgom.backend.inoculation.infrastructure.persistence.VaccinationRepository
import com.vacgom.backend.search.application.dto.DiseaseSearchResponse
import com.vacgom.backend.search.application.dto.VaccinationSearchResponse
import org.springframework.stereotype.Service

@Service
class SearchService(
        val vaccinationRepository: VaccinationRepository,
        val diseaseService: DiseaseService,
) {
    private fun findAllVaccinations(): List<Vaccination> {
        return vaccinationRepository.findAll()
    }

    fun searchDisease(
            age: List<AgeCondition>,
            condition: List<HealthCondition>,
    ): List<DiseaseSearchResponse> {
        val diseases = diseaseService.findAll()

        return diseases.filter {
            isMatched(it, age, condition)
        }.map { DiseaseSearchResponse.of(it) }
    }

    fun searchVaccination(
            age: List<AgeCondition>,
            condition: List<HealthCondition>,
    ): List<VaccinationSearchResponse> {
        val diseases = this.searchDisease(age, condition)
        val vaccinations = findAllVaccinations()

        return vaccinations.filter {
            diseases.any { disease -> it.diseaseName.contains(disease.name) }
        }.map { VaccinationSearchResponse.of(it) }
    }

    fun isMatched(
            disease: Disease,
            age: List<AgeCondition>,
            condition: List<HealthCondition>,
    ): Boolean {
        var conditionValue = 0
        condition.forEach {
            conditionValue = conditionValue or it.value
        }

        var ageValue = 0
        age.forEach {
            ageValue = ageValue or it.value
        }

        return disease.ageFilter and ageValue == ageValue ||
                (
                        disease.conditionalAgeFilter and ageValue == ageValue &&
                                disease.healthConditionFilter and conditionValue > 0 &&
                                disease.forbiddenHealthConditionFilter and conditionValue == 0
                        )
    }
}
