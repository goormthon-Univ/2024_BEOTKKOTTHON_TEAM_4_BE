package com.vacgom.backend.auth.application.dto.request

import java.time.LocalDate

data class Vaccine(
        val vaccineType: String, //DTap
        val inoculationOrder: Long, // 4
        val inoculationOrderString: String, // 4차(추가)
        val date: LocalDate, // 2024-03-21
        val agency: String, // 광명시보건소
        val vaccineName: String?, // 보령정제피디티백신
        val vaccineBrandName: String?, // (주)보령바이오파마
        val lotNumber: String?, //AC14B097BB
)
