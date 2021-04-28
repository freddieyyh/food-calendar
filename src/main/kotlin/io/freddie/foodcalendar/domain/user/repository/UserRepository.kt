package io.freddie.foodcalendar.domain.user.repository

import io.freddie.foodcalendar.domain.user.entity.User
import org.springframework.data.jpa.repository.JpaRepository

interface UserRepository: JpaRepository<User, Long> {
    fun findByEmail(email: String) : User?
}