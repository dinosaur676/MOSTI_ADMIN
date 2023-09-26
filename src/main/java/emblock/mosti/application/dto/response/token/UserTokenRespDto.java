package emblock.mosti.application.dto.response.token;

import emblock.mosti.application.domain.UserToken;

public record UserTokenRespDto(
        long tokenId,
        String metaData
) {
    public static UserTokenRespDto 생성(UserToken userToken) {
        return new UserTokenRespDto(
                userToken.getTokenId(),
                userToken.getMetaData()
        );
    }
}
