package com.contasimplesmei.security

import io.jsonwebtoken.JwtException
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import io.jsonwebtoken.security.Keys
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import java.util.Date
import javax.crypto.SecretKey

private const val VALIDITY_IN_MS = 3600000

private val logger = LoggerFactory.getLogger(JwtTokenProvider::class.java)

@Component
class JwtTokenProvider {
    private val secretKey: SecretKey = Keys.secretKeyFor(SignatureAlgorithm.HS256)

    fun gerarToken(email: String): String {
        val now = Date()
        val validity = Date(now.time + VALIDITY_IN_MS)

        return Jwts
            .builder()
            .setSubject(email)
            .setIssuedAt(now)
            .setExpiration(validity)
            .signWith(secretKey)
            .compact()
    }

    fun getEmailDoToken(token: String): String =
        Jwts
            .parserBuilder()
            .setSigningKey(secretKey)
            .build()
            .parseClaimsJws(token)
            .body.subject

    fun tokenValido(token: String): Boolean =
        try {
            Jwts
                .parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
            true
        } catch (e: JwtException) {
            logger.warn("Token inválido: ${e.message}")
            false
        }
}
