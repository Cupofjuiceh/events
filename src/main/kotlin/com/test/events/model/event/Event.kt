package com.test.events.model.event

import com.test.events.model.user.User
import java.util.*
import javax.persistence.*

@Entity
@Table(name = "events")
data class Event(

        @Id
        @Column(name = "id")
        @GeneratedValue(strategy = GenerationType.AUTO)
        var id: Long = 0,

        @Column(name = "title")
        val title: String = "",

        @Column(name = "category")
        val category: String = "",

        @Column(name = "description")
        val description: String = "",

        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "user_id")
        val author: User? = null,

        @ManyToMany(fetch = FetchType.EAGER)
        val users: Set<User>? = mutableSetOf(),

        @Temporal(TemporalType.TIMESTAMP)
        val date: Date? = null,

        val lat: Double = 0.0,
        val lng: Double = 0.0,

        val filename: String = ""

)