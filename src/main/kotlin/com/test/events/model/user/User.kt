package com.test.events.model.user

import com.test.events.model.event.Event
import org.hibernate.annotations.Type
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import java.time.LocalDateTime
import java.util.*
import javax.persistence.*

@Entity
@Table(name = "users")
data class User(

        @Id
        @GeneratedValue @Type(type = "uuid-char")
        @Column(name = "id", length = 36, nullable = false, updatable = false)
        var id: UUID? = null,

        @Column(name = "email", nullable = false, unique = true)
        var email: String = "",

        @Column(name = "password", nullable = false)
        private var password: String = "",

        @Column(name = "full_name", nullable = false)
        var fullName: String = "",

        @Column(name = "verification_code")
        var activationCode: Int = 0,

        @Column(name = "is_email_verified")
        var isEmailVerified: Boolean = false,

        @Column(name = "date_joined")
        var dateJoined: LocalDateTime? = null,

        @ManyToMany(cascade = [CascadeType.ALL], fetch = FetchType.EAGER)
        @JoinTable(name = "user_events", joinColumns = [JoinColumn(name = "user_id", referencedColumnName = "id")], inverseJoinColumns = [JoinColumn(name = "event_id", referencedColumnName = "id")])
        var events: MutableSet<Event> = mutableSetOf(),

        @ElementCollection(targetClass = Roles::class, fetch = FetchType.EAGER)
        @CollectionTable(name = "user_role", joinColumns = [JoinColumn(name = "user_id")])
        @Enumerated(EnumType.STRING)
        val roles: MutableSet<Roles> = mutableSetOf()
) : UserDetails {

    override fun getAuthorities(): MutableCollection<out GrantedAuthority> =
            roles


    override fun isEnabled(): Boolean =
            isEmailVerified


    override fun getUsername(): String =
            email


    override fun isCredentialsNonExpired(): Boolean =
            isEmailVerified


    override fun getPassword(): String =
            password

    override fun isAccountNonExpired(): Boolean =
            isEmailVerified


    override fun isAccountNonLocked(): Boolean =
            isEmailVerified

}