package com.vacgom.backend.inoculation.domain

import com.fasterxml.jackson.annotation.JsonFormat
import com.vacgom.backend.global.auditing.BaseEntity
import com.vacgom.backend.member.domain.Member
import jakarta.persistence.*
import org.hibernate.annotations.GenericGenerator
import java.time.LocalDate
import java.util.*

@Entity
@Table(name = "t_inoculation")
class Inoculation(
        val inoculationOrder: Long,
        val inoculationOrderString: String,
        @JsonFormat(
                shape = JsonFormat.Shape.STRING,
                pattern = "yyyy-MM-dd",
        )
        val date: LocalDate,
        val agency: String,
        val vaccineName: String?,
        val vaccineBrandName: String?,
        val lotNumber: String?,
        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "member_id")
        val member: Member,
        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "vaccination_id")
        val vaccination: Vaccination,
) : BaseEntity() {
    @Id
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(columnDefinition = "BINARY(16)", name = "vaccine_id")
    val id: UUID = UUID.randomUUID()
}
