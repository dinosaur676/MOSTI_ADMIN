package emblock.mosti.application.security.jwt.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import emblock.framework.dto.FailRespDto;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
@Component
public class UserLoginFailureCustomHandler implements AuthenticationFailureHandler {
    private final ObjectMapper objectMapper;

    public UserLoginFailureCustomHandler(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(objectMapper.writeValueAsString(new FailRespDto("99", "로그인 실패")));
    }
}
