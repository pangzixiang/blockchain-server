package com.scutgroup3.blockchainserver.Login;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.bind.annotation.CrossOrigin;

@Configurable
@EnableWebSecurity
@CrossOrigin
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        AuthenticationManager manager = super.authenticationManagerBean();
        return manager;
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Autowired(required = false)
    private LoginService loginService;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(loginService);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors().and().csrf().disable()
                .authorizeRequests()
                .antMatchers("/css/**","/fonts/**","/images/**","/js/**","/login/**","/APIDoc").permitAll()
                .antMatchers("/InitRule","/getDiscountRuleID","/QueryParticipation","/QueryState","/Open","/api3","/api4","/api5","/api6","/api7","/api12","/api15").hasRole("sellerMSP")
                .antMatchers("/InitGroup","/getInitGroupBuyingID","/Participate","/getParticipate","/api8","/api9","/api10","/api12","/api15").hasRole("buyerMSP")
                .antMatchers("/InitCredit","/ChangeCredit","/InitTrans","/getTransID","/ChangeTrans","/QueryTrans","/api12","/api13","/api14","/api15","/api16","/api17","/api18","/api19").hasRole("platformMSP")
                .anyRequest().authenticated()
                .and()
                .formLogin(form -> form
                                .loginPage("/login").successForwardUrl("/")
                                .permitAll()
//                    .successHandler(customAuthenticationSuccessHandler)
                ).httpBasic().and().logout().logoutSuccessUrl("/");
        http.headers().frameOptions().disable();
    }



}
