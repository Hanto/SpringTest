package com.myrran.springtest.web;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.sql.DataSource;

@Slf4j
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter
{
    @Autowired
    private DataSource dataSource;

    @Bean
    public PasswordEncoder passwordEncoder()
    {   return PasswordEncoderFactories.createDelegatingPasswordEncoder(); }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception
    {
        auth.jdbcAuthentication()
            .dataSource(dataSource)
            .passwordEncoder(passwordEncoder())
            .usersByUsernameQuery("select username, password, enabled from users where username=?")
            .authoritiesByUsernameQuery(
                "select u.username, a.authority " +
                "from Authorities as a, Users as u, User_roles as r " +
                "where u.username = ? " +
                "   and a.id = r.fk_rol " +
                "   and u.id = r.fk_user");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception
    {
        /*http.authorizeRequests()
            .antMatchers("/h2-console/**").permitAll()
            .antMatchers("/api/**").access("hasRole('ADMIN')")
            .anyRequest().authenticated()
            .and()
            .formLogin();

        http.csrf()
            .ignoringAntMatchers("/h2-console/**");
        http.headers()
            .frameOptions()
            .sameOrigin();*/

        http.formLogin();

        http.authorizeRequests()
            .antMatchers("/api/*").hasAnyAuthority("ADMIN")
            .antMatchers("/api/groups/").permitAll();
    }
}
