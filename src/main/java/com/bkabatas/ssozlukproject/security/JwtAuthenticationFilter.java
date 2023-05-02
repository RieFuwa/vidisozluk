package com.bkabatas.ssozlukproject.security;
import com.bkabatas.ssozlukproject.service.Impl.UserDetailsServiceImpl;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;



@Data
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    //REQUEST GELDIGINDE FILTER ASAMASINDAN GEÇER+ EXTRA BİR AŞAMA KENDİMİZ YAZIYORUZ. JWT FILTER AŞAMASI EKLİYORUZ.

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            String jwtToken = extractJwtFromRequest(request);
            if(StringUtils.hasText(jwtToken) && jwtTokenProvider.validateToken(jwtToken)) { //JWT VALIDATE TOKENA GÖNDERİYORUZ VE VALIDATE MI BAKIYORUZ
                Long id = jwtTokenProvider.getUserIdFromJwt(jwtToken);
                UserDetails user = userDetailsService.loadUserById(id);
                if(user != null) { // USER VAR ISE DOGRULAMA YAPIYORUZ.
                    UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
                    auth.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(auth);//security için verileri tutar. name pass gibi,
                }
            }
        } catch(Exception e) {
            return;
        }
        filterChain.doFilter(request, response);
    }
    private String extractJwtFromRequest(HttpServletRequest request) { //BİR BEARER + TOKEN
        String bearer = request.getHeader("Authorization");
        if(StringUtils.hasText(bearer) && bearer.startsWith("Bearer "))

            return bearer.substring("Bearer".length() + 1);
        return null;
    }

}