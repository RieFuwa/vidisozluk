package com.bkabatas.ssozlukproject.configuration;
import com.bkabatas.ssozlukproject.security.JwtAuthenticationEntryPoint;
import com.bkabatas.ssozlukproject.security.JwtAuthenticationFilter;
import com.bkabatas.ssozlukproject.service.Impl.UserDetailsServiceImpl;
import lombok.Data;
import org.apache.tomcat.util.http.parser.Authorization;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Role;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;


@Configuration
@EnableWebSecurity
@Data
@RestController
public class SecurityConfig {

    private UserDetailsServiceImpl userDetailsService;
    private JwtAuthenticationEntryPoint handler;

    public SecurityConfig(UserDetailsServiceImpl userDetailsService, JwtAuthenticationEntryPoint handler) {
        this.userDetailsService = userDetailsService;
        this.handler = handler;
    }
    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter() {
        return new JwtAuthenticationFilter();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean //FRONTEND TARAFINDA FARKLI ORIGINDEN GELEN İSTEKLER İÇİN SIKINTI YAŞAMAMAK İÇİN YAZILAN TÜM İSTEKLERE İZİN VER.
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        config.addAllowedOrigin("*");
        config.addAllowedHeader("*");
        config.addAllowedMethod("OPTIONS");
        config.addAllowedMethod("HEAD");
        config.addAllowedMethod("GET");
        config.addAllowedMethod("PUT");
        config.addAllowedMethod("POST");
        config.addAllowedMethod("DELETE");
        config.addAllowedMethod("PATCH");
        source.registerCorsConfiguration("/**", config);
        return new CorsFilter(source);
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .cors()
                .and().csrf().disable()
                .exceptionHandling().authenticationEntryPoint(handler).and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
                .authorizeHttpRequests()
                .requestMatchers(HttpMethod.GET,"/user/getAll").hasAnyAuthority("ROLE_ADMIN")
                .requestMatchers(HttpMethod.GET,"/report/getAllPostReports{postId}").hasAnyAuthority("ROLE_ADMIN")
                .requestMatchers(HttpMethod.GET,"/report/getAll").hasAnyAuthority("ROLE_ADMIN")
                .requestMatchers(HttpMethod.GET,"/post/getTodayPost").permitAll()
                .requestMatchers(HttpMethod.POST,"/userAuth/**").permitAll()
                .requestMatchers(HttpMethod.GET,"/post/getAllPostTypePost{postTypeId}").permitAll()
                .requestMatchers(HttpMethod.GET,"/post/postAnswers{connectedPostId}").permitAll()
                .requestMatchers(HttpMethod.GET,"/post/getAllUserPost{userId}").permitAll()
                .requestMatchers(HttpMethod.GET,"/user/{userId}").permitAll()
                .requestMatchers(HttpMethod.GET,"/post/{postId}").permitAll()
                .requestMatchers(HttpMethod.POST,"/user/add").permitAll()
                .requestMatchers(HttpMethod.POST,"/role/addRoleToUser").permitAll()
                .anyRequest().authenticated();


        httpSecurity.addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
        return httpSecurity.build();

    }
}
