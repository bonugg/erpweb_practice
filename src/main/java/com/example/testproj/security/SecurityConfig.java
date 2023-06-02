package com.example.testproj.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    /* 로그인 실패 핸들러 의존성 주입 */
    private final AuthenticationFailureHandler customFailureHandler;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                //csrf 제외
                .csrf().ignoringAntMatchers("/CnoCheck", "/production/monthPlan")
                .and()
                .headers().frameOptions().disable()
                .and()
                .authorizeRequests()
                .antMatchers("/images/**").permitAll()
                .antMatchers("/css/**").permitAll()
                .antMatchers("/js/**").permitAll()
                .antMatchers("/" ,"/sginin", "/sginin_member", "/login", "/CnoCheck", "/production/monthPlan").permitAll()
                .antMatchers("/managerAcess","/vacationAcessView","/meetingAcessView", "businessAcessView", "/Access", "/Cancle").hasAuthority("ROLE_MANAGER")
                .anyRequest().authenticated();

        http
                .formLogin()
                .loginPage("/")    // GET 요청
                .permitAll()
                .loginProcessingUrl("/auth")    // POST 요청
                .usernameParameter("CNO")
                .passwordParameter("PWD")
                .failureHandler(customFailureHandler) // 로그인 실패 핸들러
                .defaultSuccessUrl("/");

        http
                .logout()
                .logoutUrl("/logout")
                .logoutSuccessUrl("/")
                .deleteCookies("JSESSIONID")
                .invalidateHttpSession(true);
        http
                .exceptionHandling()
                .accessDeniedPage("/");
        return http.build();
    }
}