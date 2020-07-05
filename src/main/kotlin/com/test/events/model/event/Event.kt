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
        var title: String = "",

        @Column(name = "category")
        var category: String = "",

        @Column(name = "description")
        var description: String = "",

        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "user_id")
        var author: User? = null,

        @ManyToMany(fetch = FetchType.EAGER)
        var users: Set<User>? = mutableSetOf(),

        @Temporal(TemporalType.TIMESTAMP)
        var date: Date? = null,

        var lat: Double = 0.0,
        var lng: Double = 0.0

)