package emblock.mosti.application.dto.request.token;

public record MintWaitReqDto(

        String address,
        String userName,
        long tokenId

) {}
