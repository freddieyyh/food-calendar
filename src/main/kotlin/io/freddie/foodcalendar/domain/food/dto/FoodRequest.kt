package io.freddie.foodcalendar.domain.food.dto

import io.freddie.foodcalendar.domain.food.entity.NutrientCategory
import io.freddie.foodcalendar.domain.food.entity.Unit

data class FoodRequest(
    var name: String,
    var servingSize: ServingSize,
    var description: String = "",
    var nutrients: List<Nutrient> = emptyList()
) {

    data class Nutrient(
        var category: NutrientCategory,
        var amount: Int
    )

    data class ServingSize(
        var amount: Double,
        var unit: Unit
    )
}
