package com.test.events.repository.jdbctemplate.impl

import com.test.events.dto.event.NearbyEventsSearchDTO
import com.test.events.model.event.Event
import com.test.events.repository.jdbctemplate.JdbcTemplateEventRepository
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
import org.springframework.stereotype.Repository
import java.util.*
import kotlin.collections.HashMap

@Repository
class JdbcTemplateEventRepositoryImpl(
        private val namedParameterJdbcTemplate: NamedParameterJdbcTemplate
) : JdbcTemplateEventRepository {

    override fun nearbySearch(nearbyEventsSearchDTO: NearbyEventsSearchDTO, eventCollection: MutableCollection<Event>): Long? {
        val params = MapSqlParameterSource()
                .addValue("radius", nearbyEventsSearchDTO.radius)
                .addValue("lat", nearbyEventsSearchDTO.lat)
                .addValue("lng", nearbyEventsSearchDTO.lng)
                .addValue("limit", nearbyEventsSearchDTO.size)
                .addValue("offset", nearbyEventsSearchDTO.size * nearbyEventsSearchDTO.page)

        val selectQuery = "SELECT *, ST_Distance(ST_MakePoint(lng, lat), ST_MakePoint(:lng, :lat)::geography) as distance "
        val orderByDistance = " ORDER BY distance LIMIT :limit OFFSET :offset "
        val countQuery = "SELECT COUNT(1) "
        val query = " FROM events WHERE ST_DWithin(ST_MakePoint(lng, lat), ST_MakePoint(:lng, :lat)::geography, :radius) "

        val counter = namedParameterJdbcTemplate.queryForObject("$countQuery $query", params, Long::class.java)
        val dbResult = namedParameterJdbcTemplate.queryForList("$selectQuery $query $orderByDistance", params)

        eventCollection.addAll(convertDbResultToEventList(dbResult))
        return counter
    }

    private fun convertDbResultToEventList(result: List<Map<String, Any>>): MutableCollection<Event> {
        val resultMap = HashMap<Long, Event>()

        result.forEach {
            val event = resultMap.computeIfAbsent(it["id"] as Long) { _ ->
                val tempEvent = Event()
                tempEvent.id = it["id"] as Long
                tempEvent.date = it["date"] as Date
                tempEvent.description = it["description"] as String
                tempEvent.title = it["title"] as String
                tempEvent.lat = it["lat"] as Double
                tempEvent.lng = it["lng"] as Double

                tempEvent
            }
            event.category = it["category"] as String
        }

        return resultMap.values
    }
}