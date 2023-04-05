package emblock.mosti.application.domain;

import java.time.LocalDateTime;

public class TokenInfo {

    private long tokenId;
    private long type;
    private String metaData;
    private String tokenOwner;
    private LocalDateTime createdOn;

    public TokenInfo(long tokenId, long type, String metaData, String tokenOwner) {
        this.tokenId = tokenId;
        this.type = type;
        this.metaData = metaData;
        this.tokenOwner = tokenOwner;
        this.createdOn = LocalDateTime.now();
    }
}
