package com.bkabatas.ssozlukproject.security;
import com.auth0.jwt.algorithms.Algorithm;
import com.bkabatas.ssozlukproject.service.UserService;
import io.jsonwebtoken.*;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;
import java.util.Date;
import java.util.stream.Collectors;

@Component
@Data
public class JwtTokenProvider {
    @Value("${ssozluk.app.secret}")
    private String APP_SECRET; //TOKEN OLUSTURMA
    @Value("${ssozluk.expires.in}")
    private Long EXPIRES_IN; // KAÇ SANIYE TOKEN DEGERİNİ YİTİRİR.

    private JwtUserDetails jwtUserDetails;
    private UserService userService;

    public String generateJwtToken( Authentication auth){
        JwtUserDetails userDetails = (JwtUserDetails) auth.getPrincipal();//Auth edecegimiz User
        Date expireDate = new Date(new Date().getTime() + 60*60*60);
        return Jwts.builder().setSubject((userDetails.getId().toString()))
                //USER ID SI
                .setIssuedAt(new Date()).setExpiration(expireDate)
                .claim("roles",userDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
                .signWith(SignatureAlgorithm.HS512, APP_SECRET).compact();

    }

    public String generateJwtTokenByUserId(Long userId) {

        Date expireDate = new Date(new Date().getTime() + EXPIRES_IN);
        return Jwts.builder().setSubject(Long.toString(userId))
                .setIssuedAt(new Date()).setExpiration(expireDate)
                .signWith(SignatureAlgorithm.HS512, APP_SECRET).compact();
    }

    boolean validateToken(String token) { //FRONTEND DEN GELEN TOKEN DOGRU MU KONTROL İŞLEMİ YAPIYORUZ.
        try {
            Jwts.parser().setSigningKey(APP_SECRET).parseClaimsJws(token); //PARS EDEBİLİYORSAK BİZİM TOKENIMIZDIR.
            return !isTokenExpired(token);
        } catch (SignatureException e) {
            return false;
        } catch (MalformedJwtException e) {
            return false;
        } catch (ExpiredJwtException e) {
            return false;
        } catch (UnsupportedJwtException e) {
            return false;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }
    private boolean isTokenExpired(String token) {
        Date expiration = Jwts.parser().setSigningKey(APP_SECRET).parseClaimsJws(token).getBody().getExpiration();
        return expiration.before(new Date());
    }

    Long getUserIdFromJwt(String token) {
        Claims claims = Jwts.parser().setSigningKey(APP_SECRET).parseClaimsJws(token).getBody(); //CLAIMS JWT ALTINDA HAZIR GELEN BİR METHOD
        return Long.parseLong(claims.getSubject()); //KEY'DEN USER A GİDİYORUZ. ŞİFREYİ GERİ ÇÖZÜYORUZ.
    }

}