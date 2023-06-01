package emblock.mosti.application.dto.response;

import emblock.mosti.application.security.AuthUser;
import emblock.mosti.application.security.jwt.JWTInfo;

public record UserLoginRespDto (
        String loginId,
        String grantType,
        String accessToken
){
    static public UserLoginRespDto 생성(AuthUser authUser, JWTInfo jwtInfo) {
        return new UserLoginRespDto(
                authUser.getLoginId(),
                jwtInfo.getGrantType(),
                jwtInfo.getAccessToken()
        );
    }
}
