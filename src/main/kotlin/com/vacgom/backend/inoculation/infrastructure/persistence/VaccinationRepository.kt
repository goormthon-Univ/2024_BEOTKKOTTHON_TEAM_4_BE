package com.vacgom.backend.inoculation.infrastructure.persistence

import com.vacgom.backend.inoculation.domain.Vaccination
import com.vacgom.backend.inoculation.domain.constants.VaccinationType
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface VaccinationRepository : JpaRepository<Vaccination, UUID> {
    fun findByVaccineName(diseaseName: String): Vaccination?
    fun findAllByVaccinationType(vaccinationType: VaccinationType): List<Vaccination>
}
