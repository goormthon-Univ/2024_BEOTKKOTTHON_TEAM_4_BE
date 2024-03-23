package com.vacgom.backend.inoculation.application.dto.response

import java.time.LocalDate

data class InoculationCertificateResponse(
    val diseaseName: String,
    val vaccineName: String,
    val inoculatedDate: LocalDate,
    val iconImage: String
)