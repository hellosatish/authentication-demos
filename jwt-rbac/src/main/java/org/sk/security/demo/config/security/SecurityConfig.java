package org.sk.security.demo.config.security;


import static org.sk.security.demo.constants.RoleConstants.ROLE_ADMIN;
import static org.sk.security.demo.constants.RoleConstants.ROLE_STORE_MANAGER;
import static org.sk.security.demo.constants.RoleConstants.ROLE_STORE_USER;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
/**
 * @author satish sharma
 *
 * Configure SpringSecurity to use our logic for Authorization.
 * Also confifure the API accessible to roles
 * 
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class SecurityConfig  extends WebSecurityConfigurerAdapter {

    @Autowired
    TokenService jwtTokenProvider;

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    // Configure resources to be accessible to all  H2 console and swagger ui
    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring()
            .antMatchers(HttpMethod.OPTIONS, "/**")
            .antMatchers("/bower_components/**")
            .antMatchers("/swagger-ui.html")
            .antMatchers("/swagger-resources/**")
            .antMatchers("/v2/api-docs")
            .antMatchers("/webjars/**")
            .antMatchers("/")
            .antMatchers("/db-console/**.css")
            .antMatchers("/db-console/**")
            ;
    }
    
    /**
     * Configure security based on roles
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .httpBasic().disable()
            .csrf().disable()
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and()
                .authorizeRequests()
                .antMatchers("/authorize").permitAll()	//allowed to all
                .antMatchers("/product/**").hasRole(ROLE_STORE_MANAGER)	//allowed only to MANAGER
                .antMatchers("/mgmt/**").hasAnyRole(ROLE_ADMIN)	//allowed only to ADMIN
                .antMatchers("/user/**").hasAnyRole(ROLE_STORE_USER) //allowed only to USER
                .anyRequest().authenticated()	// Rest of the request must be authenticated
            .and()
            .apply(new JwtConfigurer(jwtTokenProvider)) //user this to configure filters
            ;
    }
}