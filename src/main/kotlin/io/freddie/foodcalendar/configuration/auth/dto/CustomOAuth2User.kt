package io.freddie.foodcalendar.configuration.auth.dto

import io.freddie.foodcalendar.domain.user.entity.User
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.oauth2.core.user.OAuth2User
import org.springframework.util.Assert

class CustomOAuth2User(val user: User, private val attributes: MutableMap<String, Any>, private val authorities: MutableCollection<out GrantedAuthority>) : OAuth2User {
    init {
        Assert.notEmpty(attributes, "attributes cannot be empty")
    }
    override fun getName(): String {
        return user.username
    }

    override fun getAttributes(): MutableMap<String, Any> {
        return attributes
    }

    override fun getAuthorities(): MutableCollection<out GrantedAuthority> {
        return authorities
    }
}