package com.vacgom.backend.disease.infrastructure.persistence

import com.vacgom.backend.disease.domain.Disease
import org.springframework.data.jpa.repository.JpaRepository

interface DiseaseRepository : JpaRepository<Disease, Long>
