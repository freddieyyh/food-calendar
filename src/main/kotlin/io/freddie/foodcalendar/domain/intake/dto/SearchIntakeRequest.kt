package io.freddie.foodcalendar.domain.intake.dto

import org.springframework.format.annotation.DateTimeFormat
import java.time.LocalDate

data class SearchIntakeRequest(
    @field:DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    val startDate: LocalDate = LocalDate.now(),
    @field:DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    val endDate: LocalDate = LocalDate.now()
)