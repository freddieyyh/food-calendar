package io.freddie.foodcalendar.domain.food.dto

import io.freddie.foodcalendar.domain.configuration.NoArg
import io.freddie.foodcalendar.domain.food.entity.Food
import io.freddie.foodcalendar.domain.food.entity.NutrientCategory
import io.freddie.foodcalendar.domain.food.entity.Unit

@NoArg
data class FoodResponse(
    val id: Long,
    var name: String,
    var servingSize: ServingSize,
    var description: String = "",
    var nutrients: List<Nutrient> = emptyList()
) {
    @NoArg
    data class Nutrient(
        var category: NutrientCategory,
        var amount: Int
    )

    @NoArg
    data class ServingSize(
        var amount: Double,
        var unit: Unit
    )

    companion object {
        fun fromEntity(food: Food) : FoodResponse {
            return FoodResponse(
                id = food.id,
                name = food.name,
                servingSize = ServingSize(
                    amount = food.servingSize.amount,
                    unit = food.servingSize.unit
                ),
                description = food.description,
                nutrients = food.nutrients.map { Nutrient(it.category, it.amount) }
            )
        }
    }
}
