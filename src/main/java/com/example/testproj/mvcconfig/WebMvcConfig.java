package com.example.testproj.mvcconfig;

import com.example.testproj.handler.TeamLeaderInterceptor;
import com.example.testproj.repository.CalendarRepository;
import com.example.testproj.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@RequiredArgsConstructor
public class WebMvcConfig implements WebMvcConfigurer {
    private final UserRepository userRepository;


    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new TeamLeaderInterceptor(userRepository))
                .addPathPatterns("/**");
    }
}