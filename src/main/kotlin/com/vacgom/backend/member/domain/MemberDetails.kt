package com.vacgom.backend.member.domain

import com.fasterxml.jackson.annotation.JsonFormat
import com.vacgom.backend.member.domain.constants.Sex
import jakarta.persistence.Embeddable
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@Embeddable
class MemberDetails(
        var name: String,

        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
        var birthday: LocalDate,

        @Enumerated(EnumType.STRING)
        var sex: Sex
) {
    constructor(name: String, birthday: String, sex: String) : this(
            name,
            LocalDate.parse(birthday, DateTimeFormatter.ofPattern("yyyy-MM-dd")),
            Sex.getSexByValue(sex)
    )
}
