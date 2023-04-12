package emblock.mosti.application.dto.response.token;

import emblock.mosti.application.domain.TokenInfo;

import java.time.LocalDateTime;

public record TokenInfoRespDto (
    long tokenId,
    String metaData,
    String tokenOwnerName, //여기는 조인으로 이름 넣기
    String type, //여기는 조인으로 type이름 넣기
    char contractType,
    LocalDateTime createdOn
) {
    public static TokenInfoRespDto 생성(TokenInfo tokenInfo) {
        return new TokenInfoRespDto(
                tokenInfo.getTokenId(),
                tokenInfo.getMetaData(),
                tokenInfo.getTokenOwner(),
                tokenInfo.getType(),
                tokenInfo.getContractType(),
                tokenInfo.getCreatedOn()
        );
    }
}
