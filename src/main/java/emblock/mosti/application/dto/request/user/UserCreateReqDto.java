package emblock.mosti.application.dto.request.user;

import emblock.mosti.application.domain.User;
import emblock.framework.validate.EnumVal;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;

public record UserCreateReqDto(
        @NotEmpty(message = "로그인 아이디는 필수값입니다.")
        String loginId,
        @NotEmpty(message = "비밀번호는 필수값입니다.")
        String password,
        @NotEmpty(message = "사용자명은 필수값입니다.")
        String userName,
        @Email(message = "이메일 형식을 입력해주세요.") @NotEmpty(message = "이메일은 필수값입니다.")
        String email,
        String address,
        String phone,
        String cellPhone,

        String school,
        @EnumVal(message = "적절하지 않은 유형입니다." , enumClass = User.UserType.class)
        User.UserType type,
        @EnumVal(message = "적절하지 않은 사용여부 값입니다." , enumClass = User.UserStatus.class)
        User.UserStatus status
) {}
