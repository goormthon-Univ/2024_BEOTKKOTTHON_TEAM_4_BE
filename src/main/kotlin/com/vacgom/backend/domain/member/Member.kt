package com.vacgom.backend.domain.member

import com.vacgom.backend.domain.auth.constants.Role
import com.vacgom.backend.domain.auth.oauth.constants.ProviderType
import com.vacgom.backend.domain.member.constants.Sex
import com.vacgom.backend.global.auditing.BaseEntity
import jakarta.persistence.*
import org.hibernate.annotations.GenericGenerator
import java.time.LocalDate
import java.util.*

@Entity
@Table(name = "t_member")
class Member(
        var providerId: Long,
        @Enumerated(EnumType.STRING) var providerType: ProviderType,
        @Enumerated(EnumType.STRING) var role: Role,
) : BaseEntity() {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(columnDefinition = "BINARY(16)", name = "member_id")
    val id: UUID? = null

    var name: String? = null
        private set

    var birthday: LocalDate? = null
        private set

    @Enumerated(EnumType.STRING)
    var sex: Sex? = null
        private set

    @Embedded
    var vacgomId: VacgomId? = null
        private set
}
