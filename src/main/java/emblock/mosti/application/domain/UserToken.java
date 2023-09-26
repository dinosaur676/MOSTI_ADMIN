package emblock.mosti.application.domain;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class UserToken {
    long tokenId;
    long id;

    String metaData;

    String walletName;
    String walletTag;
    String userName;


    LocalDateTime createdOn;
    LocalDateTime deletedOn;

    public UserToken(long tokenId, long id, String metaData, String walletName, String walletTag, String userName, LocalDateTime createdOn, LocalDateTime deletedOn) {
        this.tokenId = tokenId;
        this.id = id;
        this.metaData = metaData;
        this.walletName = walletName;
        this.walletTag = walletTag;
        this.userName = userName;
        this.createdOn = createdOn;
        this.deletedOn = deletedOn;
    }

    public UserToken(long tokenId, String walletName, String walletTag, String userName, LocalDateTime createdOn, LocalDateTime deletedOn) {
        this.tokenId = tokenId;
        this.walletName = walletName;
        this.walletTag = walletTag;
        this.userName = userName;
        this.createdOn = createdOn;
        this.deletedOn = deletedOn;
    }

    public UserToken(String walletName, String walletTag, String userName) {
        this.walletName = walletName;
        this.walletTag = walletTag;
        this.userName = userName;
    }

    public long getTokenId() {
        return tokenId;
    }

    public long getId() {
        return id;
    }

    public String getMetaData() {
        return metaData;
    }

    public String getWalletName() {
        return walletName;
    }

    public String getWalletTag() {
        return walletTag;
    }

    public String getUserName() {
        return userName;
    }

    public LocalDateTime getCreatedOn() {
        return createdOn;
    }

    public LocalDateTime getDeletedOn() {
        return deletedOn;
    }
}
