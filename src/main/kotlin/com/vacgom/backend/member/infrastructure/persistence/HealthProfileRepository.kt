package com.vacgom.backend.member.infrastructure.persistence

import com.vacgom.backend.member.domain.HealthProfile
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface HealthProfileRepository : JpaRepository<HealthProfile, UUID>
