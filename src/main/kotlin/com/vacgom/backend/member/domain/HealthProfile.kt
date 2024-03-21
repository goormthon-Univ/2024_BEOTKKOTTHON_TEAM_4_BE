package com.vacgom.backend.member.domain

import com.vacgom.backend.disease.domain.constants.HealthCondition
import jakarta.persistence.*
import org.hibernate.annotations.GenericGenerator
import java.util.*

@Entity
@Table(name = "t_health_profile")
class HealthProfile(
        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "member_id")
        var member: Member,
        @Enumerated(value = EnumType.STRING)
        var healthCondition: HealthCondition,
) {
    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(columnDefinition = "BINARY(16)", name = "health_profile_id")
    val id: UUID? = null
}
