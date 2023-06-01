package emblock.mosti.application;

// import emblock.mosti.adapter.ramda.NftGateway;

import emblock.framework.helper.Do;
import emblock.mosti.adapter.ramda.dto.response.RamdaMapResponseDto;
import emblock.mosti.application.dto.request.user.UserCreateReqDto;
import emblock.mosti.application.dto.request.user.UserUpdateReqDto;
import emblock.mosti.application.dto.response.UserRespDto;
import emblock.mosti.application.port.in.IUserService;
import emblock.mosti.application.port.out.IUserRepository;
import emblock.mosti.application.domain.User;
import emblock.mosti.application.domain.factory.UserFactory;
import emblock.mosti.application.security.jwt.JWTInfo;
import emblock.mosti.application.security.jwt.JWTProvider;
import io.netty.util.internal.StringUtil;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Service
public class UserService implements IUserService {
    private final IUserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final ApplicationEventPublisher eventPublisher;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private JWTProvider jwtProvider;


    // public UserService(IUserRepository userRepository, PasswordEncoder passwordEncoder, ApplicationEventPublisher eventPublisher, NftGateway nftGateway) {
    public UserService(IUserRepository userRepository, PasswordEncoder passwordEncoder,
                       ApplicationEventPublisher eventPublisher, AuthenticationManagerBuilder authenticationManagerBuilder,
                       JWTProvider jwtProvider) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.eventPublisher = eventPublisher;
        this.authenticationManagerBuilder = authenticationManagerBuilder;
        this.jwtProvider = jwtProvider;
    }

    @Override
    public JWTInfo 로그인(Authentication authentication) {
        return jwtProvider.generateToken(authentication);
    }

    @Override
    public JWTInfo 로그인(String loginId, String password) {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(loginId, password);
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        JWTInfo jwtInfo = jwtProvider.generateToken(authentication);

        return jwtInfo;
    }

    @Override
    public List<UserRespDto> 목록조회(String school) {
        List<UserRespDto> output = null;
        if (school.equals(""))
            output = this.userRepository.목록조회().stream().map(UserRespDto::생성).collect(Collectors.toList());
        else
            output = this.userRepository.목록조회(school).stream().map(UserRespDto::생성).collect(Collectors.toList());

        return output;
    }

    @Override
    public UserRespDto 조회(String loginId) {
        User user = this.userRepository.조회(loginId);
        if (Do.비었음(user))
            return null;

        return UserRespDto.생성(user);
    }

    @Override
    public UserRespDto 조회(long userId) {
        User user = this.userRepository.조회(userId);
        if (Do.비었음(user))
            return null;

        return UserRespDto.생성(user);
    }

    @Override
    public User 추가(UserCreateReqDto userCreateReqDto) {
        User user = UserFactory.일반사용자생성(userCreateReqDto);
        user.암호화패스워드설정(this.passwordEncoder.encode(user.getPassword()));
        this.userRepository.추가(user);
        this.지갑정보생성및수정(user.getUserId());

        return user;
    }

    @Override
    public void 수정(UserUpdateReqDto userUpdateReqDto) {
        User user = UserFactory.수정사용자생성(userUpdateReqDto);
        if (!StringUtil.isNullOrEmpty(user.getPassword()))
            user.암호화패스워드설정(this.passwordEncoder.encode(user.getPassword()));
        this.userRepository.수정(user);
    }

    @Override
    public void 삭제(long userId) {
        this.userRepository.삭제(userId);
    }

    @Override
    public RamdaMapResponseDto 지갑정보생성및수정(long userId) {
        Map<String, String> param = new ConcurrentHashMap<>();
        param.put("userId", String.valueOf(userId));
        // RamdaMapResponseDto respDto =
        //         this.nftGateway.requestWithPostWebClient(NftGateway.API.CREATE_WALLET, RamdaMapResponseDto.class,param);
        // this.userRepository.지갑정보수정(respDto.getData().get("walletId"),String.valueOf(respDto.getData().get("address")),userId);
        // return respDto;
        return null;
    }

}
