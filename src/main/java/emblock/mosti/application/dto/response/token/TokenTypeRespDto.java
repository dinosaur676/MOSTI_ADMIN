package emblock.mosti.application.dto.response.token;

import emblock.mosti.application.domain.TokenType;

public record TokenTypeRespDto(
        long tokenType,
        String description
) {
    public static TokenTypeRespDto 생성(TokenType tokenType) {
        return new TokenTypeRespDto(tokenType.getType(), tokenType.getDescription());
    }
}
