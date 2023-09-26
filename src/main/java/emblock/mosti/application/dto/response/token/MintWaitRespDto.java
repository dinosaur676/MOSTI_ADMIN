package emblock.mosti.application.dto.response.token;

import emblock.mosti.application.domain.MintWait;

public record MintWaitRespDto(
        String userName,
        long tokenId
) {
    public static MintWaitRespDto 생성(MintWait mintWait) {
        return new MintWaitRespDto(
                mintWait.getUserName(),
                mintWait.getTokenId()
        );
    }
}
