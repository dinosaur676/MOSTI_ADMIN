package emblock.mosti.application.security.jwt;

import emblock.mosti.application.security.AuthUser;
import jakarta.servlet.*;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import reactor.netty.http.Cookies;

import java.io.IOException;

public class JWTFilter extends BasicAuthenticationFilter {

    public static final String HEADER_LABEL = "Authorization";
    public static final String REFRESH_COOKIE_LABEL = "RefreshToken";
    private final JWTProvider jwtProvider;

    public JWTFilter(AuthenticationManager authenticationManager, JWTProvider jwtProvider) {
        super(authenticationManager);
        this.jwtProvider = jwtProvider;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        String token = resolveToken(request);

        if (!StringUtils.hasText(token)) {
            Cookie[] cookies = request.getCookies();

            if(cookies.length != 0) {
                Cookie refreshCookie = null;

                for (Cookie cookie : cookies) {
                    if (cookie.getName().equals(REFRESH_COOKIE_LABEL)) {
                        refreshCookie = cookie;
                    }
                }

                if (refreshCookie != null) {
                    String refreshToken = refreshCookie.getValue();
                    if(jwtProvider.validateToken(refreshToken) == null) {
                        token = jwtProvider.refreshAccessToken(refreshToken);
                    }
                }
            }

        }

        if (StringUtils.hasText(token) && jwtProvider.validateToken(token) == null) {
            Authentication authentication = jwtProvider.getAuthentication(token);

            if (authentication != null) {
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }

        chain.doFilter(request, response);
    }

    private String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader(HEADER_LABEL);
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer")) {
            return bearerToken.substring(7);
        }

        return null;
    }
}
