
package com.gylgroup.conelalma.security;

import com.gylgroup.conelalma.services.UsuarioService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private BCryptPasswordEncoder encoder;

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(usuarioService).passwordEncoder(encoder);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.authorizeRequests()
                .antMatchers("/",
                        "/admin/**", "/usuario/registrarse", "/usuario/guardar",
                        "/css/**", "/img/**", "/js/*", "index", "/public/**")
                .permitAll().antMatchers("/**").permitAll().and().formLogin().loginPage("/login")
                .loginProcessingUrl("/logincheck").usernameParameter("email").passwordParameter("contrasenia")
                .defaultSuccessUrl("/admin", true).failureUrl("/login?error=true").permitAll().and().logout()
                .logoutUrl("/logout").logoutSuccessUrl("/login?logout=true").permitAll()
                .deleteCookies("JSESSIONIO").and().csrf().disable();

        // http.csrf().disable().authorizeRequests().anyRequest().permitAll();
    }

}
