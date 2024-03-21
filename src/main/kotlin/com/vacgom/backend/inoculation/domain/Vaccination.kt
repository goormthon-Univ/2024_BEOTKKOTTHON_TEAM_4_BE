package com.vacgom.backend.inoculation.domain

import com.vacgom.backend.global.auditing.BaseEntity
import com.vacgom.backend.inoculation.domain.constants.VaccinationType
import jakarta.persistence.*
import org.hibernate.annotations.GenericGenerator
import java.util.*

@Entity
@Table(name = "t_vaccination")
class Vaccination(
        val diseaseName: String,
        val vaccineName: String,
        val minOrder: Long,
        val maxOrder: Long,
        @Enumerated(EnumType.STRING) val vaccinationType: VaccinationType
) : BaseEntity() {

    @Id
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(columnDefinition = "BINARY(16)", name = "vaccination_id")
    val id: UUID? = null
}

