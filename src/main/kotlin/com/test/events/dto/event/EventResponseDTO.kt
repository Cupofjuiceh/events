package com.test.events.dto.event

import com.test.events.model.event.Event
import java.util.*

data class EventResponseDTO(

        var id: Long = 0,
        var title: String,
        var category: String,
        var description: String,
        var date: Date? = null,
        var lat: Double = 0.0,
        var lng: Double = 0.0
) {
    constructor(event: Event): this(
            event.id,
            event.title,
            event.category,
            event.description,
            event.date,
            event.lat,
            event.lng
    )
}