package emblock.mosti.application.domain;

import java.time.LocalDateTime;

public class UserToken {
    private String address;
    TokenInfo tokenInfo;
    private LocalDateTime deletedOn;

    public UserToken(String address, TokenInfo tokenInfo, LocalDateTime deletedOn) {
        this.address = address;
        this.tokenInfo = new TokenInfo(
                tokenInfo.getTokenId(),
                tokenInfo.getType(),
                tokenInfo.getMetaData(),
                tokenInfo.getTokenOwner(),
                tokenInfo.getContractType(),
                LocalDateTime.now()
        );
        this.deletedOn = deletedOn;
    }

    public String getAddress() {
        return address;
    }

    public TokenInfo getTokenInfo() {
        return tokenInfo;
    }

    public LocalDateTime getDeletedOn() {
        return deletedOn;
    }
}
