package br.com.fiap.projeto_mottu.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SegurancaConfig {

	@Bean
    public SecurityFilterChain chain(HttpSecurity http) throws Exception {

        http.authorizeHttpRequests((request) -> request
                    .requestMatchers("/h2-console/**").hasAuthority("ADMIN")
                    .requestMatchers("/funcionario/novo").hasAuthority("ADMIN")
                    .anyRequest().authenticated()
                )
            .formLogin((login) -> login
                    .loginPage("/login")
                    .usernameParameter("email")      // mapeia o campo email
                    .passwordParameter("password")   // mantém o padrão password
                    .defaultSuccessUrl("/index", true)
                    .failureUrl("/login?falha=true")
                    .permitAll()
            )
            .logout((logout) -> logout
                    .logoutUrl("/logout")
                    .logoutSuccessUrl("/login?logout=true")
                    .permitAll()
            )
            .exceptionHandling((exception) -> 
                exception.accessDeniedHandler((request, response, ex) -> {
                    response.sendRedirect("/acesso_negado");
                })
            );

    http.headers(headers -> headers.frameOptions().disable()); // Permite uso de frames para H2
    http.csrf(csrf -> csrf.ignoringRequestMatchers("/h2-console/**")); // Desabilita CSRF só para H2
    return http.build();
    }

	@Bean
	public PasswordEncoder encoder() {
		return new BCryptPasswordEncoder();
	}
}
