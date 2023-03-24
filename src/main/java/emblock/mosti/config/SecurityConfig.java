package emblock.mosti.config;

import emblock.mosti.application.security.AuthUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity
@EnableMethodSecurity
@Configuration
public class SecurityConfig {
    private static final Logger log =  LoggerFactory.getLogger(SecurityConfig.class);
    private static final String DEFAULT_PAGE= "/page/user";
    private static final String LOGIN_PAGE= "/page/login";
    private static final String ISSUE_PAGE= "/page/token-validation";

    private final UserDetailsService userDetailsService;

    public SecurityConfig(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }
/*    @Bean
    public AuthenticationProvider authenticationProvider(){
        return new EmbAuthenticationProvider(passwordEncoder(), userDetailsService);
    }*/

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        //간단하게 사용자 조회하고, 암호만 체크하는 경우 사용
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService);
        provider.setPasswordEncoder(passwordEncoder());

        return provider;
    }

//    @Bean
//    public AuthenticationProvider authenticationProvider() {
//        // 사용자 조회와 암호 체크 외에 별도의 추가작업을 필요로 하는 경우에 사용
//        return new SsoqAuthenticationProvider(passwordEncoder(), userDetailsService);
//    }


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        //http.headers().frameOptions().sameOrigin(); // 동일 도메인에서 iframe 접근 가능
        http
            .authorizeRequests()
            // 페이지 권한 설정
            
            .antMatchers("/api/admin-users/**").permitAll()
            .antMatchers("/assets/**", "/models/**", "/views/**").permitAll()
            .antMatchers(ISSUE_PAGE, LOGIN_PAGE, "/page/home").permitAll()
            .anyRequest().authenticated()
        .and() // 로그인 설정
        .formLogin()
            .loginPage(LOGIN_PAGE)
            .loginProcessingUrl(LOGIN_PAGE)
            .usernameParameter("loginId")
            .failureUrl(LOGIN_PAGE + "?error=true")
            //.defaultSuccessUrl(DEFAULT_PAGE, true)
            .successHandler((request, response, authentication) -> {
                ((AuthUser)authentication.getPrincipal()).패스워드지우기();
              response.sendRedirect(
                      authentication.getAuthorities().contains(new SimpleGrantedAuthority("ADMIN"))? DEFAULT_PAGE: "/page/user");
            })
//            .failureHandler(authenticationFailureHandler())
            .permitAll()
        .and() // 로그아웃 설정
        .logout()
            .logoutUrl("/page/logout")
            .logoutSuccessUrl("/page/login?logout=true")
            .invalidateHttpSession(true)
            .deleteCookies("JSESSIONID")
            .permitAll()
        .and()
        .authenticationProvider(authenticationProvider())
        .exceptionHandling();
//            .exceptionHandling().accessDeniedHandler(accessDeniedHandler());


        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

//    @Bean
//    public WebSecurityCustomizer webSecurityCustomizer() {
//        return (web) -> web.debug(false)
//                .ignoring()
//                .antMatchers("/assets/**", "/favicon.ico");
//    }



}

