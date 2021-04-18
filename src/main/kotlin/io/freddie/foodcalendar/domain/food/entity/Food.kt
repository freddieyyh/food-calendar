package io.freddie.foodcalendar.domain.food.entity

import javax.persistence.CascadeType
import javax.persistence.Embedded
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.OneToMany

@Entity
class Food(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0,
    var name: String,
    @Embedded
    var servingSize: ServingSize,
    var description: String = ""
) {
    @OneToMany(cascade = [CascadeType.ALL], orphanRemoval = true, mappedBy = "food")
    val nutrients: MutableList<Nutrient> = mutableListOf()
}