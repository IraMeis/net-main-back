package com.morena.netMain.auth.service;

import com.morena.netMain.auth.model.AuthUser;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.Map;

@Slf4j
@Component
public class JwtProvider {

    private final String jwtAccessSecret;
    private final String jwtRefreshSecret;
    private final long accessExpirationMinutes;
    private final long refreshExpirationDays;

    //todo добавить нормальные пароли
    public JwtProvider(
            @Value("${jwt.secret.access}") String jwtAccessSecret,
            @Value("${jwt.secret.refresh}") String jwtRefreshSecret,
            @Value("${accessTokenMinutes}") long accessExpirationMinutes,
            @Value("${refreshTokenDays}") long refreshExpirationDays
    ) {
        this.jwtAccessSecret = jwtAccessSecret;
        this.jwtRefreshSecret = jwtRefreshSecret;
        this.accessExpirationMinutes = accessExpirationMinutes;
        this.refreshExpirationDays = refreshExpirationDays;
    }

    //todo время жизни установить 10 мин - после содинения с фронтом
    public String generateAccessToken(@NonNull AuthUser user) {
        final ZonedDateTime expIn = LocalDateTime.now().plusMinutes(accessExpirationMinutes).atZone(ZoneId.systemDefault());
        final Date accessExpiration = Date.from(expIn.toInstant());
        return Jwts.builder()
                .setSubject(user.getLogin())
                .setExpiration(accessExpiration)
                .signWith(SignatureAlgorithm.HS512, jwtAccessSecret)
                .claim("roles", user.getRoles())
                .claim("login", user.getLogin())
                .claim("id", user.getId())
                .addClaims(Map.of("expires", expIn.withZoneSameInstant(ZoneId.of("GMT")).toLocalDateTime().toString()))
                .compact();
    }

    public String generateRefreshToken(@NonNull AuthUser user) {
        final ZonedDateTime expIn = LocalDateTime.now().plusDays(refreshExpirationDays).atZone(ZoneId.systemDefault());
        final Date refreshExpiration = Date.from(expIn.toInstant());
        return Jwts.builder()
                .setSubject(user.getLogin())
                .setExpiration(refreshExpiration)
                .addClaims(Map.of("expires", expIn.withZoneSameInstant(ZoneId.of("GMT")).toLocalDateTime().toString()))
                .signWith(SignatureAlgorithm.HS512, jwtRefreshSecret)
                .compact();
    }

    public boolean validateAccessToken(@NonNull String token) {
        return validateToken(token, jwtAccessSecret);
    }

    public boolean validateRefreshToken(@NonNull String token) {
        return validateToken(token, jwtRefreshSecret);
    }

    private boolean validateToken(@NonNull String token, @NonNull String secret) {
        try {
            Jwts.parser().setSigningKey(secret).parseClaimsJws(token);
            return true;
        } catch (ExpiredJwtException expEx) {
            log.error("Token expired", expEx);
        } catch (UnsupportedJwtException unsEx) {
            log.error("Unsupported jwt", unsEx);
        } catch (MalformedJwtException mjEx) {
            log.error("Malformed jwt", mjEx);
        } catch (SignatureException sEx) {
            log.error("Invalid signature", sEx);
        } catch (Exception e) {
            log.error("invalid token", e);
        }
        return false;
    }

    public Claims getAccessClaims(@NonNull String token) {
        return getClaims(token, jwtAccessSecret);
    }

    public Claims getRefreshClaims(@NonNull String token) {
        return getClaims(token, jwtRefreshSecret);
    }

    private Claims getClaims(@NonNull String token, @NonNull String secret) {
        return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
    }

}