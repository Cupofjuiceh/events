package com.test.events.dto.event

data class NearbyEventsResponseDTO(
        val totalEvents: Long,
        val eventResponses: List<EventResponseDTO>
)