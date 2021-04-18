package io.freddie.foodcalendar.domain.intake.dto

import io.freddie.foodcalendar.domain.configuration.NoArg
import io.freddie.foodcalendar.domain.food.dto.FoodResponse
import io.freddie.foodcalendar.domain.intake.entity.Intake
import java.time.LocalDate

@NoArg
data class IntakeResponse(
    var id: Long,
    var amount: Double,
    var food: FoodResponse,
    var date: LocalDate = LocalDate.now()
) {
    companion object {
        fun fromEntity(intake: Intake): IntakeResponse {
            return IntakeResponse(
                id = intake.id,
                amount = intake.amount,
                food = FoodResponse.fromEntity(intake.food),
                date = intake.date
            )
        }
    }
}
