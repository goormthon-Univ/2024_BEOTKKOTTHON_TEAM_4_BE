package com.vacgom.backend.disease.application

import com.vacgom.backend.disease.domain.Disease
import com.vacgom.backend.disease.infrastructure.persistence.DiseaseRepository
import org.springframework.stereotype.Service

@Service
class DiseaseService(
        private val diseaseRepository: DiseaseRepository,
) {
    fun findAll(): List<Disease> {
        return diseaseRepository.findAll()
    }
}
