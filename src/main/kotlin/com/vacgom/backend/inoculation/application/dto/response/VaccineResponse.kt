package com.vacgom.backend.inoculation.application.dto.response

class VaccineResponse(
        val id: String?,
        val name: String,
        val diseases: List<Long>,
)
