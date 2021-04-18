package io.freddie.foodcalendar.domain.intake.dto

import java.time.LocalDate

data class IntakeRequest(
    val date: LocalDate,
    val foodId: Long,
    val amount: Double
)