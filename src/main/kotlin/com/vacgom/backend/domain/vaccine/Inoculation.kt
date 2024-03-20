package com.vacgom.backend.domain.vaccine

import com.fasterxml.jackson.annotation.JsonFormat
import com.vacgom.backend.domain.member.Member
import com.vacgom.backend.domain.vaccine.constants.Vaccination
import com.vacgom.backend.global.auditing.BaseEntity
import jakarta.persistence.*
import org.hibernate.annotations.GenericGenerator
import java.time.LocalDate
import java.util.*

@Entity
@Table(name = "t_innoculation")
class Inoculation(
        @Enumerated(value = EnumType.STRING)
        val vaccination: Vaccination,
        val inoculationOrder: Long,
        val inoculationOrderString: String,
        @JsonFormat(
                shape = JsonFormat.Shape.STRING,
                pattern = "yyyy-MM-dd"
        )
        val date: LocalDate,
        val agency: String,
        val vaccineName: String,
        val vaccineBrandName: String,
        val lotNumber: String,

        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "member_id")
        val member: Member
) : BaseEntity() {

    @Id
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(columnDefinition = "BINARY(16)", name = "vaccine_id")
    val id: UUID? = null
}
