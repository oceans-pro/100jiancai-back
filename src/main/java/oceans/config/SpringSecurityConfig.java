package oceans.config;

import oceans.service.auth.impl.MySimpleUrlAuthenticationFailureHandler;
import oceans.service.auth.impl.MyUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * Spring-Security框架配置
 */
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {
    /**
     * 配置用户认证信息
     * 1. 使用我们自己的UserService查询用户信息、权限信息
     * 2. 如果用户量很大可以使用 redis
     */
    @Autowired
    private MyUserDetailsService userDetailsService;

    /**
     * 配置加密方式
     */
    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * 配置认证提供者，整合前面的配置
     * 1. setHideUserNotFoundExceptions防止UsernameNotfound被吞掉
     */
    @Bean
    public AuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsService);
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        authenticationProvider.setHideUserNotFoundExceptions(false);
        return authenticationProvider;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) {
        auth.authenticationProvider(daoAuthenticationProvider());
    }


    private static final String[] AUTH_WHITELIST = {
            // -- third party
            "/swagger-resources/**",
            "/swagger-ui.html",
            "/v2/api-docs",
            "/druid/**",
            // -- authentication
            "/webjars/**",
            "login/**",
            "/logout/**",
            "/public/**",
            // -- 访客记录
            "/visitor/**"
    };

    /**
     * 配置
     * 1. security路径
     * 2. cors、csrf
     *
     * @see oceans.controller.client.GetController
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http.cors();
        http.formLogin()
                .loginPage("/login/need_login").permitAll() // get method custom
                .usernameParameter("username").passwordParameter("password")
                .loginProcessingUrl("/login/process") // post (by security)
                // .failureUrl("/login/error") // get method custom
                .failureHandler(new MySimpleUrlAuthenticationFailureHandler())
                .defaultSuccessUrl("/login/success"); // get method custom

        http.logout()
                .logoutUrl("/logout/process") // post (by security)
                .logoutSuccessUrl("/logout/success"); // get method custom

        http.rememberMe()
                .rememberMeParameter("remember"); // todo

        http.authorizeRequests()
                .antMatchers(AUTH_WHITELIST).permitAll()

                .regexMatchers(HttpMethod.valueOf("GET"), "^/?info.*").hasAuthority("info:get")
                .regexMatchers(HttpMethod.valueOf("DELETE"), "^/?info.*").hasAuthority("info:mod")
                .regexMatchers(HttpMethod.valueOf("PUT"), "^/?info.*").hasAuthority("info:mod")
                .regexMatchers(HttpMethod.valueOf("POST"), "^/?info.*").hasAuthority("info:mod")

                .regexMatchers(HttpMethod.valueOf("GET"), "^/?hire.*").hasAuthority("hire:get")
                .regexMatchers(HttpMethod.valueOf("DELETE"), "^/?hire.*").hasAuthority("hire:mod")
                .regexMatchers(HttpMethod.valueOf("POST"), "^/?hire.*").hasAuthority("hire:mod")
                .regexMatchers(HttpMethod.valueOf("PUT"), "^/?hire.*").hasAuthority("hire:mod")

                .regexMatchers(HttpMethod.valueOf("GET"), "^/?news.*").hasAuthority("news:get")
                .regexMatchers(HttpMethod.valueOf("DELETE"), "^/?news.*").hasAuthority("news:mod")
                .regexMatchers(HttpMethod.valueOf("POST"), "^/?news.*").hasAuthority("news:mod")
                .regexMatchers(HttpMethod.valueOf("PUT"), "^/?news.*").hasAuthority("news:mod")

                .regexMatchers(HttpMethod.valueOf("GET"), "^/?product.*").hasAuthority("product:get")
                .regexMatchers(HttpMethod.valueOf("DELETE"), "^/?product.*").hasAuthority("product:mod")
                .regexMatchers(HttpMethod.valueOf("POST"), "^/?product.*").hasAuthority("product:mod")
                .regexMatchers(HttpMethod.valueOf("PUT"), "^/?product.*").hasAuthority("product:mod")

                .regexMatchers(HttpMethod.valueOf("GET"), "^/?tag.*").hasAnyAuthority("tag:get", "tag:mod")
                .regexMatchers(HttpMethod.valueOf("DELETE"), "^/?tag.*").hasAuthority("tag:mod")
                .regexMatchers(HttpMethod.valueOf("POST"), "^/?tag.*").hasAuthority("tag:mod")

                .anyRequest().authenticated();
    }
}
