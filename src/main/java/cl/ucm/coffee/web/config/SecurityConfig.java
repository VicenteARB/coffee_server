package cl.ucm.coffee.web.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

@Configuration
@EnableMethodSecurity(securedEnabled = true)
public class SecurityConfig {
    @Autowired
    private JwtFilter jwtFilter;

    //@Autowired
    //public SecurityConfig(JwtFilter jwtFilter) {
    //    this.jwtFilter = jwtFilter;
   // }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .cors().and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
                .authorizeHttpRequests()
                //Auth
                .requestMatchers(HttpMethod.POST,"/api/auth/login").permitAll()
                .requestMatchers(HttpMethod.POST,"/api/auth/register").permitAll()
                .requestMatchers(HttpMethod.GET,"/api/auth/list").hasAnyRole("ADMIN")
                .requestMatchers(HttpMethod.POST,"/api/auth/update/{username}").hasAnyRole("ADMIN")
                .requestMatchers(HttpMethod.GET,"/api/auth/get/{username}").hasAnyRole("ADMIN")
                //Coffee
                .requestMatchers(HttpMethod.GET,"/api/coffee/list").hasAnyRole("ADMIN", "CUSTOMER")
                .requestMatchers(HttpMethod.POST,"/api/coffee/save").hasAnyRole("ADMIN")
                .requestMatchers(HttpMethod.GET,"/api/coffee/findByName").hasAnyRole("ADMIN")
                .requestMatchers(HttpMethod.POST, "/api/coffee/update/{id}").hasAnyRole("ADMIN")
                .requestMatchers(HttpMethod.POST,"/api/coffee/delete/{id}").hasAnyRole("ADMIN")
                //Testimonials
                .requestMatchers(HttpMethod.POST,"/api/testimonials/create").hasAnyRole("ADMIN", "CUSTOMER")
                .requestMatchers(HttpMethod.GET,"/api/testimonials/findByCoffeeId/{coffeeId}").hasAnyRole("ADMIN", "CUSTOMER")
                .anyRequest()
                .authenticated()
                .and()
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
