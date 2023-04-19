package shop.mtcoding.thereblog.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Slf4j
@Configuration
public class SecurityConfig {

    @Bean
    BCryptPasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
        // 1. CSRF 해제
        http.csrf().disable();

        // 2. Form 로그인 설정
        http.formLogin()
                .loginPage("/loginForm")
                .loginProcessingUrl("/login")
                .successHandler((request, response, authentication) -> {
                    log.debug("디버그 : 로그인 성공");
                })
                .failureHandler((request, response, exception) -> {
                    log.debug("디버그 : 로그인 실패 : "+exception.getMessage());
                });

        // 3. 인증, 권한 필터 설정
        http.authorizeRequests(
                authorize -> authorize.antMatchers("/s/**").authenticated()
                        .anyRequest().permitAll()
        );

        return http.build();
    }
}
