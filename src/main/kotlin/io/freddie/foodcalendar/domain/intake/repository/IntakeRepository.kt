package io.freddie.foodcalendar.domain.intake.repository

import io.freddie.foodcalendar.domain.intake.entity.Intake
import org.springframework.data.jpa.repository.JpaRepository
import java.time.LocalDate

interface IntakeRepository: JpaRepository<Intake, Long> {
    fun findAllByDateBetween(start: LocalDate, end: LocalDate): List<Intake>
}