package com.myrran.springtest.model.appusers;

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
public class SecurityConfig extends WebSecurityConfigurerAdapter
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
            .usersByUsernameQuery("select username, password, enabled from appusers where username = ?")
            .authoritiesByUsernameQuery(
                "select u.username, a.authority " +
                "from Authorities as a, Appusers as u, appusers_to_appRoles as r " +
                "where u.username = ? " +
                "   and a.id = r.approlid " +
                "   and u.id = r.appUserid");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception
    {
        http.formLogin();

        http.authorizeRequests()
            .antMatchers("/api/*").hasAnyAuthority("ADMIN")
            .antMatchers("/api/groups/").permitAll();
    }
}
