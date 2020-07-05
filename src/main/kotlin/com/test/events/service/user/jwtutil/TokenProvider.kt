package com.test.events.service.user.jwtutil

import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import java.util.*

@Component
class TokenProvider {
    private val logger: Logger = LoggerFactory.getLogger(TokenProvider::class.java)

    @Value("\${events.app.jwtSecret}")
    lateinit var secret: String
    @Value("\${events.app.jwtExpiration}")
    var exp:Int?=0

    fun validateToken(token: String): Boolean {
        try {
            Jwts.parser().setSigningKey(secret).parseClaimsJws(token)
            return true
        } catch (e: Exception) {
            logger.error("Invalid token", e)
        }
        return false
    }

    fun generateToken(email: String): String {
        return Jwts.builder()
                .setSubject(email)
                .setIssuedAt(Date())
                .setExpiration(Date((Date()).time + exp!! * 1000))
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact()
    }

    fun getUserNameFromToken(token: String): String {
        return Jwts.parser()
                .setSigningKey(secret)
                .parseClaimsJws(token)
                .body.subject
    }
}