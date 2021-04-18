package io.freddie.foodcalendar.domain.food.controller

import io.freddie.foodcalendar.domain.food.dto.FoodRequest
import io.freddie.foodcalendar.domain.food.dto.FoodResponse
import io.freddie.foodcalendar.domain.food.dto.SearchFoodRequest
import io.freddie.foodcalendar.domain.food.service.FoodService
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.data.web.PageableDefault
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/foods")
class FoodController(
    private val foodService: FoodService
) {
    @PostMapping
    fun createFood(@RequestBody foodRequest: FoodRequest): ResponseEntity<Unit> {
        val food = foodService.createFood(foodRequest)
        return ResponseEntity.created(
            linkTo(FoodController::class.java).slash(food.id).toUri()
        ).build()
    }

    @PutMapping("/{id}")
    fun updateFood(@PathVariable id: Long, @RequestBody foodRequest: FoodRequest): FoodResponse {
        val food = foodService.updateFood(id, foodRequest)
        return FoodResponse.fromEntity(food)
    }

    @GetMapping("/{id}")
    fun getFood(@PathVariable id: Long): FoodResponse {
        val food = foodService.getFood(id)
        return FoodResponse.fromEntity(food)
    }

    @GetMapping
    fun getFoods(
        searchFoodRequest: SearchFoodRequest,
        @PageableDefault pageable: Pageable
    ): Page<FoodResponse> {
        return foodService.getFoods(searchFoodRequest, pageable)
            .map { FoodResponse.fromEntity(it) }
    }
}