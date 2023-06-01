package emblock.mosti.application.security.jwt;

import emblock.mosti.application.domain.MenuRoleMapping;
import emblock.mosti.application.domain.User;
import emblock.mosti.application.port.in.IMenuService;
import emblock.mosti.application.port.out.IUserRepository;
import emblock.mosti.application.security.AuthUser;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;


@Component
public class JWTProvider {

    private final Key key;
    private final IMenuService menuService;
    private final IUserRepository userRepository;

    private long tokenValidTime = 1000L * 60 * 30;

    JWTProvider(@Value("${jwt.secretKey}") final String secretKey, IMenuService menuService, IUserRepository userRepository) {
        key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(secretKey));
        this.menuService = menuService;
        this.userRepository = userRepository;
    }

    public JWTInfo generateToken(Authentication authentication) {
        String accessToken = createAccessToken(authentication, getAccessTokenExpiresIn());

        String refreshToken = createRefreshToken(((AuthUser) authentication.getPrincipal()).getLoginId(), getFreshTokenExpiresIn());

        return new JWTInfo("Bearer", accessToken, refreshToken);
    }

    public String createAccessToken(Authentication authentication, Date accessTokenExpiresIn) {
        String authorities = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));

        return createAccessToken(authentication.getName(), authorities, accessTokenExpiresIn);
    }

    public String createAccessToken(String loginId, String authorities, Date accessTokenExpiresIn) {
        return Jwts.builder()
                .setSubject(loginId)
                .claim("auth", authorities)
                .setExpiration(accessTokenExpiresIn)
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    public String createRefreshToken(String loginId, Date freshTokenExpiresIn) {

        return Jwts.builder()
                .setSubject(loginId)
                .setExpiration(freshTokenExpiresIn)
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    public Date getAccessTokenExpiresIn() {
        long now = (new Date()).getTime();
        return new Date(now + tokenValidTime);
    }

    public Date getFreshTokenExpiresIn() {
        long now = (new Date()).getTime();
        return new Date(now + ExpiredUtil.Day);
    }
    public Authentication getAuthentication(String accessToken) {
        // 토큰 복호화
        Claims claims = parseClaims(accessToken);

        if (claims.get("auth") == null) {
            throw new RuntimeException("권한 정보가 없는 토큰입니다.");
        }

        // 클레임에서 권한 정보 가져오기
        List<? extends GrantedAuthority> authorities =
                Arrays.stream(claims.get("auth").toString().split(","))
                        .map(SimpleGrantedAuthority::new)
                        .collect(Collectors.toList());

        User.UserType userType = null;

        for(int i = 0 ; i < authorities.size(); ++i) {
            String authority =  authorities.get(i).getAuthority();
            if(authority.equals("S") || authority.equals("A") || authority.equals("B")) {
                userType = User.UserType.fromValue(authority);
            }
        }

        List<MenuRoleMapping> menuRoleMappingList = this.menuService.사용자별메뉴조회(userType.getRoleId());

        // UserDetails 객체를 만들어서 Authentication 리턴
        AuthUser principal = new AuthUser(claims.getSubject(), "", menuRoleMappingList);
        return new UsernamePasswordAuthenticationToken(principal, "", authorities);
    }

    //null반환시 정상종료, String반환시 오류메시지
    public String validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return null;
        } catch (io.jsonwebtoken.security.SecurityException | MalformedJwtException e) {
            return "Invalid JWT Token";
        } catch (ExpiredJwtException e) {
            return "Expired JWT Token";
        } catch (UnsupportedJwtException e) {
            return "Unsupported JWT Token";
        } catch (IllegalArgumentException e) {
            return "JWT claims string is empty.";
        }
    }

    public String refreshAccessToken(String refreshToken) {
        if(validateToken(refreshToken) == null) {
            String loginId = parseClaims(refreshToken).getSubject();
            User user = userRepository.조회(loginId);
            User.UserType userType = user.getType();

            String authorities = userType.getCode() + "," + userType.getCodeName();


            return createAccessToken(loginId, authorities, getAccessTokenExpiresIn());
        }

        return null;
    }

    private Claims parseClaims(String accessToken) {
        try {
            return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(accessToken).getBody();
        } catch (ExpiredJwtException e) {
            return e.getClaims();
        }
    }


}
