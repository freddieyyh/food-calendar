package io.freddie.foodcalendar.error

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import org.springframework.boot.autoconfigure.web.ServerProperties
import org.springframework.boot.autoconfigure.web.servlet.error.AbstractErrorController
import org.springframework.boot.web.error.ErrorAttributeOptions
import org.springframework.boot.web.servlet.error.ErrorAttributes
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.RequestMapping
import javax.servlet.http.HttpServletRequest

//@Controller
@RequestMapping("\${server.error.path:\${error.path:/error}}")
class CustomErrorController(errorAttributes: ErrorAttributes, private val serverProperties: ServerProperties) :
    AbstractErrorController(errorAttributes) {

    @RequestMapping
    fun error(request: HttpServletRequest): ResponseEntity<Map<String, String>> {
        val status = getStatus(request)
        if (status == HttpStatus.NO_CONTENT) {
            return ResponseEntity.noContent().build()
        }

        val attributes = getErrorAttributes(request, ErrorAttributeOptions.defaults())
        val responseBody = mapOf(
            "uri" to attributes["path"]?.toString().orEmpty(),
            "customMessage" to attributes["message"]?.toString().orEmpty()
        )

        return ResponseEntity(responseBody, status)
    }

    override fun getErrorPath(): String {
        return serverProperties.error.path
    }
}