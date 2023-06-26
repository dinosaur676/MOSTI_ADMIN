package emblock.mosti.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import emblock.mosti.application.security.CustomOidcUserService;
import emblock.mosti.application.security.KeycloakLogoutHandler;
import emblock.mosti.application.security.OAuth2UserService;
import emblock.mosti.application.security.Oauth2AuthenticationFailureHandler;
import emblock.mosti.application.security.Oauth2AuthenticationSuccessHandler;
import emblock.mosti.application.security.Oauth2LogoutSuccessHandler;
import emblock.mosti.application.security.jwt.JWTFilter;
import emblock.mosti.application.security.jwt.JWTProvider;
import emblock.mosti.application.security.jwt.filter.UserLoginFailureCustomHandler;
import emblock.mosti.application.security.jwt.filter.UserLoginSuccessCustomHandler;
import emblock.mosti.application.security.jwt.filter.UsernamePasswordAuthenticationCustomFilter;
import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import org.keycloak.OAuth2Constants;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
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
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.authority.mapping.GrantedAuthoritiesMapper;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.oidc.OidcIdToken;
import org.springframework.security.oauth2.core.oidc.OidcUserInfo;
import org.springframework.security.oauth2.core.oidc.user.OidcUserAuthority;
import org.springframework.security.oauth2.core.user.OAuth2UserAuthority;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.security.web.authentication.session.RegisterSessionAuthenticationStrategy;
import org.springframework.security.web.authentication.session.SessionAuthenticationStrategy;

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

    private final KeycloakLogoutHandler keycloakLogoutHandler;
    //private final CustomJwtAuthenticationConverter customJwtAuthenticationConverter;
    private final OAuth2UserService oAuth2UserService;
    private final CustomOidcUserService customOidcUserService;


    public SecurityConfig(UserDetailsService userDetailsService, JWTProvider jwtProvider,
        UserLoginSuccessCustomHandler successHandler, UserLoginFailureCustomHandler failureHandler,
        ObjectMapper objectMapper, PasswordEncoder passwordEncoder,
        KeycloakLogoutHandler keycloakLogoutHandler,
        //CustomJwtAuthenticationConverter customJwtAuthenticationConverter,
        OAuth2UserService oAuth2UserService,
        CustomOidcUserService customOidcUserService
    ) {
        this.userDetailsService = userDetailsService;
        this.jwtProvider = jwtProvider;
        this.successHandler = successHandler;
        this.failureHandler = failureHandler;
        this.objectMapper = objectMapper;
        this.passwordEncoder = passwordEncoder;
        this.keycloakLogoutHandler = keycloakLogoutHandler;
        //this.customJwtAuthenticationConverter = customJwtAuthenticationConverter;
        this.oAuth2UserService = oAuth2UserService;
        this.customOidcUserService = customOidcUserService;
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
    public AuthenticationFailureHandler oauth2AuthenticationFailureHandler() {
        return new Oauth2AuthenticationFailureHandler();
    }

    @Bean
    public AuthenticationSuccessHandler oauth2AuthenticationSuccessHandler() {
        return new Oauth2AuthenticationSuccessHandler();
    }

    @Bean
    public LogoutSuccessHandler oauthLogoutHandler() {
        return new Oauth2LogoutSuccessHandler();
    }


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        //http.headers().frameOptions().sameOrigin(); // 동일 도메인에서 iframe 접근 가능
        http.authorizeHttpRequests()
            .requestMatchers("/api/admin-users/**", "/api/third/**").permitAll()
            .requestMatchers("**/oauth2/**", "/models/**", "/views/**", "/assets/**").permitAll()
            .requestMatchers(VALID_API, LOGIN_PAGE, "/page/home").permitAll()
            .requestMatchers("/favicon.ico").permitAll()
            .requestMatchers("/api/**").hasRole("admin")
            .anyRequest().authenticated();
        http.oauth2Login(o -> o
                .userInfoEndpoint(userInfoEndpointConfig -> {
                    //userInfoEndpointConfig.userService(oAuth2UserService);
                    //userInfoEndpointConfig.oidcUserService(customOidcUserService);
                    //userInfoEndpointConfig.userAuthoritiesMapper(userAuthoritiesMapper());
                })
                .loginPage("/oauth2/authorization/keycloak")
                .defaultSuccessUrl("/page/user")
                .successHandler(oauth2AuthenticationSuccessHandler())
                .failureHandler(oauth2AuthenticationFailureHandler())
            )
            .logout(o ->
                o.logoutUrl("/oauth2/logout")
                 .addLogoutHandler(keycloakLogoutHandler)
                 .logoutSuccessHandler(oauthLogoutHandler())
                 .deleteCookies()
                 .clearAuthentication(true)
                 .invalidateHttpSession(true));
/*        http.oauth2ResourceServer()
            .jwt()
            .jwtAuthenticationConverter(customJwtAuthenticationConverter);*/
        return http.build();
    }

    private GrantedAuthoritiesMapper userAuthoritiesMapper() {
        return (authorities) -> {
            Set<GrantedAuthority> mappedAuthorities = new HashSet<>();

            authorities.forEach(authority -> {
                if (OidcUserAuthority.class.isInstance(authority)) {
                    OidcUserAuthority oidcUserAuthority = (OidcUserAuthority) authority;

                    OidcIdToken idToken = oidcUserAuthority.getIdToken();
                    OidcUserInfo userInfo = oidcUserAuthority.getUserInfo();

                    // Map the claims found in idToken and/or userInfo
                    // to one or more GrantedAuthority's and add it to mappedAuthorities
                    if (userInfo.hasClaim("realm_access")) {
                        var realmAccess = userInfo.getClaimAsMap("realm_access");
                        var roles = (Collection<String>) realmAccess.get("roles");
                        mappedAuthorities.addAll(generateAuthoritiesFromClaim(roles));
                    }

                } else if (OAuth2UserAuthority.class.isInstance(authority)) {
                    OAuth2UserAuthority oauth2UserAuthority = (OAuth2UserAuthority) authority;

                    Map<String, Object> userAttributes = oauth2UserAuthority.getAttributes();

                    // Map the attributes found in userAttributes
                    // to one or more GrantedAuthority's and add it to mappedAuthorities

                }
            });

            return mappedAuthorities;
        };
    }

    Collection<GrantedAuthority> generateAuthoritiesFromClaim(Collection<String> roles) {
        return roles.stream()
                    .map(role -> new SimpleGrantedAuthority("SCOPE_" + role))
                    .collect(Collectors.toList());
    }

    @Bean
    protected SessionAuthenticationStrategy sessionAuthenticationStrategy() {
        return new RegisterSessionAuthenticationStrategy(new SessionRegistryImpl());
    }

/*    public class MyCustomDsl extends AbstractHttpConfigurer<MyCustomDsl, HttpSecurity> {

        @Override
        public void configure(HttpSecurity http) {
            AuthenticationManager authenticationManager = http.getSharedObject(
                AuthenticationManager.class); //스프링 시큐리티 필터내에 cors 관련 필터가 있음!! 그래서 제공해주는 필터 객체를 생성후 HttpSecurity에 등록!

            http.addFilterBefore(
                new UsernamePasswordAuthenticationCustomFilter(authenticationManager, objectMapper,
                    successHandler, failureHandler),
                UsernamePasswordAuthenticationFilter.class);
            http.addFilter(new JWTFilter(authenticationManager, jwtProvider));

        }
    }*/
    @Bean
    Keycloak keycloak() {
        return KeycloakBuilder.builder()
                              .serverUrl("http://172.30.1.157:8443")
                              .realm("master")
                              .clientId("admin-cli")
                              .grantType(OAuth2Constants.PASSWORD)
                              .username("mostidevadmin")
                              .password("1")
                              .build();
    }

//    @Bean
//    public WebSecurityCustomizer webSecurityCustomizer() {
//        return (web) -> web.debug(false)
//                .ignoring()
//                .antMatchers("/assets/**", "/favicon.ico");
//    }


}

