package io.freddie.foodcalendar.domain.intake.entity

import io.freddie.foodcalendar.domain.food.entity.Food
import java.time.LocalDate
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.OneToOne

@Entity
data class Intake(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0,
    var amount: Double,
    @OneToOne
    @JoinColumn(name = "food_id")
    var food: Food,
    var date: LocalDate = LocalDate.now()
)
