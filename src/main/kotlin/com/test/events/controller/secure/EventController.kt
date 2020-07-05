package com.test.events.controller.secure

import com.test.events.dto.event.NearbyEventsSearchDTO
import com.test.events.dto.event.NewEventDTO
import com.test.events.model.user.User
import com.test.events.service.event.EventService
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/secured/event")
class EventController(
        private val eventService: EventService
) {

    @GetMapping("/{id}")
    fun getEvent(@PathVariable id: Long) = eventService.findEventById(id)

    @PostMapping
    fun createEvent(@AuthenticationPrincipal currentUser: User, @RequestBody newEventDTO: NewEventDTO) =
            ResponseEntity.ok(eventService.saveEvent(currentUser, newEventDTO))

    @DeleteMapping("/{id}")
    fun deleteEvent(@AuthenticationPrincipal currentUser: User, @PathVariable id: Long) =
            ResponseEntity.ok(eventService.deleteEvent(currentUser, id))


    @PostMapping("/nearbyEvents")
    fun searchEvents(@RequestBody nearbyEventsSearchDTO: NearbyEventsSearchDTO) =
            ResponseEntity.ok(eventService.findEventsNearby(nearbyEventsSearchDTO))
}