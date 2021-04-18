package io.freddie.foodcalendar.domain.intake.service

import io.freddie.foodcalendar.domain.food.service.FoodService
import io.freddie.foodcalendar.domain.intake.dto.IntakeRequest
import io.freddie.foodcalendar.domain.intake.dto.SearchIntakeRequest
import io.freddie.foodcalendar.domain.intake.entity.Intake
import io.freddie.foodcalendar.domain.intake.repository.IntakeRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class IntakeService(
    private val intakeRepository: IntakeRepository,
    private val foodService: FoodService
) {
    @Transactional
    fun create(intakeRequest: IntakeRequest): Intake {
        val food = foodService.getFood(intakeRequest.foodId)

        val intake = intakeRequest.run {
            Intake(amount = amount, date = date, food = food)
        }

        return intakeRepository.save(intake)
    }

    @Transactional
    fun update(id: Long, intakeRequest: IntakeRequest): Intake {
        val intake = intakeRepository.findByIdOrNull(id)
            ?: throw RuntimeException()

        val food = foodService.getFood(intakeRequest.foodId)

        intakeRequest.run {
            intake.amount = amount
            intake.date = date
            intake.food = food
        }

        return intake
    }

    @Transactional
    fun delete(id: Long) {
        intakeRepository.deleteById(id)
    }

    fun get(id: Long): Intake {
        return intakeRepository.findByIdOrNull(id)
            ?: throw RuntimeException()
    }

    fun search(searchIntakeRequest: SearchIntakeRequest): List<Intake> {
        return intakeRepository.findAllByDateBetween(searchIntakeRequest.startDate, searchIntakeRequest.endDate)
    }
}