package emblock.mosti.application.dto.request.user;

import emblock.mosti.application.domain.User;
import emblock.mosti.application.dto.response.UserRespDto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public record UserUpdateReqDto (
        @NotEmpty(message = "로그인 아이디는 필수값입니다.")
        String userId,
        @NotEmpty(message = "로그인 아이디는 필수값입니다.")
        String loginId,
        String password,
        @NotEmpty(message = "사용자명은 필수값입니다.")
        String userName,
        @Email(message = "이메일 형식을 입력해주세요.") @NotEmpty(message = "이메일은 필수값입니다.")
        String email,
        String address,
        String phone,
        String cellPhone,
        @NotNull(message = "유형은 필수값입니다.")
        User.UserType type,
        @NotNull(message = "상태는 필수값입니다.")
        User.UserStatus status
) {
        public static UserUpdateReqDto 이름수정(UserRespDto dto, String userName) {
                return new UserUpdateReqDto(
                        dto.userId(),
                        dto.loginId(),
                        "",
                        userName,
                        dto.email(),
                        dto.address(),
                        dto.phone(),
                        dto.cellPhone(),
                        dto.type(),
                        dto.status()
                );
        }
}
