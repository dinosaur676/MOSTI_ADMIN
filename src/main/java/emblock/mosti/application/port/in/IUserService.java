package emblock.mosti.application.port.in;

import emblock.mosti.adapter.ramda.dto.response.RamdaMapResponseDto;
import emblock.mosti.application.domain.User;
import emblock.mosti.application.dto.request.user.UserCreateReqDto;
import emblock.mosti.application.dto.request.user.UserUpdateReqDto;
import emblock.mosti.application.dto.response.UserRespDto;
import emblock.mosti.application.security.jwt.JWTInfo;
import org.springframework.security.core.Authentication;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface IUserService {

    JWTInfo 로그인(Authentication authentication);
    JWTInfo 로그인(String loginId, String password);
    List<UserRespDto> 목록조회(String school);
    UserRespDto 조회(String loginId);
    UserRespDto 조회(long userId);
    @Transactional
    User 추가(UserCreateReqDto userCreateReqDto);
    @Transactional
    void 수정(UserUpdateReqDto userUpdateReqDto);

    void 삭제(long userId);
    RamdaMapResponseDto 지갑정보생성및수정(long userId);

}
