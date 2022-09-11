package it.uniroma3.siw.springcertificazioni.config;

import javax.sql.DataSource;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import it.uniroma3.siw.springcertificazioni.model.Ruolo;
import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@SuppressWarnings("deprecation")
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final DataSource datasource;

    /*
     * Permessi publici GET
     */
    private static final String[] PUBLIC_GET_PATH = {
            "/", "/index", "/images/**", "/css/**", "/js/**"
    };

    /* Permessi publici POST */
    private static final String[] PUBLIC_POST_PATHS = {
            "/login"
    };

    /*
     * Permessi amministratore GET
     */
    private static final String[] SEGRETERIA_GET_PATH = {
            "/segreteria/**"
    };

    private static final String[] SEGRETERIA_POST_PATH = {
            "/segreteria/**"
    };

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                /*
                 * Autorizzazioni
                 */
                .authorizeRequests()
                /* Permessi Publici */
                .antMatchers(HttpMethod.GET, PUBLIC_GET_PATH).permitAll()
                .antMatchers(HttpMethod.POST, PUBLIC_POST_PATHS).permitAll()
                /* Permessi da Amministratore */
                .antMatchers(HttpMethod.GET, SEGRETERIA_GET_PATH).hasAnyAuthority(Ruolo.SEGRETERIA.toString())
                .antMatchers(HttpMethod.POST, SEGRETERIA_POST_PATH).hasAnyAuthority(Ruolo.SEGRETERIA.toString())
                /* Path restanti agli autenticati */
                .anyRequest().authenticated()
                .and()
                /* login */
                .formLogin()
                .loginPage("/login")
                .defaultSuccessUrl("/default")
                .failureUrl("/index")
                .and()
                /*
                 * logout paragraph: qui definiamo il logout
                 */
                .logout()
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .logoutSuccessUrl("/index")
                .invalidateHttpSession(true)
                .clearAuthentication(true)
                .permitAll();
    }

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .jdbcAuthentication()
                .dataSource(this.datasource)
                .authoritiesByUsernameQuery("SELECT username, ruolo FROM credenziali WHERE username=?")
                .usersByUsernameQuery("SELECT username, password, 1 as enabled FROM credenziali WHERE username=?");
    }
}
