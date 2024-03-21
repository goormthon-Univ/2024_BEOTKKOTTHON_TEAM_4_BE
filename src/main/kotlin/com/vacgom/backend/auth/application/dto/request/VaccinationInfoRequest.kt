package com.vacgom.backend.auth.application.dto.request

data class VaccinationInfoRequest(
        val name: String,
        val birth: String,
        val sex: String,
        val vaccineList: MutableList<Vaccine>
)
