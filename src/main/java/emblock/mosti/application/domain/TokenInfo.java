package emblock.mosti.application.domain;

import java.time.LocalDateTime;

public class TokenInfo {

    long tokenId;
    String metaData;
    String userName;
    LocalDateTime createdOn;

    public TokenInfo(long tokenId, String metaData, String userName, LocalDateTime createdOn) {
        this.tokenId = tokenId;
        this.metaData = metaData;
        this.userName = userName;
        this.createdOn = createdOn;
    }

    public TokenInfo(long tokenId, String metaData, String userName) {
        this.tokenId = tokenId;
        this.metaData = metaData;
        this.userName = userName;
        this.createdOn = LocalDateTime.now();
    }

    public long getTokenId() {
        return tokenId;
    }

    public String getMetaData() {
        return metaData;
    }

    public String getUserName() {
        return userName;
    }

    public LocalDateTime getCreatedOn() {
        return createdOn;
    }
}
