package com.vacgom.backend.auth.application.dto.request

data class SignUpRequest(
        val memberInfo: MemberInfoRequest,
        val vaccinationInfo: VaccinationInfoRequest
)
