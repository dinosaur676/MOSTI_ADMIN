package emblock.mosti.application.security;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;
import org.springframework.web.servlet.HandlerInterceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@Component
public class MenuRoleCheckInterceptor implements HandlerInterceptor {
    private static final Logger log = LoggerFactory.getLogger(MenuRoleCheckInterceptor.class);
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if(!this.doMenuAuthCheckOn(request)){
            response.sendError(HttpStatus.FORBIDDEN.value());
            return false;
        }
        return true;
    }

    private boolean doMenuAuthCheckOn(HttpServletRequest request){
        HttpSession session = request.getSession(false);
        if (null == session) return false;
        Authentication authentication =  SecurityContextHolder.getContext().getAuthentication();
        if(null == authentication ) return false;
        AuthUser authUser = (AuthUser) authentication.getPrincipal();
        String path = request.getRequestURI();
        PathMatcher pathMatcher = new AntPathMatcher();
        return authUser.getMenuRoleMappingList().stream().filter( el -> pathMatcher.match(el.getMenu().getPath(),path))
                .map(el -> true).findFirst().orElse(false);
    }

}
