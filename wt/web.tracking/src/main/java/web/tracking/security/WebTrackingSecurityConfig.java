package web.tracking.security;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import web.tracking.security.userdetails.WebTrackingUserDetailService;

@EnableWebSecurity
public class WebTrackingSecurityConfig extends WebSecurityConfigurerAdapter {
  @Bean
  public UserDetailsService mongoUserDetails() {
    return new WebTrackingUserDetailService();
  }
  
  @Override
  protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    UserDetailsService userDetailsService = mongoUserDetails();
    auth.userDetailsService(userDetailsService);
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http.authorizeRequests().mvcMatchers("/admin/*").access("hasRole('ROLE_ADMIN')").mvcMatchers("/user/*")
    .access("hasRole('ROLE_USER')").mvcMatchers("/sa/*").access("hasRole('ROLE_SA')")
    .mvcMatchers("/company/").access("hasRole('ROLE_ADMIN') OR hasRole('ROLE_SA')").and().formLogin()
    .loginPage("/login").defaultSuccessUrl("/default").permitAll().and().logout().permitAll();
    http.csrf().disable();
  }

}
