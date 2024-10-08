package com.example.bookshop.jwt;

import com.example.bookshop.enums.Role;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class SecurityConfig {
    String[] PUBLIC_ENDPOINT = {"/auth/token", "/auth/introspect", "/auth/logout", "/auth/refresh","/api/books/create","/api/chapter/create/*","/api/admin/category/create","/api/comment/**","/notification/token"};
    String[] PUBLIC_ENDPOINT_GET = {"/api/books/**","/api/chapter/**","/api/admin/category"};
    String [] PUBLIC_ENDPOINT_PUT = {"/api/books/**","/api/chapter/**","/api/admin/category"};
    String [] PRIVATE_ENDPOINT_POST = {"/api/comment/**"};
    CustomJwtDecoder jwtDecoder;
    RestAccessDeniedHandler restAccessDeniedHandler;

    public SecurityConfig(CustomJwtDecoder jwtDecoder, RestAccessDeniedHandler restAccessDeniedHandler) {
        this.jwtDecoder = jwtDecoder;
        this.restAccessDeniedHandler = restAccessDeniedHandler;
    }


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.authorizeHttpRequests(request ->
                request
                        .requestMatchers(HttpMethod.POST, PUBLIC_ENDPOINT)
                        .permitAll()
                        .requestMatchers(HttpMethod.POST , "/api/users/create")
                        .permitAll()
                        .requestMatchers(HttpMethod.GET,PUBLIC_ENDPOINT_GET)
                        .permitAll()
                        .requestMatchers(HttpMethod.PUT,PUBLIC_ENDPOINT_PUT)
                        .permitAll()
                        .requestMatchers(HttpMethod.POST,PRIVATE_ENDPOINT_POST )
                        .hasAuthority(Role.ROLE_USER.name())
                        .anyRequest()
                        .authenticated());

        httpSecurity.oauth2ResourceServer(oauth2 ->
                        oauth2.jwt(jwtConfigurer -> jwtConfigurer.decoder(jwtDecoder)
                                        .jwtAuthenticationConverter(jwtAuthenticationConverter()))
                                .authenticationEntryPoint(new JwtAuthenticationEntryPoint()))
                .exceptionHandling(handler -> handler.accessDeniedHandler(restAccessDeniedHandler))
        ;

        httpSecurity.csrf(AbstractHttpConfigurer::disable);


        return httpSecurity.build();
    }

    @Bean
    public CorsFilter corsFilter() {
        CorsConfiguration config = new CorsConfiguration();
        config.addAllowedOrigin("http://localhost:3000");
        config.addAllowedMethod("*");
        config.addAllowedHeader("*");
        UrlBasedCorsConfigurationSource urlBasedCorsConfigurationSource = new UrlBasedCorsConfigurationSource();
        urlBasedCorsConfigurationSource.registerCorsConfiguration("/**", config);
        return new CorsFilter(urlBasedCorsConfigurationSource);

    }

    @Bean
    JwtAuthenticationConverter jwtAuthenticationConverter() {
        JwtGrantedAuthoritiesConverter jwtGrantedAuthoritiesConverter = new JwtGrantedAuthoritiesConverter();
        jwtGrantedAuthoritiesConverter.setAuthorityPrefix("");
        JwtAuthenticationConverter jwtAuthenticationConverter = new JwtAuthenticationConverter();
        jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(jwtGrantedAuthoritiesConverter);
        return jwtAuthenticationConverter;
    }


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(10);
    }

}
