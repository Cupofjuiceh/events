package com.test.events.model.user

import org.springframework.security.core.GrantedAuthority

enum class Roles: GrantedAuthority {
    REGISTERED_USER, ADMIN;

    override fun getAuthority(): String {
        return this.name
    }
}