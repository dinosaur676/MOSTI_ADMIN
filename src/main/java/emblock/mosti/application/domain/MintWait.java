package emblock.mosti.application.domain;

import java.time.LocalDateTime;

public class MintWait {
    String userName;
    long tokenId;
    boolean enable;
    LocalDateTime createdOn;

    public MintWait(String userName, long tokenId, boolean enable, LocalDateTime createdOn) {
        this.userName = userName;
        this.tokenId = tokenId;
        this.enable = enable;
        this.createdOn = createdOn;
    }

    public String getUserName() {
        return userName;
    }

    public long getTokenId() {
        return tokenId;
    }

    public boolean isEnable() {
        return enable;
    }

    public LocalDateTime getCreatedOn() {
        return createdOn;
    }
}
