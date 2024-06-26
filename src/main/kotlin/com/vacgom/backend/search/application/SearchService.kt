package com.vacgom.backend.search.application

import com.vacgom.backend.disease.application.DiseaseService
import com.vacgom.backend.disease.domain.Disease
import com.vacgom.backend.disease.domain.constants.AgeCondition
import com.vacgom.backend.disease.domain.constants.HealthCondition
import com.vacgom.backend.global.exception.error.BusinessException
import com.vacgom.backend.inoculation.domain.Vaccination
import com.vacgom.backend.inoculation.domain.constants.VaccinationType
import com.vacgom.backend.inoculation.infrastructure.persistence.InoculationRepository
import com.vacgom.backend.inoculation.infrastructure.persistence.VaccinationRepository
import com.vacgom.backend.member.exception.MemberError
import com.vacgom.backend.member.infrastructure.persistence.MemberRepository
import com.vacgom.backend.search.application.dto.response.DiseaseSearchResponse
import com.vacgom.backend.search.application.dto.response.SupportVaccineResponse
import com.vacgom.backend.search.application.dto.response.VaccinationSearchResponse
import org.slf4j.Logger
import org.springframework.stereotype.Service
import java.time.LocalDate
import java.util.*

@Service
class SearchService(
    val vaccinationRepository: VaccinationRepository,
    val inoculationRepository: InoculationRepository,
    val memberRepository: MemberRepository,
    val diseaseService: DiseaseService,
    val log: Logger,
) {
    private fun findAllVaccinations(): List<Vaccination> {
        return vaccinationRepository.findAll()
    }

    fun searchDisease(
        age: List<AgeCondition>,
        condition: List<HealthCondition>,
    ): List<DiseaseSearchResponse> {
        val diseases = diseaseService.findAll()

        return diseases.filter {
            isMatched(it, age, condition)
        }.map { DiseaseSearchResponse.of(it) }
    }

    fun searchVaccination(
        age: List<AgeCondition>,
        condition: List<HealthCondition>,
        type: VaccinationType,
    ): List<VaccinationSearchResponse> {
        val diseases = this.searchDisease(age, condition)
        val vaccinations = findAllVaccinations()

        return vaccinations.filter {
            diseases.any { disease -> it.diseaseName.contains(disease.name) } &&
                it.vaccinationType == type
        }.map { VaccinationSearchResponse.of(it) }
    }

    private fun isInJeolGi(date: LocalDate): Boolean {
        val jeolGi = LocalDate.of(2023, 9, 20)
        val jeolGiEnd = LocalDate.of(2024, 4, 30)

        println("jeolGi: $date")
        return date.isAfter(jeolGi) && date.isBefore(jeolGiEnd)
    }

    private fun userVaccinatedIIV(memberId: UUID): Boolean {
        val inoculations =
            inoculationRepository.findInoculationsByMemberIdAndVaccinationTypeAndDiseaseName(
                memberId,
                VaccinationType.NATION,
                "인플루엔자",
            ) ?: return false
        if (inoculations.isEmpty()) return false

        return this.isInJeolGi(inoculations.first().date)
    }

    fun searchRecommendVaccination(memberId: UUID): List<DiseaseSearchResponse> {
        val member =
            memberRepository.findById(memberId).orElseThrow {
                BusinessException(MemberError.NOT_FOUND)
            }

        val ageCondition =
            AgeCondition.getAgeCondition(member.memberDetails?.birthday?.year!!.minus(LocalDate.now().year))
        val vaccinations = findAllVaccinations()
        val inoculatedDiseaseName =
            inoculationRepository.findDistinctDiseaseNameByMemberId(memberId).flatMap { it.split("·") }.toSet()
        val recommendedVaccinations =
            vaccinations.filter { vaccination -> !inoculatedDiseaseName.contains(vaccination.vaccineName) }

        val healthProfiles = member.healthProfiles.map { it.healthCondition }.toList()

        return this.searchDisease(listOf(ageCondition), healthProfiles).filter { response ->
            (
                (response.name == "인플루엔자" && this.userVaccinatedIIV(memberId)) ||
                    response.name != "인플루엔자"
            ) &&
                !inoculatedDiseaseName.contains(response.name)
        }.toList()
    }

    private fun filterByDisease(
        vaccinations: List<Vaccination>,
        diseases: List<DiseaseSearchResponse>,
    ) = vaccinations.filter {
        diseases.any { disease -> it.diseaseName.contains(disease.name) }
    }.map { VaccinationSearchResponse.of(it) }

    fun isMatched(
        disease: Disease,
        age: List<AgeCondition>,
        condition: List<HealthCondition>,
    ): Boolean {
        var conditionValue = 0
        condition.forEach {
            conditionValue = conditionValue or it.value
        }

        var ageValue = 0
        age.forEach {
            ageValue = ageValue or it.value
        }

        return disease.ageFilter and ageValue == ageValue ||
            (
                disease.conditionalAgeFilter and ageValue == ageValue &&
                    disease.healthConditionFilter and conditionValue > 0 &&
                    disease.forbiddenHealthConditionFilter and conditionValue == 0
            )
    }

    fun getInoculatedRatioResponse(): SupportVaccineResponse {
        val membersCount = memberRepository.countValidUser()
        val hpv = inoculationRepository.findInoculationsByDiseaseName("사람유두종바이러스감염증")
        val influenza = inoculationRepository.findInoculationsByDiseaseName("인플루엔자")

        val distinctInfluenzaCount = influenza.distinctBy { it.member.id }.count()
        val distinctHpvCount = hpv.distinctBy { it.member.id }.count()
        println("dic: $distinctInfluenzaCount")
        println("member: $membersCount")

        val influenzaPercentage = 13
        val hpvPercentage = distinctHpvCount.toDouble() / membersCount.toDouble() * 100.0

        return SupportVaccineResponse(influenzaPercentage.toLong(), hpvPercentage.toLong())
    }
}
