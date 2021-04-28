package io.freddie.foodcalendar.configuration.auth.dto

import io.freddie.foodcalendar.domain.user.entity.Gender
import io.freddie.foodcalendar.domain.user.entity.Role
import io.freddie.foodcalendar.domain.user.entity.User

data class OAuthAttributes(
    val username: String,
    val email: String,
    val ageGroup: Int,
    val gender: Gender,
    val attributes: Map<String, Any>
) {
    fun toUserEntity(): User {
        return User(
            username = username,
            email = email,
            ageGroup = ageGroup,
            role = Role.ROLE_USER,
            gender = gender.toEntityGender()
        )
    }

    enum class Gender {
        MALE, FEMALE;

        fun toEntityGender(): io.freddie.foodcalendar.domain.user.entity.Gender {
            return if (this == FEMALE) {
                io.freddie.foodcalendar.domain.user.entity.Gender.FEMALE
            } else io.freddie.foodcalendar.domain.user.entity.Gender.MALE
        }
    }

    companion object {
        fun ofNaver(attributes: Map<String, Any>): OAuthAttributes {
            val response = attributes["response"] as Map<String, Any>

            val age = response["age"] as String
            val gender = response["gender"] as String
            return OAuthAttributes(
                username = response["nickname"] as String,
                email = response["email"] as String,
                ageGroup = age.split("-").first().toInt(),
                gender = if (gender == "M") Gender.MALE else Gender.FEMALE,
                attributes = response
            )
        }

    }
}
