package com.vacgom.backend.disease.domain

import com.vacgom.backend.global.auditing.BaseEntity
import jakarta.persistence.*

@Entity()
@Table(name = "t_disease")
class Disease(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "disease_id")
        val id: Long? = null,
        val name: String,
        val iconImage: String?,
        val description: String?,
        val ageFilter: Int,
        val conditionalAgeFilter: Int,
        val healthConditionFilter: Int,
        val forbiddenHealthConditionFilter: Int,
) : BaseEntity()
