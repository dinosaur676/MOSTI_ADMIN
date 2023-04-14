package emblock.mosti.application.domain;

import java.time.LocalDateTime;

public class TokenInfo {

    private long tokenId;
    private String type;
    private String metaData;
    private String tokenOwner;
    private char contractType;
    private LocalDateTime createdOn;

    public TokenInfo(long tokenId, String type, String metaData, String tokenOwner, char contractType) {
        this.tokenId = tokenId;
        this.type = type;
        this.metaData = metaData;
        this.tokenOwner = tokenOwner;
        this.contractType = contractType;
        this.createdOn = LocalDateTime.now();
    }

    public TokenInfo(long tokenId, String type, String metaData, String tokenOwner, char contractType, LocalDateTime createdOn) {
        this.tokenId = tokenId;
        this.type = type;
        this.metaData = metaData;
        this.tokenOwner = tokenOwner;
        this.contractType = contractType;
        this.createdOn = createdOn;
    }

    public long getTokenId() {
        return tokenId;
    }

    public String getType() {
        return type;
    }

    public String getMetaData() {
        return metaData;
    }

    public String getTokenOwner() {
        return tokenOwner;
    }

    public char getContractType() {
        return contractType;
    }

    public LocalDateTime getCreatedOn() {
        return createdOn;
    }

    public void createdCurrentTime() {
        createdOn = LocalDateTime.now();
    }
}
