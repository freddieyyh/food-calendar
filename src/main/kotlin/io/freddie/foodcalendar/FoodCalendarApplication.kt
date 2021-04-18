package io.freddie.foodcalendar

import org.modelmapper.ModelMapper
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean

@SpringBootApplication
class FoodCalendarApplication {
    @Bean
    fun modelMapper() = ModelMapper()
}

fun main(args: Array<String>) {
    runApplication<FoodCalendarApplication>(*args)
}
