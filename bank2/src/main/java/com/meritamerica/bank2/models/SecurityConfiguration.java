package com.meritamerica.bank2.models;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.config.BeanIds;
import com.meritamerica.bank2.JWT.JwtAuthenticationEntryPoint;
import com.meritamerica.bank2.JWT.JwtAuthenticationFilter;



@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(
        securedEnabled = true,
        jsr250Enabled = true,
        prePostEnabled = true
)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
	
	@Autowired
	private MyUserDetailsService myUserDetailsService;
	
	@Autowired
    private JwtAuthenticationEntryPoint unauthorizedHandler;
	
	@Autowired
     JwtAuthenticationFilter jwtAuthenticationFilter;
       
    
	
	@Bean
    public PasswordEncoder passwordEncoder() {
        //return new BCryptPasswordEncoder();
		return NoOpPasswordEncoder.getInstance();
    }
     
	@Bean(BeanIds.AUTHENTICATION_MANAGER)
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
 
    @Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(myUserDetailsService)
		.passwordEncoder(passwordEncoder());
	}
    
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors().and().csrf().disable()
        .exceptionHandling()
        .authenticationEntryPoint(unauthorizedHandler)
        .and()
        .sessionManagement()
		.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        .and()
        .authorizeRequests()
        .antMatchers("/authenticate").permitAll()
            .antMatchers("/authenticate/createUser").hasAuthority("Administrator")
            .antMatchers("/AccountHolders/**").hasAuthority("Administrator")
            .antMatchers("/Me/**").hasAuthority("AccountHolder")
            .antMatchers(HttpMethod.GET ,"/CDOfferings").hasAnyAuthority("AccountHolder","Administrator")
            .antMatchers(HttpMethod.POST ,"/CDOfferings").hasAuthority("Administrator")
            .anyRequest().authenticated();
     http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
    }
//    @Override
//  protected void configure(HttpSecurity http) throws Exception {
//      
//      http.authorizeRequests()
//      .antMatchers("/authenticate").permitAll()
//          .antMatchers("/authenticate/createUser").hasAuthority("Administrator")
//          .antMatchers("/AccountHolders/**").hasAuthority("Administrator")
//          .antMatchers("/Me/**").hasAuthority("AccountHolder")
//          .antMatchers(HttpMethod.GET ,"/CDOfferings").hasAnyAuthority("AccountHolder","Administrator")
//          .antMatchers(HttpMethod.POST ,"/CDOfferings").hasAuthority("Administrator")
//          .anyRequest().authenticated()
//          .and()
//          .sessionManagement()
//    		.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
//      http.csrf().disable();
//      http.headers().frameOptions().disable();
//   http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
//   }
	
}
