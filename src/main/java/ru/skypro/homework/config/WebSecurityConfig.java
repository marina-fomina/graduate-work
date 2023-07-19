package ru.skypro.homework.config;

import static org.springframework.security.config.Customizer.withDefaults;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import ru.skypro.homework.dto.Role;

import javax.sql.DataSource;

@EnableWebSecurity
@Configuration
public class WebSecurityConfig {

  private static final String[] AUTH_WHITELIST = {
    "/swagger-resources/**",
    "/swagger-ui.html",
    "/v3/api-docs",
    "/webjars/**",
    "/login",
    "/register"
  };

  @Bean
  public UserDetailsManager userDetailsManager(DataSource dataSource,
                                               AuthenticationManager authenticationManager) {
    JdbcUserDetailsManager jdbcUserDetailsManager = new JdbcUserDetailsManager(dataSource);
    jdbcUserDetailsManager.setAuthenticationManager(authenticationManager);
    return jdbcUserDetailsManager;
  }

  @Bean
  public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration)
          throws Exception {
    return authenticationConfiguration.getAuthenticationManager();
  }

  // Бин SecurityFilterChain представляет
  // цепочку фильтров для обработки входящих запросов
  // в соответствии с настройками безопасности.
  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    http.csrf()
        .disable()
        .authorizeHttpRequests(
            (authorization) ->
                authorization
                    .mvcMatchers(AUTH_WHITELIST).permitAll()
                    .mvcMatchers(HttpMethod.GET, "/ads").permitAll() // Права для анонимного пользователя (только получение списка объявлений).
                    .mvcMatchers("/ads/**", "/users/**").hasRole(Role.USER.name()) // Права для авторизованного пользователя.
                    .mvcMatchers("/admin/**").hasRole(Role.ADMIN.name()) // Права для админа.
        )
        .logout().logoutUrl("/logout")
        .and()
        .cors()
        .and()
        .httpBasic(withDefaults());
    return http.build(); // Возвращаем сконфигурированную цепочку фильтров безопасности.
  }

  // Бин кодировщика паролей (для хеширования паролей пользователей)
  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }
}
