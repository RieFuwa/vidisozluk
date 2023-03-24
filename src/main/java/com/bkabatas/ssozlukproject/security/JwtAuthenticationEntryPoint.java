package com.bkabatas.ssozlukproject.security;
<<<<<<< HEAD
=======

>>>>>>> test
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import java.io.IOException;

<<<<<<< HEAD
@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {
    //TOKEN YAKALIYORSAK unauthorised döndür -401-
=======
import java.io.IOException;

@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

    //TOKEN YAKALIYORSAK unauthorised döndür -401-

>>>>>>> test
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, authException.getMessage());
    }
}
