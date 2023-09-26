package emblock.mosti.application.dto.response.token;


import emblock.mosti.application.domain.TokenInfo;

public record TokenInfoRespDto (
        long tokenId,
        String metaData,
        String userName
){
    static public TokenInfoRespDto 생성(TokenInfo tokenInfo) {
        return new TokenInfoRespDto(
                tokenInfo.getTokenId(),
                tokenInfo.getMetaData(),
                tokenInfo.getUserName()
        );
    }
}
