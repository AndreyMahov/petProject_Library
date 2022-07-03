package com.mahov.mvc.config;



import com.mahov.mvc.services.PersonDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;


//TODO new style of configuration https://clck.ru/rfszh
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final PersonDetailService personDetailService;

    @Autowired
    public SecurityConfig(PersonDetailService personDetailService) {
        this.personDetailService = personDetailService;
    }

  @Override
  protected void configure(HttpSecurity http) throws Exception {
      http.
              authorizeRequests()
              .antMatchers("/people/index","/people/new","/people/edit","/books/new","/books/edit")
              .hasRole("ADMIN")
              .antMatchers("/auth/login","/error","/auth/registration")
              .permitAll()
              .anyRequest().hasAnyRole("USER","ADMIN")
              .and()
              .formLogin().loginPage("/auth/login")
              .loginProcessingUrl("/process_login")
              .defaultSuccessUrl("/books",true)
              .failureUrl("/auth/login?error")
              .and().logout().logoutUrl("/logout").logoutSuccessUrl("/auth/login");
  }

   @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    auth.userDetailsService(personDetailService).passwordEncoder(getPasswordEncoder());
    }

    @Bean
    public PasswordEncoder getPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }

}
