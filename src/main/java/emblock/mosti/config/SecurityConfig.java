package emblock.mosti.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import emblock.mosti.application.domain.User;
import emblock.mosti.application.security.AuthUser;
import emblock.mosti.application.security.jwt.JWTFilter;
import emblock.mosti.application.security.jwt.JWTProvider;
import emblock.mosti.application.security.jwt.filter.UserLoginFailureCustomHandler;
import emblock.mosti.application.security.jwt.filter.UserLoginSuccessCustomHandler;
import emblock.mosti.application.security.jwt.filter.UsernamePasswordAuthenticationCustomFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity
@EnableMethodSecurity
@Configuration
public class SecurityConfig {
    private static final Logger log = LoggerFactory.getLogger(SecurityConfig.class);
    private static final String USER_PAGE = "/page/user";
    private static final String SCHOOL_PAGE = "/page/student";
    private static final String CERTIFIED_PAGE = "/page/certified";
    private static final String LOGIN_PAGE = "/page/login";
    private static final String VALID_API = "/api/issue/valid/**";

    private final UserDetailsService userDetailsService;
    private final JWTProvider jwtProvider;
    private final UserLoginSuccessCustomHandler successHandler;
    private final UserLoginFailureCustomHandler failureHandler;
    private final ObjectMapper objectMapper;

    private final PasswordEncoder passwordEncoder;

    public SecurityConfig(UserDetailsService userDetailsService, JWTProvider jwtProvider,
                          UserLoginSuccessCustomHandler successHandler, UserLoginFailureCustomHandler failureHandler,
                          ObjectMapper objectMapper, PasswordEncoder passwordEncoder) {
        this.userDetailsService = userDetailsService;
        this.jwtProvider = jwtProvider;
        this.successHandler = successHandler;
        this.failureHandler = failureHandler;
        this.objectMapper = objectMapper;
        this.passwordEncoder = passwordEncoder;
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
        provider.setPasswordEncoder(passwordEncoder);

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
        http.authorizeHttpRequests()
                .requestMatchers("/api/admin-users/**", "/api/third/**").permitAll()
                .requestMatchers("/assets/**", "/models/**", "/views/**").permitAll()
                .requestMatchers(VALID_API, LOGIN_PAGE, "/page/home").permitAll()
                .requestMatchers("/favicon.ico").permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin()// 로그인 설정
                .loginPage(LOGIN_PAGE)
                .loginProcessingUrl(LOGIN_PAGE)
                .usernameParameter("loginId")
                .failureUrl(LOGIN_PAGE + "?error=true")
                //.defaultSuccessUrl(DEFAULT_PAGE, true)
                .successHandler((request, response, authentication) -> {
                    ((AuthUser) authentication.getPrincipal()).패스워드지우기();
                    response.sendRedirect(
                            authentication.getAuthorities().contains(new SimpleGrantedAuthority(User.UserType.A.getCodeName())) ? USER_PAGE :
                                    authentication.getAuthorities().contains(new SimpleGrantedAuthority(User.UserType.B.getCodeName())) ? SCHOOL_PAGE : CERTIFIED_PAGE);
                })
                //.failureHandler(authenticationFailureHandler())
                .permitAll()
                .and() // 로그아웃 설정
                .logout()
                .logoutUrl("/page/logout")
                .logoutSuccessUrl("/page/login?logout=true")
                .invalidateHttpSession(true)
                .deleteCookies("JSESSIONID")
                .permitAll()
                .and()
                //.authenticationProvider(authenticationProvider())
                .exceptionHandling();
//            .exceptionHandling().accessDeniedHandler(accessDeniedHandler());


        return http.build();
    }

    public class MyCustomDsl extends AbstractHttpConfigurer<MyCustomDsl, HttpSecurity> {

        @Override
        public void configure(HttpSecurity http) {
            AuthenticationManager authenticationManager = http.getSharedObject(AuthenticationManager.class); //스프링 시큐리티 필터내에 cors 관련 필터가 있음!! 그래서 제공해주는 필터 객체를 생성후 HttpSecurity에 등록!

            http.addFilterBefore(new UsernamePasswordAuthenticationCustomFilter(authenticationManager, objectMapper, successHandler, failureHandler),
                    UsernamePasswordAuthenticationFilter.class);
            http.addFilter(new JWTFilter(authenticationManager, jwtProvider));

        }
    }


//    @Bean
//    public WebSecurityCustomizer webSecurityCustomizer() {
//        return (web) -> web.debug(false)
//                .ignoring()
//                .antMatchers("/assets/**", "/favicon.ico");
//    }


}

