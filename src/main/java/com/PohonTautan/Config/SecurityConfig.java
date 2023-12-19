package com.PohonTautan.Config;

import java.util.EnumSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.session.HttpSessionEventPublisher;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.handler.HandlerMappingIntrospector;

import jakarta.servlet.DispatcherType;
import jakarta.servlet.Filter;

@EnableWebSecurity
@Configuration
public class SecurityConfig {

    @Autowired
    private CustomAuthProvider customAuthProvider;

    @Bean
    public static PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Autowired
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(customAuthProvider);
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring()
                .requestMatchers("/static/**");
    }

    @Bean
    public HttpSessionEventPublisher httpSessionEventPublisher() {
        return new HttpSessionEventPublisher();
    }
    
    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.addAllowedOrigin("*");
        configuration.addAllowedMethod("*");
        configuration.addAllowedHeader("*");
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    @Bean
	static FilterRegistrationBean<Filter> handlerMappingIntrospectorCacheFilter(HandlerMappingIntrospector hmi) {
		Filter cacheFilter = hmi.createCacheFilter();
		FilterRegistrationBean<Filter> registrationBean = new FilterRegistrationBean<>(cacheFilter);
		registrationBean.setOrder(Ordered.HIGHEST_PRECEDENCE);
		registrationBean.setDispatcherTypes(EnumSet.allOf(DispatcherType.class));
		return registrationBean;
	}

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.cors();
        http.csrf()
            .disable()
            .headers().frameOptions().sameOrigin().and()
            .authorizeRequests()
            .requestMatchers("/").permitAll()
            .requestMatchers("/adm/**").authenticated()
            .and()
            .formLogin()
            .failureUrl("/login?error")
            .loginPage("/login")
            .defaultSuccessUrl("/adm/dasboard", true)
            .permitAll()
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
