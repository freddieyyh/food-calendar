package io.freddie.foodcalendar.domain.dailyreview.entity

import java.time.ZonedDateTime
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id

@Entity
data class DailyReview(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private var id: Long = 0,
    private var date: ZonedDateTime,
    private var description: String
)
