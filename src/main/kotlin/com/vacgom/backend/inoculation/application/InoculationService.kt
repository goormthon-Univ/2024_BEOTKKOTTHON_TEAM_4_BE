package com.vacgom.backend.inoculation.application

import com.vacgom.backend.global.exception.error.BusinessException
import com.vacgom.backend.global.exception.error.GlobalError
import com.vacgom.backend.inoculation.application.dto.request.DiseaseNameRequest
import com.vacgom.backend.inoculation.application.dto.request.MemberNameRequest
import com.vacgom.backend.inoculation.application.dto.response.InoculationCertificateResponse
import com.vacgom.backend.inoculation.application.dto.response.InoculationDetailResponse
import com.vacgom.backend.inoculation.application.dto.response.InoculationSimpleResponse
import com.vacgom.backend.inoculation.domain.Inoculation
import com.vacgom.backend.inoculation.domain.constants.VaccinationType
import com.vacgom.backend.inoculation.infrastructure.persistence.InoculationRepository
import com.vacgom.backend.inoculation.infrastructure.persistence.VaccinationRepository
import com.vacgom.backend.member.domain.Nickname
import com.vacgom.backend.member.exception.MemberError
import com.vacgom.backend.member.infrastructure.persistence.MemberRepository
import com.vacgom.backend.notification.application.NotificationService
import jakarta.transaction.Transactional
import org.slf4j.Logger
import org.springframework.stereotype.Service
import java.time.LocalDate
import java.util.*

@Service
@Transactional
class InoculationService(
    private val inoculationRepository: InoculationRepository,
    private val vaccinationRepository: VaccinationRepository,
    private val memberRepository: MemberRepository,
    private val notificationService: NotificationService,
    private val log: Logger,
) {
    fun getInoculationSimpleResponse(
        memberId: UUID,
        vaccinationType: String,
    ): List<InoculationSimpleResponse> {
        val validatedVaccinationType = VaccinationType.valueOf(vaccinationType.uppercase())
        val vaccinations = vaccinationRepository.findAllByVaccinationType(validatedVaccinationType)
        val inoculations =
            inoculationRepository.findInoculationsByMemberIdAndVaccinationType(memberId, validatedVaccinationType)

        val hashMap = HashMap<String, MutableList<Long>>()
        inoculations.forEach { inoculation ->
            val vaccineName = inoculation.vaccination.vaccineName
            val inoculationOrder = inoculation.inoculationOrder

            hashMap.computeIfAbsent(vaccineName) { ArrayList() }
            hashMap[vaccineName]!!.add(inoculationOrder)
        }

        return vaccinations.map { vaccination ->
            val vaccineOrders = hashMap[vaccination.vaccineName]?.toHashSet()?.toList() ?: listOf()
            val isCompleted = vaccineOrders.any { order -> order == vaccination.maxOrder }

            InoculationSimpleResponse(
                vaccination.diseaseName,
                vaccination.vaccineName,
                vaccination.minOrder,
                vaccination.maxOrder,
                isCompleted,
                vaccineOrders,
            )
        }.toList()
    }

    fun getInoculationDetailResponse(
        memberId: UUID,
        request: DiseaseNameRequest,
        vaccinationType: String,
    ): List<InoculationDetailResponse> {
        val validatedVaccinationType = VaccinationType.valueOf(vaccinationType.uppercase())
        val inoculations = (
                inoculationRepository.findInoculationsByMemberIdAndVaccinationTypeAndDiseaseName(
                    memberId,
                    validatedVaccinationType,
                    request.name,
                )
                    ?: throw BusinessException(GlobalError.GLOBAL_NOT_FOUND)
                )

        return inoculations.map {
            InoculationDetailResponse(
                it.vaccination.vaccineName,
                it.inoculationOrderString,
                it.agency,
                it.lotNumber,
                it.vaccineName,
                it.vaccineBrandName,
                it.date,
            )
        }.toList()
    }

    fun getCertificates(memberId: UUID): List<InoculationCertificateResponse> {
        val inoculations = inoculationRepository.findDistinctLatestInoculationsByMemberId(memberId)
        val sortedByDescending = inoculations.sortedByDescending { it.date }
        return sortedByDescending.map {
            InoculationCertificateResponse(
                memberId.toString(),
                it.vaccination.id.toString(),
                it.vaccination.diseaseName,
                it.vaccination.vaccineName,
                it.date,
                it.vaccination.certificationIcon,
            )
        }.toList()
    }

    fun addEventInoculation(memberNameRequest: MemberNameRequest) {
        val name = memberNameRequest.name
        val member =
            memberRepository.findMemberByNickname(Nickname(name)) ?: throw BusinessException(MemberError.NOT_FOUND)

        val eventVaccination = vaccinationRepository.findById(UUID.fromString("30784537-3331-3646-3734-453738383131"))
            .orElseThrow { BusinessException(GlobalError.GLOBAL_NOT_FOUND) }

        inoculationRepository.save(Inoculation(
            1,
            "이벤트",
            LocalDate.now(),
            "백신아 곰아워!",
            "이벤트 백신",
            "아프지 말라곰",
            "https://vacgom.co.kr",
            member,
            eventVaccination
        ))
        notificationService.sendNotification(member.id!!, "이벤트 백신 접종 증명서가 발신되었어요!", "ㅎㅅㅎ")
    }
}
