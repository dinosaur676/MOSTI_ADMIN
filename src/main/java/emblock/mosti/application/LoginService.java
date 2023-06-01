package emblock.mosti.application;

import emblock.mosti.application.port.in.ILoginService;
import emblock.mosti.application.port.out.IUserRepository;
import emblock.mosti.application.security.jwt.JWTInfo;
import emblock.mosti.application.security.jwt.JWTProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class LoginService implements ILoginService {
    private final IUserRepository userRepository;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final JWTProvider jwtProvider;

    public LoginService(IUserRepository userRepository, AuthenticationManagerBuilder authenticationManagerBuilder, JWTProvider jwtProvider) {
        this.userRepository = userRepository;
        this.authenticationManagerBuilder = authenticationManagerBuilder;
        this.jwtProvider = jwtProvider;
    }

    @Override
    public JWTInfo login(String memberId, String password) {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(memberId, password);
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);

        JWTInfo tokenInfo = jwtProvider.generateToken(authentication);

        return tokenInfo;
    }
}
