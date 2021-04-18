package io.freddie.foodcalendar.domain.food.entity

import javax.persistence.Embeddable
import javax.persistence.EnumType
import javax.persistence.Enumerated

@Embeddable
data class ServingSize(
    var amount: Double,
    @Enumerated(EnumType.STRING)
    var unit: Unit
)