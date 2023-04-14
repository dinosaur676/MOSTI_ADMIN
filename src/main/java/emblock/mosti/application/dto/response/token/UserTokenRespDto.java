package emblock.mosti.application.dto.response.token;

import emblock.mosti.application.domain.TokenInfo;
import emblock.mosti.application.domain.UserToken;

import java.time.LocalDateTime;

public record UserTokenRespDto (
        String address,
        long tokenId,
        String metaData,
        String tokenOwnerName, //여기는 조인으로 이름 넣기
        String type, //여기는 조인으로 type이름 넣기
        char contractType,
        LocalDateTime createdOn,
        LocalDateTime deletedOn
){
    public static UserTokenRespDto 실행(UserToken userToken) {
        TokenInfo tokenInfo = userToken.getTokenInfo();
        return new UserTokenRespDto(
                userToken.getAddress(),
                tokenInfo.getTokenId(),
                tokenInfo.getMetaData(),
                tokenInfo.getTokenOwner(),
                tokenInfo.getType(),
                tokenInfo.getContractType(),
                tokenInfo.getCreatedOn(),
                userToken.getDeletedOn()
        );
    }
}
