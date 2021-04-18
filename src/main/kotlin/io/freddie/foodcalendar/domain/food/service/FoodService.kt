package io.freddie.foodcalendar.domain.food.service

import io.freddie.foodcalendar.domain.food.dto.FoodRequest
import io.freddie.foodcalendar.domain.food.dto.FoodResponse
import io.freddie.foodcalendar.domain.food.dto.SearchFoodRequest
import io.freddie.foodcalendar.domain.food.entity.Food
import io.freddie.foodcalendar.domain.food.entity.Nutrient
import io.freddie.foodcalendar.domain.food.entity.ServingSize
import io.freddie.foodcalendar.domain.food.repository.FoodRepository
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class FoodService(
    private val foodRepository: FoodRepository
) {
    @Transactional
    fun createFood(foodRequest: FoodRequest): Food {
        val food = foodRequest.run {
            val servingSize = ServingSize(this.servingSize.amount, this.servingSize.unit)
            Food(name = name, servingSize = servingSize, description = description)
        }

        foodRequest.nutrients
            .map { Nutrient(category = it.category, amount = it.amount, food = food) }
            .forEach { food.nutrients.add(it) }

        return foodRepository.save(food)
    }

    @Transactional
    fun updateFood(id: Long, foodRequest: FoodRequest): Food {
        val food = foodRepository.findByIdOrNull(id)
            ?: throw RuntimeException()

        foodRequest.run {
            food.name = name
            food.description = description
            food.servingSize = ServingSize(servingSize.amount, servingSize.unit)

            food.nutrients.clear()
            nutrients
                .map { Nutrient(category = it.category, amount = it.amount, food = food) }
                .forEach { food.nutrients.add(it) }
        }

        return food
    }

    fun getFood(id: Long) : Food {
        return foodRepository.findByIdOrNull(id)
            ?: throw RuntimeException()
    }

    fun getFoods(searchFoodRequest: SearchFoodRequest, pageable: Pageable): Page<Food> {
        return foodRepository.findAllByNameLike("%${searchFoodRequest.name}%", pageable)
    }
}