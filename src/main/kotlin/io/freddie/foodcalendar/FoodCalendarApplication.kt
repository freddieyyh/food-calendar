package io.freddie.foodcalendar

import org.modelmapper.ModelMapper
import org.modelmapper.convention.MatchingStrategies.LOOSE
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean

@SpringBootApplication
class FoodCalendarApplication {
    @Bean
    fun modelMapper(): ModelMapper {
        val mapper = ModelMapper()
        mapper.configuration.matchingStrategy = LOOSE
        return mapper
    }
}

fun main(args: Array<String>) {
    runApplication<FoodCalendarApplication>(*args)
}
