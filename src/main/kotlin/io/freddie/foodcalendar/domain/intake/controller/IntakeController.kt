package io.freddie.foodcalendar.domain.intake.controller

import io.freddie.foodcalendar.domain.intake.dto.IntakeRequest
import io.freddie.foodcalendar.domain.intake.dto.IntakeResponse
import io.freddie.foodcalendar.domain.intake.dto.SearchIntakeRequest
import io.freddie.foodcalendar.domain.intake.service.IntakeService
import org.modelmapper.ModelMapper
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/intakes")
class IntakeController(
    private val intakeService: IntakeService,
    private val modelMapper: ModelMapper
) {
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun addIntake(@RequestBody intakeRequest: IntakeRequest): ResponseEntity<Unit> {
        val intake = intakeService.create(intakeRequest)
        return ResponseEntity.created(
            WebMvcLinkBuilder.linkTo(IntakeController::class.java).slash(intake.id).toUri()
        ).build()
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    fun deleteIntake(@PathVariable("id") id: Long) {
        intakeService.delete(id)
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    fun updateIntake(@PathVariable("id") id: Long, @RequestBody intakeRequest: IntakeRequest) {
        intakeService.update(id, intakeRequest)
    }

    @GetMapping("/{id}")
    fun getIntake(@PathVariable("id") id: Long): IntakeResponse {
        val intake = intakeService.get(id)
        return modelMapper.map(intake, IntakeResponse::class.java)
    }

    @GetMapping
    fun getIntakes(searchIntakeRequest: SearchIntakeRequest): List<IntakeResponse> {
        return intakeService.search(searchIntakeRequest)
            .map { modelMapper.map(it, IntakeResponse::class.java) }
    }
}