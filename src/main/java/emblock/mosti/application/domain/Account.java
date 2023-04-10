package emblock.mosti.application.domain;

import emblock.framework.domain.SnowflakeIdGenerator;

import java.time.LocalDateTime;

public class Account {
    private long user_id;
    private String privateKey;
    private String address;
    private LocalDateTime createdOn;

    public void setUser_id(long user_id) {
        this.user_id = user_id;
    }

    public void setCreatedOn(LocalDateTime createdOn) {
        this.createdOn = createdOn;
    }

    public long getUser_id() {
        return user_id;
    }

    public String getPrivateKey() {
        return privateKey;
    }

    public String getAddress() {
        return address;
    }

    public LocalDateTime getCreatedOn() {
        return createdOn;
    }

    public Account(long user_id, String privateKey, String address, LocalDateTime createdOn) {
        this.user_id = user_id;
        this.privateKey = privateKey;
        this.address = address;
        this.createdOn = createdOn;
    }

    public Account(long user_id, String privateKey, String address) {
        this.user_id = user_id;
        this.privateKey = privateKey;
        this.address = address;
        this.createdOn = LocalDateTime.now();
    }

    public Account(String privateKey, String address)
    {
        this.user_id = SnowflakeIdGenerator.genId();
        this.privateKey = privateKey;
        this.address = address;
        this.createdOn = LocalDateTime.now();
    }
}
