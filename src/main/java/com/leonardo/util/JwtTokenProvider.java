package com.leonardo.util;

import java.util.Date;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

import com.leonardo.document.UserDetailImpl;
import com.leonardo.security.UserPrincipal;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import lombok.extern.log4j.Log4j2;

@Component
@Log4j2
public class JwtTokenProvider {
	
	// Đoạn JWT_SECRET này là bí mật, chỉ có phía server biết
    private final String JWT_SECRET = "leonardo";

    //Thời gian có hiệu lực của chuỗi jwt
    private final long JWT_EXPIRATION = 604800000L;
    
    // Tạo ra jwt từ thông tin user
    public String generateToken(UserDetailImpl userDetails) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + JWT_EXPIRATION);
        // Tạo chuỗi json web token từ id của user.
        return Jwts.builder()
                   .setSubject(userDetails.getId())
                   .setIssuedAt(now)
                   .setExpiration(expiryDate)
                   .signWith(SignatureAlgorithm.HS512, JWT_SECRET)
                   .compact();
    }
    
    public String generateToken(UserPrincipal userPrincipal) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + JWT_EXPIRATION);
        // Tạo chuỗi json web token từ id của user.
        String user = userPrincipal.getUsername();
        return Jwts.builder()
                   .setSubject(userPrincipal.getId())
                   .setIssuedAt(now)
                   .setExpiration(expiryDate)
                   .signWith(SignatureAlgorithm.HS512, JWT_SECRET)
                   .compact();
    }

    // Lấy thông tin user từ jwt
    public String getIdFromJWT(String token) {
        Claims claims = Jwts.parser()
                            .setSigningKey(JWT_SECRET)
                            .parseClaimsJws(token)
                            .getBody();

        return claims.getSubject();
    }

    public boolean validateToken(String authToken) {
        try {
            Jwts.parser().setSigningKey(JWT_SECRET).parseClaimsJws(authToken);
            return true;
        } catch (MalformedJwtException ex) {
//            log.error("Invalid JWT token");
        } catch (ExpiredJwtException ex) {
//            log.error("Expired JWT token");
        } catch (UnsupportedJwtException ex) {
//            log.error("Unsupported JWT token");
        } catch (IllegalArgumentException ex) {
//            log.error("JWT claims string is empty.");
        }
        return false;
    }
}
