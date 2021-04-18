package io.freddie.foodcalendar

import io.freddie.foodcalendar.domain.food.entity.Food
import io.freddie.foodcalendar.domain.food.entity.Nutrient
import io.freddie.foodcalendar.domain.food.entity.NutrientCategory
import io.freddie.foodcalendar.domain.food.entity.ServingSize
import io.freddie.foodcalendar.domain.food.entity.Unit
import io.freddie.foodcalendar.domain.food.repository.FoodRepository
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class FoodCalendarApplicationTests(

) {
    @Autowired
    lateinit var foodRepository: FoodRepository

    @Test
    fun contextLoads() {
        val food = Food(
            name = "test1",
            servingSize = ServingSize(
                amount = 1.0,
                unit = Unit.EACH
            ),
            nutrients = mutableListOf()
        )
        food.nutrients.add(
            Nutrient(
                category = NutrientCategory.CALORIES,
                amount = 1
            )
        )

        foodRepository.save(food)
    }

}
