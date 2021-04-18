package io.freddie.foodcalendar.domain.food.entity

import javax.persistence.Entity
import javax.persistence.EnumType
import javax.persistence.Enumerated
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne

@Entity
data class Nutrient(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0,
    @Enumerated(EnumType.STRING)
    var category: NutrientCategory,
    var amount: Int,
    @ManyToOne
    @JoinColumn(name = "food_id")
    var food: Food
) {
}