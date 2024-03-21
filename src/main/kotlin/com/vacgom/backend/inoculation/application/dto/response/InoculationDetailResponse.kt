package com.vacgom.backend.inoculation.application.dto.response

import java.time.LocalDate

data class InoculationDetailResponse(
        val vaccineName: String,
        val order: String,
        val agency: String,
        val lotNumber: String?,
        val vaccineProductName: String?,
        val vaccineBrandName: String?,
        val date: LocalDate
)
