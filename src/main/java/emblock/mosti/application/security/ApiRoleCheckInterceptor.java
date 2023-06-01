package emblock.mosti.application.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import emblock.mosti.application.domain.ApiRoleMapping;
import emblock.framework.dto.FailRespDto;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;
import org.springframework.web.servlet.HandlerInterceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.List;

@Component
public class ApiRoleCheckInterceptor implements HandlerInterceptor {
    private final static Logger logger = LoggerFactory.getLogger(ApiRoleCheckInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if(!this.doApiAuthCheckOn(request)) {
            ObjectMapper mapper = new ObjectMapper();
            response.setStatus(HttpStatus.FORBIDDEN.value());
            response.setContentType("application/json;charset=utf-8");
            response.getWriter().write(mapper.writeValueAsString(new FailRespDto("403", "권한이 존재하지 않습니다.","해당 권한이 존재하지 않습니다.")));
            return false;
        }
        return  true;
    }
    private boolean doApiAuthCheckOn(HttpServletRequest request){
        String method = request.getMethod();
        String path = request.getRequestURI();

        PathMatcher pathMatcher = new AntPathMatcher();
        HttpSession session = request.getSession(false);
        if (null == session) return false;
        //AuthUser authUser = (AuthUser)SecurityContextHolder.getContext().getAuthentication().getAuthorities();

        return ((List<ApiRoleMapping>)session.getAttribute("apiAuthorityList")).stream()
                .filter(el ->  method.toUpperCase().equals(el.getApi().getMethod()) && pathMatcher.match(el.getApi().getPath(),path))
                .map(el -> true).findFirst().orElse(false);

    }


    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
    }
}
