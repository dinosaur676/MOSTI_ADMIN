package emblock.mosti.application.domain.factory;

import emblock.mosti.application.domain.User;
import emblock.mosti.application.dto.request.user.UserCreateReqDto;
import emblock.mosti.application.dto.request.user.UserUpdateReqDto;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserFactory {
    public static User 일반사용자생성(UserCreateReqDto userCreateReqDto) {
        return User.Builder.builder등록(userCreateReqDto.loginId().toLowerCase(), userCreateReqDto.password())
                        .userName(userCreateReqDto.userName())
                .type( userCreateReqDto.type())
                .status(userCreateReqDto.status())
                .email(userCreateReqDto.email())
                .address(userCreateReqDto.address())
                .cellPhone(userCreateReqDto.cellPhone())
                .phone(userCreateReqDto.phone())
                .build();
    }

    public static User 수정사용자생성(UserUpdateReqDto reqDto){
        return User.Builder.builder수정(Long.valueOf(Long.parseLong(reqDto.userId())))
                .userId(Long.parseLong(reqDto.userId()))
                .loginId(reqDto.loginId().toLowerCase())
                .userName(reqDto.userName())
                .password(reqDto.password())
                .type(reqDto.type())
                .status(reqDto.status())
                .email(reqDto.email())
                .address(reqDto.address())
                .cellPhone(reqDto.cellPhone())
                .phone(reqDto.phone())
                .build();
    }

}
