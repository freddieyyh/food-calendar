package io.freddie.foodcalendar.domain.user.entity

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.EnumType
import javax.persistence.Enumerated
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id

@Entity
data class User(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0,
    @Column(nullable = false)
    var username: String,
    @Column(nullable = false, unique = true)
    var email: String,
    @Column(nullable = false)
    var ageGroup: Int,
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    var gender: Gender,
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    var role: Role
)
