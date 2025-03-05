package SODEFEND.SODEFEND.Secuirty;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtUtil {

    // Güvenlik anahtarını sabit tanımlamak yerine, environment variable'dan alman daha güvenli olur.
    private static final String SECRET_KEY = "SECRET_ANAHTARIN_COK_UZUN_OLMALI";

    // Token geçerlilik süresi (örneğin 1 saat).
    private static final long EXPIRATION_TIME = 60 * 60 * 1000;

    // Token üretme metodu
    public String generateToken(String username) {
        return Jwts.builder()
                .setSubject(username)
                .setIssuer("SODEFEND")
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY.getBytes())
                .compact();
    }

    // Token’dan username'i çekme
    public String extractUsername(String token) {
        return getClaims(token).getSubject();
    }

    // Token valid mi kontrol
    public boolean validateToken(String token, String userDetailsUsername) {
        String username = extractUsername(token);
        return (username.equals(userDetailsUsername) && !isTokenExpired(token));
    }

    // Token süresi doldu mu?
    private boolean isTokenExpired(String token) {
        return getClaims(token).getExpiration().before(new Date());
    }

    // Token içindeki bilgiler (Claims)
    private Claims getClaims(String token) {
        return Jwts.parser()
                .setSigningKey(SECRET_KEY.getBytes())
                .parseClaimsJws(token)
                .getBody();
    }
}
