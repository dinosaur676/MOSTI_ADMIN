package emblock.mosti.application.domain;

import java.time.LocalDateTime;

public class UserToken {
    private String account;
    private long tokenId;
    private char contractType;
    private LocalDateTime createdOn;
    private LocalDateTime deletedOn;

    public UserToken(String account, long tokenId, char contractType) {
        this.account = account;
        this.tokenId = tokenId;
        this.contractType = contractType;
        this.createdOn = LocalDateTime.now();
    }

    public UserToken(String account, long tokenId, char contractType, LocalDateTime createdOn, LocalDateTime deletedOn) {
        this.account = account;
        this.tokenId = tokenId;
        this.contractType = contractType;
        this.createdOn = createdOn;
        this.deletedOn = deletedOn;
    }

    public UserToken(String account, long tokenId, char contractType, LocalDateTime deletedOn) {
        this.account = account;
        this.tokenId = tokenId;
        this.contractType = contractType;
        this.createdOn = LocalDateTime.now();
        this.deletedOn = deletedOn;
    }

    public String getAccount() {
        return account;
    }

    public long getTokenId() {
        return tokenId;
    }

    public char getContractType() {
        return contractType;
    }

    public LocalDateTime getCreatedOn() {
        return createdOn;
    }

    public LocalDateTime getDeletedOn() {
        return deletedOn;
    }
}
