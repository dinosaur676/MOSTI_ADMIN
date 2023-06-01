package emblock.mosti.application.security.jwt.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import emblock.framework.dto.SuccessRespDto;
import emblock.mosti.application.dto.response.UserLoginRespDto;
import emblock.mosti.application.port.in.ILogService;
import emblock.mosti.application.port.in.IUserService;
import emblock.mosti.application.security.AuthUser;
import emblock.mosti.application.security.jwt.JWTFilter;
import emblock.mosti.application.security.jwt.JWTInfo;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class UserLoginSuccessCustomHandler implements AuthenticationSuccessHandler {

    private final IUserService userService;
    private final ILogService logService;
    private final ObjectMapper objectMapper;

    public UserLoginSuccessCustomHandler(IUserService userService, ILogService logService, ObjectMapper objectMapper) {
        this.userService = userService;
        this.logService = logService;
        this.objectMapper = objectMapper;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {

        AuthUser authUser = ((AuthUser) authentication.getPrincipal());

        JWTInfo jwtInfo = userService.로그인(authentication);

        UserLoginRespDto userLoginRespDto = UserLoginRespDto.생성(authUser, jwtInfo);
        SuccessRespDto successRespDto = new SuccessRespDto(userLoginRespDto, "로그인을 성공하였습니다. 토큰이 발급되었습니다.");

        String res = objectMapper.writeValueAsString(successRespDto);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(res);

        Cookie cookie = new Cookie(JWTFilter.REFRESH_COOKIE_LABEL, jwtInfo.getRefreshToken());
        cookie.setHttpOnly(true);
        cookie.setPath("/");

        logService.추가(Long.parseLong(authUser.getUserId()), "Login");

        response.addCookie(cookie);

    }
}
