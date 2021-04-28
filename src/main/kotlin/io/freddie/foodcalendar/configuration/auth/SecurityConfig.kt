package io.freddie.foodcalendar.configuration.auth

import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter

@Configuration
@EnableWebSecurity
class SecurityConfig(
    private val customOAuth2UserService: CustomOAuth2UserService
) : WebSecurityConfigurerAdapter() {
    override fun configure(http: HttpSecurity) {
        http
            .csrf().disable()
            .formLogin().disable()
            .httpBasic().disable()
            .authorizeRequests()
            .antMatchers("/api/auth/check", "/login").permitAll()
            .antMatchers("/", "/api/**").authenticated()
            .anyRequest().permitAll()
            .and()
            .logout().logoutSuccessUrl("/login").permitAll()
            .and()
                .oauth2Login()
                .loginPage("/login")
                .redirectionEndpoint()
            .and()
            .userInfoEndpoint()
            .userService(customOAuth2UserService)
    }
}