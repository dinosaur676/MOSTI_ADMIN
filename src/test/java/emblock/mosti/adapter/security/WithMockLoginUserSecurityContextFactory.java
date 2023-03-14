package emblock.mosti.adapter.security;

import emblock.mosti.application.security.AuthUser;
import emblock.mosti.application.security.AuthUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithSecurityContextFactory;

public class WithMockLoginUserSecurityContextFactory implements WithSecurityContextFactory<WithMockLoginInfo> {
    @Autowired
    private AuthUserService userService;
    @Override
    public SecurityContext createSecurityContext(WithMockLoginInfo customUser) {
        SecurityContext context = SecurityContextHolder.createEmptyContext();
        AuthUser user = (AuthUser)userService.loadUserByUsername(customUser.name());
        Authentication auth = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
        context.setAuthentication(auth);
        return context;
    }
}