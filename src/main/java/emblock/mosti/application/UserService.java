package emblock.mosti.application;

import emblock.mosti.adapter.ramda.NftGateway;
import emblock.mosti.adapter.ramda.dto.response.RamdaMapResponseDto;
import emblock.mosti.application.dto.request.user.UserCreateReqDto;
import emblock.mosti.application.dto.request.user.UserUpdateReqDto;
import emblock.mosti.application.dto.response.UserRespDto;
import emblock.mosti.application.port.in.IUserService;
import emblock.mosti.application.port.out.IUserRepository;
import emblock.mosti.application.domain.User;
import emblock.mosti.application.domain.factory.UserFactory;
import io.netty.util.internal.StringUtil;
import org.springframework.context.ApplicationEventPublisher;
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
    private final NftGateway nftGateway;


    public UserService(IUserRepository userRepository, PasswordEncoder passwordEncoder, ApplicationEventPublisher eventPublisher, NftGateway nftGateway) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.eventPublisher = eventPublisher;
        this.nftGateway = nftGateway;
    }

    @Override
    public List<UserRespDto> 목록조회() {
        return this.userRepository.목록조회().stream().map(UserRespDto::생성).collect(Collectors.toList());
    }

    @Override
    public UserRespDto 조회(String loginId) {
        return UserRespDto.생성(this.userRepository.조회(loginId));
    }

    @Override
    public void 추가(UserCreateReqDto userCreateReqDto) {
        User user = UserFactory.일반사용자생성(userCreateReqDto);
        user.암호화패스워드설정(this.passwordEncoder.encode(user.getPassword()));
        this.userRepository.추가(user);
        this.지갑정보생성및수정(user.getUserId());
    }

    @Override
    public void 수정(UserUpdateReqDto userUpdateReqDto) {
        User user = UserFactory.수정사용자생성(userUpdateReqDto);
        if(!StringUtil.isNullOrEmpty(user.getPassword()))
            user.암호화패스워드설정(this.passwordEncoder.encode(user.getPassword()));
        this.userRepository.수정(user);
    }

    @Override
    public RamdaMapResponseDto 지갑정보생성및수정(long userId) {
        Map<String,String> param = new ConcurrentHashMap<>();
        param.put("userId",String.valueOf(userId));
        RamdaMapResponseDto respDto =
                this.nftGateway.requestWithPostWebClient(NftGateway.API.CREATE_WALLET, RamdaMapResponseDto.class,param);
        this.userRepository.지갑정보수정(respDto.getData().get("walletId"),String.valueOf(respDto.getData().get("address")),userId);
        return respDto;
    }

}
