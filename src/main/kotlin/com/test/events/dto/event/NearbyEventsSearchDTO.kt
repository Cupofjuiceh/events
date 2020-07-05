package com.test.events.dto.event

data class NearbyEventsSearchDTO(
        val lat: Float = 0.0f,
        val lng: Float = 0.0f,
        val page: Int = 0,
        val size: Int = 20,
        val radius: Int = 6000
)