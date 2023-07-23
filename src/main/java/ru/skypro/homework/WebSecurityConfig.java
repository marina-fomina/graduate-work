package ru.skypro.homework;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
public class WebSecurityConfig {

  private static final String[] AUTH_WHITELIST = {
    "/swagger-resources/**",
    "/swagger-ui.html",
    "/v3/api-docs",
    "/webjars/**",
    "/login",
    "/register",
    "/ads"
  };

//  @Bean
//  public InMemoryUserDetailsManager userDetailsService() {
//    UserDetails user =
//        User.builder()
//            .username("user@gmail.com")
//            .password("password")
//            .passwordEncoder((plainText) -> passwordEncoder().encode(plainText))
//            .roles("USER")
//            .build();
//    return new InMemoryUserDetailsManager(user);
//  }

  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    http.csrf()
        .disable()
        .authorizeHttpRequests(
            (authorization) ->
                authorization
                    .mvcMatchers(AUTH_WHITELIST)
                    .permitAll()
                    .mvcMatchers(HttpMethod.GET, "/ads/image/**", "/ads", "/users/avatar/**")
                    .permitAll()
                    .mvcMatchers("/ads/**", "/users/**")
                    .authenticated()
        )
        .cors()
        .and()
        .httpBasic(withDefaults());
    return http.build();
  }
  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }
}
