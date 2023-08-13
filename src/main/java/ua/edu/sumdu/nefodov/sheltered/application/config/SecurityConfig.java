package ua.edu.sumdu.nefodov.sheltered.application.config;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private UserDetailsService userService;
    private final BCryptPasswordEncoder passwordEncoder;

    private final Logger LOG = LogManager.getLogger(SecurityConfig.class);

    @Autowired
    public SecurityConfig(UserDetailsService userService, BCryptPasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .mvcMatchers("/shelter/home").permitAll()
                .mvcMatchers("/shelter").permitAll()
                .mvcMatchers("/shelter/**").authenticated()
                .mvcMatchers("/user/**").permitAll()
                .mvcMatchers("/api/**").permitAll();

        http.authenticationProvider(authProvider());
        http.formLogin()
                .defaultSuccessUrl("/shelter/home", true)
                .loginPage("/user/login")
                .loginProcessingUrl("/user/perform-login")
                .usernameParameter("username")
                .passwordParameter("password")
                .permitAll();
        http.logout().logoutUrl("/user/logout").permitAll();
        http.httpBasic();
        return http.csrf().disable().build();
    }

    @Bean
    public DaoAuthenticationProvider authProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(passwordEncoder);
        provider.setUserDetailsService(userService);
        return provider;
    }

}
