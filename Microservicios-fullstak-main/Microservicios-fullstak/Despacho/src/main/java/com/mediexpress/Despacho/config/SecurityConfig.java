package com.mediexpress.Despacho.config;
import com.mediexpress.Despacho.security.JwtFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
@Configuration @EnableWebSecurity
public class SecurityConfig {
    @Autowired private JwtFilter jwtFilter;
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf(c -> c.disable())
            .sessionManagement(s -> s.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/doc/**","/v3/api-docs/**","/swagger-ui/**").permitAll()
                .requestMatchers(HttpMethod.GET,"/api/v1/despachos/seguimiento/**").hasAnyRole("ADMIN","OPERADOR","CLIENTE")
                .requestMatchers(HttpMethod.GET,"/api/v1/despachos/**").hasAnyRole("ADMIN","OPERADOR","CLIENTE")
                .requestMatchers(HttpMethod.POST,"/api/v1/despachos").hasAnyRole("ADMIN","OPERADOR")
                .requestMatchers(HttpMethod.PUT,"/api/v1/despachos/**").hasAnyRole("ADMIN","OPERADOR")
                .requestMatchers(HttpMethod.DELETE,"/api/v1/despachos/**").hasRole("ADMIN")
                .anyRequest().authenticated())
            .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }
}
