package io.freddie.foodcalendar

import org.springframework.security.authentication.AnonymousAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/auth")
class AuthController {
    @GetMapping("/check")
    fun check(): Boolean {
        val authentication = SecurityContextHolder.getContext().authentication
        return authentication !is AnonymousAuthenticationToken
    }
}