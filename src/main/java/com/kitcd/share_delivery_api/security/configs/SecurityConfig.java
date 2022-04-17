package com.kitcd.share_delivery_api.security.configs;


import com.kitcd.share_delivery_api.security.filter.JsonLoginProcessingFilter;
import com.kitcd.share_delivery_api.security.handler.JsonAccessDeniedHandler;
import com.kitcd.share_delivery_api.security.handler.JsonAuthFailureHandler;
import com.kitcd.share_delivery_api.security.handler.JsonAuthSuccessHandler;
import com.kitcd.share_delivery_api.security.handler.JsonLoginAuthEntryPoint;
import com.kitcd.share_delivery_api.security.provider.JsonAuthProvider;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@AllArgsConstructor
@Slf4j
public class SecurityConfig extends WebSecurityConfigurerAdapter {

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
        return new JsonLoginAuthEntryPoint();
    }

    @Bean
    public AccessDeniedHandler jsonAccessDeniedHandler() {
        return new JsonAccessDeniedHandler();
    }

    @Bean
    public AuthenticationProvider jsonAuthProvider(){
        return new JsonAuthProvider();
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
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth){
        auth.authenticationProvider(jsonAuthProvider());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http
                .antMatcher("/api/**") // `api/` 이하 URL에 한해서 설정 클래스 동작
                .authorizeRequests()
                .antMatchers("/api/accounts").permitAll()
                .anyRequest().authenticated()
                .and()
                .addFilterAt(jsonLoginProcessingFilter(), UsernamePasswordAuthenticationFilter.class);


        /* 미인증, 인가예외 핸들러 등록 */
        http
                .exceptionHandling()
                .accessDeniedHandler(jsonAccessDeniedHandler())
                .authenticationEntryPoint(jsonLoginAuthEntryPoint())
        ;

        http.csrf().disable(); // 스프링 시큐리티 기본 보안 설정 상 post 요청 시에 csrf 토큰 요구하기에 비활성화 해주어야 한다.

    }



}
