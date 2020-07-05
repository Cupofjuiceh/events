package com.test.events.dto.event

import java.util.*

data class NewEventDTO(
        val title: String,
        val category: String,
        val description: String,
        val lat: Double = 0.0,
        val lng: Double = 0.0,
        val date: Date
)