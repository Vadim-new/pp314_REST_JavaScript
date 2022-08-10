package ru.kata.spring.boot_security.demo.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configurers.userdetails.DaoAuthenticationConfigurer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import ru.kata.spring.boot_security.demo.service.UserService;

@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    private final SuccessUserHandler successUserHandler;
    private final UserService userService;
    private final Encoder encoder;

    @Autowired
    public WebSecurityConfig(SuccessUserHandler successUserHandler, UserService userService, Encoder encoder) {
        this.successUserHandler = successUserHandler;
        this.userService = userService;
        this.encoder = encoder;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/admin/**").hasRole("ADMIN")
                .antMatchers("/user/**").hasAnyRole("ADMIN", "USER")
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .successHandler(successUserHandler)
                .permitAll()
                .and()
                .logout().logoutSuccessUrl("/login")
                .permitAll();
    }

    /*
    Этот метод Spring security создает аутентификацию в памяти.
    Вы можете назвать метод по своему вкусу. Единственные ограничения:
    аннотируйте метод с помощью @Autowired
    метод ДОЛЖЕН находиться в классе, аннотированном одним из следующих элементов: @ EnableWebSecurity, @ EnableWebMvcSecurity, @ EnableGlobalMethodSecurity или @ EnableGlobalAuthentication
    (и, конечно, метод имеет аргумент типа AuthenticationManagerBuilder)
    */
    @Autowired
    protected void configureGlobalSecurity(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
        DaoAuthenticationConfigurer<AuthenticationManagerBuilder, UserService> daoAuthenticationConfigurer = authenticationManagerBuilder.userDetailsService(userService);
        daoAuthenticationConfigurer.passwordEncoder(encoder.passwordEncoder());

    }
}