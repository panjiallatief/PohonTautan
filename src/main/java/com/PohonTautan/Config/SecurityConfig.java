package com.PohonTautan.Config;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.session.HttpSessionEventPublisher;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@EnableWebSecurity
@Configuration
public class SecurityConfig {

    // @Autowired
    // private CustomAuthProvider customAuthProvider;

    // @Bean
    // public PasswordEncoder passwordEncoder() {
    //     int rounds = 12;
    //     return new BCryptPasswordEncoder(rounds);
    // }

    @Bean
    public static PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // @Autowired
    // public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
    //     auth.authenticationProvider(customAuthProvider);
    // }

    @Bean
    public UserDetailsService userDetailsService(PasswordEncoder encoder) {

        UserDetails admin = User.withUsername("amiya")
                .password(encoder.encode("123"))
                .roles("ADMIN", "USER")
                .build();
 
        UserDetails user = User.withUsername("ejaz")
                .password(encoder.encode("123"))
                .roles("USER")
                .build();
 
        return new InMemoryUserDetailsManager(admin, user);
    }

    // @Bean
    // public AuthenticationManager authenticationManager(UserDetailsService customUserDetailsService) {

    //     DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
    //     authProvider.setUserDetailsService(customUserDetailsService);
    //     authProvider.setPasswordEncoder(passwordEncoder());

    //     List<AuthenticationProvider> providers =  List.of(authProvider);

    //     return new ProviderManager(providers);
    // }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring()
                .requestMatchers("/assets/**")
                .requestMatchers("/css/**")
                .requestMatchers("/image/**")
                .requestMatchers("/images/**")
                .requestMatchers("/img/**")
                .requestMatchers("/js/**")
                .requestMatchers("/json/**");
    }

    @Bean
    public HttpSessionEventPublisher httpSessionEventPublisher() {
        return new HttpSessionEventPublisher();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .headers().frameOptions().sameOrigin().and()
                .authorizeRequests()
                    .requestMatchers("/").permitAll()
                    .requestMatchers("/").authenticated()
                    .and()
                .formLogin()
                    .failureUrl("/login?error")
                    .loginPage("/login")
                    .and()
                .logout()
                    .deleteCookies("JSESSIONID")
                    .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                    .logoutSuccessUrl("/login")
                    .clearAuthentication(true)
                    .invalidateHttpSession(true)
                    .and()
                .sessionManagement()
                    .sessionFixation().migrateSession()
                    .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
                    .maximumSessions(1)
                    .expiredUrl("/login?expired")
                    .and()
                .invalidSessionUrl("/login");

        return http.build();
    }
}
