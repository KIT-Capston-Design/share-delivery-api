package com.kitcd.share_delivery_api.security.configs;


import ch.qos.logback.core.net.ssl.SecureRandomFactoryBean;
import com.kitcd.share_delivery_api.security.filter.JWTAuthenticationFilter;
import com.kitcd.share_delivery_api.security.filter.JsonLoginProcessingFilter;
import com.kitcd.share_delivery_api.security.handler.CustomAccessDeniedHandler;
import com.kitcd.share_delivery_api.security.handler.JsonAuthFailureHandler;
import com.kitcd.share_delivery_api.security.handler.JsonAuthSuccessHandler;
import com.kitcd.share_delivery_api.security.handler.CustomLoginAuthEntryPoint;
import com.kitcd.share_delivery_api.security.provider.JWTAuthenticationProvider;
import com.kitcd.share_delivery_api.security.provider.JsonAuthenticationProvider;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.security.SecureRandom;


@Configuration
@EnableWebSecurity
@AllArgsConstructor
@Slf4j
public class SecurityConfig extends WebSecurityConfigurerAdapter {


    private JWTAuthenticationProvider jwtAuthenticationProvider;
    @Bean
    public AuthenticationSuccessHandler authenticationSuccessHandler(){
        return new JsonAuthSuccessHandler();
    }

    @Bean
    public AuthenticationFailureHandler authenticationFailureHandler(){
        return new JsonAuthFailureHandler();
    }

    @Bean
    public AuthenticationEntryPoint jsonLoginAuthEntryPoint() {
        return new CustomLoginAuthEntryPoint();
    }

    @Bean
    public AccessDeniedHandler jsonAccessDeniedHandler() {
        return new CustomAccessDeniedHandler();
    }

    @Bean
    public AuthenticationProvider jsonAuthenticationProvider(){
        return new JsonAuthenticationProvider();
    }

    @Bean
    public JsonLoginProcessingFilter jsonLoginProcessingFilter() throws Exception {
        JsonLoginProcessingFilter jsonLoginProcessingFilter = new JsonLoginProcessingFilter();
        jsonLoginProcessingFilter.setAuthenticationManager(authenticationManagerBean());

        // Auth Success/Fail 핸들러 설정
        jsonLoginProcessingFilter.setAuthenticationSuccessHandler(authenticationSuccessHandler());
        jsonLoginProcessingFilter.setAuthenticationFailureHandler(authenticationFailureHandler());

        return jsonLoginProcessingFilter;
    }

    @Bean
    public JWTAuthenticationFilter jwtAuthenticationFilter() throws Exception {
        return new JWTAuthenticationFilter(authenticationManagerBean());
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Bean
    public SecureRandom secureRandom(){
        return new SecureRandom();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth){
        auth.authenticationProvider(jsonAuthenticationProvider());
        auth.authenticationProvider(jwtAuthenticationProvider);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http
                .formLogin().disable()
                .httpBasic().disable()
        ;

        http
                .antMatcher("/api/**") // `api/` 이하 URL에 한해서 설정 클래스 동작
                .authorizeRequests()
                .antMatchers("/api/auth/**").permitAll()
                .anyRequest().authenticated()
                .and()
                .addFilterAt(jsonLoginProcessingFilter(), UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(jwtAuthenticationFilter(), JsonLoginProcessingFilter.class)
        ;


        /* 미인증, 인가예외 핸들러 등록 */
        http
                .exceptionHandling()
                .accessDeniedHandler(jsonAccessDeniedHandler())
                .authenticationEntryPoint(jsonLoginAuthEntryPoint())
        ;


        http.cors().and().csrf().disable(); // Enable CORS and disable CSRF //TODO: 왜 CORS는 활성화 하라는 것 ?
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS); // Set session management to stateless

    }

//    @Bean
//    public CorsFilter corsFilter() {
//        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//        CorsConfiguration config = new CorsConfiguration();
//        config.setAllowCredentials(true);
//        config.addAllowedOrigin("*");
//        config.addAllowedHeader("*");
//        config.addAllowedMethod("*");
//        source.registerCorsConfiguration("/**", config);
//        return new CorsFilter(source);
//    }


}
