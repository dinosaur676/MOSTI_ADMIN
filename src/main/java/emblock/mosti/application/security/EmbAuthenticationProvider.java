package emblock.mosti.application.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

public class EmbAuthenticationProvider implements AuthenticationProvider {
    private static Logger log = LoggerFactory.getLogger(EmbAuthenticationProvider.class);

    private final PasswordEncoder passwordEncoder;
    private final UserDetailsService authUserService;

    public EmbAuthenticationProvider(PasswordEncoder passwordEncoder, UserDetailsService authUserService) {
        this.passwordEncoder = passwordEncoder;
        this.authUserService = authUserService;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        if (authentication == null || authentication.getCredentials() == null)
            throw new BadCredentialsException("Invalid authentication");

        log.debug("<<--------- Authentication Info ------------>>");
        log.debug("name: " + authentication.getName());
        log.debug("password: " + authentication.getCredentials().toString());
        log.debug(authentication.toString());

        String username = "guest";
        Object principal = authentication.getPrincipal();
        if (principal instanceof UserDetails) {
            username = ((UserDetails)principal).getUsername();
        } else {
            username = principal.toString();
        }
        String providedPassword = authentication.getCredentials().toString();

        Object details = authentication.getDetails();
        log.debug("authentication.getDetails(): " + details.getClass());
        try {
            UserDetails authUser = this.authUserService.loadUserByUsername(username);
            
            if (authUser == null) {
                throw new BadCredentialsException("Invalid User");
            }

            if (!passwordEncoder.matches(providedPassword, authUser.getPassword())) {
                throw new BadCredentialsException("Invalid User and Password");
            } else if (!authUser.isEnabled()) {
                throw new DisabledException("Disabled User");
            } else if (!authUser.isAccountNonExpired()) {
                throw new AccountExpiredException("Expired User");
            } else if (!authUser.isAccountNonLocked()) {
                throw new LockedException("Locked User");
            } else if (!authUser.isCredentialsNonExpired()) {
                throw new CredentialsExpiredException("Credential User");
            }

//            LoginInfo loginInfo = new LoginInfo(userInfo, userCompanyList);
//            loginInfo.setLogOn(LocalDateTime.now().toString());  //로그온 시간 저장

            return new UsernamePasswordAuthenticationToken(authUser, null, authUser.getAuthorities());

        } catch (UsernameNotFoundException ex) {
            throw new BadCredentialsException("Invalid User: " + ex.getMessage());
        }
    }

    @Override
    public boolean supports(Class authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}

