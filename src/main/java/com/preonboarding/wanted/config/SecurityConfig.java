package com.preonboarding.wanted.config;

import com.preonboarding.wanted.exception.CustomAccessDeniedHandler;
import com.preonboarding.wanted.exception.CustomAuthenticationEntryPoint;
import com.preonboarding.wanted.security.JwtAuthenticationFilter;
import com.preonboarding.wanted.security.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter  {

    private final JwtTokenProvider jwtTokenProvider;

    @Bean
    public PasswordEncoder getPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/h2-console/**");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http
            .httpBasic().disable() // rest api이므로 기본설정 미사용
            .csrf().disable() // rest api이므로 csrf 보안 미사용
            .formLogin().disable()
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS); // jwt로 인증하므로 세션 미사용

        //URL 인증여부 설정.
        http
            .authorizeRequests()
            .antMatchers("/api/v1/users/signUp/**").permitAll()
            .antMatchers("/api/v1/users/login/**").permitAll()
            .antMatchers("/exception/**").permitAll()
            .anyRequest().authenticated();

        //JwtFilter 추가
        http
            .addFilterBefore(new JwtAuthenticationFilter(jwtTokenProvider), UsernamePasswordAuthenticationFilter.class);
        //JwtAuthentication exception handling
        http
            .exceptionHandling().authenticationEntryPoint(new CustomAuthenticationEntryPoint());


        //access Denial handler
        http
            .exceptionHandling().accessDeniedHandler(new CustomAccessDeniedHandler());

    }

}