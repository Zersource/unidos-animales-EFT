package com.duoc.unidosanimales.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

/**
 * Configuración de Spring Security.
 *
 * Roles:
 *   ADMIN       → acceso total
 *   COORDINADOR → gestiona mascotas y solicitudes
 *   USUARIO     → solo lectura y envío de solicitudes
 *
 * Usuarios:
 *   admin       / admin123  → ADMIN
 *   coordinador / coord123  → COORDINADOR
 *   usuario     / user123   → USUARIO
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(requests -> requests
                // ── Recursos estáticos y páginas públicas ─────────────
                .requestMatchers("/css/**", "/js/**", "/images/**").permitAll()
                .requestMatchers("/", "/login", "/mascotas/catalogo").permitAll()

                // ── Solo ADMIN y COORDINADOR gestionan mascotas ───────
                .requestMatchers("/mascotas/nueva", "/mascotas/guardar",
                                 "/mascotas/editar/**", "/mascotas/eliminar/**")
                    .hasAnyRole("ADMIN", "COORDINADOR")

                // ── Solo ADMIN y COORDINADOR gestionan adoptantes ─────
                .requestMatchers("/adoptantes/**")
                    .hasAnyRole("ADMIN", "COORDINADOR")

                // ── Aprobar/rechazar solicitudes: ADMIN y COORDINADOR ──
                .requestMatchers("/solicitudes/aprobar/**", "/solicitudes/rechazar/**")
                    .hasAnyRole("ADMIN", "COORDINADOR")

                // ── Resto requiere autenticación ──────────────────────
                .anyRequest().authenticated()
            )
            .formLogin(form -> form
                .loginPage("/login")
                .defaultSuccessUrl("/dashboard", true)
                .failureUrl("/login?error=true")
                .permitAll()
            )
            .logout(logout -> logout
                .logoutSuccessUrl("/login?logout=true")
                .permitAll()
            );

      http.headers(headers -> headers
            .contentSecurityPolicy(csp -> csp
                .policyDirectives("default-src 'self'; style-src 'self' 'unsafe-inline'; script-src 'self'")
            )
        );

        return http.build();
    }

@Bean
public UserDetailsService userDetailsService() {
    UserDetails admin = User.builder()
            .username("admin")
            .password(passwordEncoder().encode("Admin@Unidos2024!"))
            .roles("ADMIN", "COORDINADOR", "USUARIO")
            .build();

    UserDetails coordinador = User.builder()
            .username("coordinador")
            .password(passwordEncoder().encode("Coord@Unidos2024!"))
            .roles("COORDINADOR", "USUARIO")
            .build();

    UserDetails usuario = User.builder()
            .username("usuario")
            .password(passwordEncoder().encode("User@Unidos2024!"))
            .roles("USUARIO")
            .build();

    return new InMemoryUserDetailsManager(admin, coordinador, usuario);
}

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
