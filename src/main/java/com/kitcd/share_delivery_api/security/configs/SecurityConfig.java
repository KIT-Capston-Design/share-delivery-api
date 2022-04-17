package com.kitcd.share_delivery_api.security.configs;


import com.kitcd.share_delivery_api.security.filter.JsonLoginProcessingFilter;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@AllArgsConstructor
@Slf4j
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Bean
    public JsonLoginProcessingFilter jsonLoginProcessingFilter() throws Exception {
        JsonLoginProcessingFilter jsonLoginProcessingFilter = new JsonLoginProcessingFilter();
        jsonLoginProcessingFilter.setAuthenticationManager(authenticationManagerBean());

        return jsonLoginProcessingFilter;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .antMatcher("/api/**") // `api/` 이하 URL에 한해서 설정 클래스 동작
                .authorizeRequests()
                .antMatchers("/api/messages").hasRole("MANAGER")
                .anyRequest().authenticated()
                .and()
                .addFilterAt(jsonLoginProcessingFilter(), UsernamePasswordAuthenticationFilter.class);

        http.csrf().disable(); // 스프링 시큐리티 기본 보안 설정 상 post 요청 시에 csrf 토큰 요구하기에 비활성화 해주어야 한다.

    }

}
