package com.vacgom.backend.disease.domain.constants

enum class HealthCondition(
        val code: Long,
        val description: String,
        val value: Int,
) {
    DIABETES(1, "당뇨병", 0b10000000000000),
    CHRONIC_CARDIOVASCULAR_DISEASE(2, "만성 심혈관질환", 0b01000000000000),
    CHRONIC_PULMONARY_DISEASE(3, "만성 폐질환", 0b00100000000000),
    CHRONIC_RENAL_DISEASE(4, "만성 신질환", 0b00010000000000),
    CHRONIC_LIVER_DISEASE(5, "만성 간질환", 0b00001000000000),
    SOLID_TUMOR_UNDERGOING_ANTINEOPLASTIC_THERAPY(6, "항암치료중인 고형암", 0b00000100000000),
    IMMUNOSUPPRESSIVE_AGENTS_EXCLUDING_TRANSPLANT(7, "이식 이외 면역 억제제 사용", 0b00000010000000),
    ORGAN_TRANSPLANTATION(8, "장기 이식 경험", 0b00000000100000),
    HEMATOPOIETIC_STEM_CELL_TRANSPLANTATION(9, "조혈모 세포이식", 0b00000001000000),
    SICKLE_CELL_DISEASE(10, "무비증", 0b00000000010000),
    HIV_INFECTION_CD4_ABOVE_200(11, "HIV 감염:CD4>=200/mm3", 0b00000000001000),
    HIV_INFECTION_CD4_BELOW_200(11, "HIV 감염:CD4<200/mm3", 0b00000000000100),
    PREGNANCY(12, "임신부", 0b00000000000010),
    MEDICAL_WORKER(13, "의료기관 종사자", 0b00000000000001),
    ;

    fun isMatching(value: Int): Boolean {
        return value and this.value == this.value
    }

    companion object {
        fun getConditions(value: Int): List<HealthCondition> {
            return entries.filter { it.isMatching(value) }
        }
    }
}
