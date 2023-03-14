package emblock.mosti.application.port.in;

import emblock.mosti.adapter.ramda.dto.response.RamdaMapResponseDto;
import emblock.mosti.application.dto.request.user.UserCreateReqDto;
import emblock.mosti.application.dto.request.user.UserUpdateReqDto;
import emblock.mosti.application.dto.response.UserRespDto;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface IUserService {

    List<UserRespDto> 목록조회();
    UserRespDto 조회(String loginId);
    @Transactional
    void 추가(UserCreateReqDto userCreateReqDto);
    @Transactional
    void 수정(UserUpdateReqDto userUpdateReqDto);
    RamdaMapResponseDto 지갑정보생성및수정(long userId);

}
