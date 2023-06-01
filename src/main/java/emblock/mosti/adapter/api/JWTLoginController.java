package emblock.mosti.adapter.api;

import emblock.framework.dto.ResponseDto;
import emblock.framework.dto.SuccessRespDto;
import emblock.framework.exception.DomainException;
import emblock.framework.helper.Do;
import emblock.mosti.application.domain.TokenInfo;
import emblock.mosti.application.domain.User;
import emblock.mosti.application.dto.request.user.UserLoginDto;
import emblock.mosti.application.port.in.IUserService;
import emblock.mosti.application.port.out.IUserRepository;
import emblock.mosti.application.security.jwt.JWTInfo;
import emblock.mosti.application.security.jwt.JWTProvider;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RequestMapping("/page")
@RestController
public class JWTLoginController {

    private final IUserService userService;
    private final JWTProvider jwtProvider;

    public JWTLoginController(IUserService userService, JWTProvider jwtProvider) {
        this.userService = userService;
        this.jwtProvider = jwtProvider;
    }

//    @PostMapping("/login")
//    public ResponseDto login(@RequestBody UserLoginDto user) {
//        JWTInfo jwtInfo = userService.로그인(user.loginId(), user.password());
//        return new SuccessRespDto(jwtInfo.toJson(), "asdf");
//    }
}
