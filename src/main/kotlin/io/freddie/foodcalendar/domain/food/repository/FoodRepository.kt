package io.freddie.foodcalendar.domain.food.repository

import io.freddie.foodcalendar.domain.food.entity.Food
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository

interface FoodRepository : JpaRepository<Food, Long> {
    fun findAllByNameLike(name: String, pageable: Pageable): Page<Food>
}