package emblock.mosti.application.domain;

import java.time.LocalDateTime;

public class Log {
    private long userId;
    private String loginId;
    private String userName;
    private String label;
    private User.UserType userType;
    private User.UserStatus userStatus;
    private LocalDateTime createdOn;

    public Log(long userId, String loginId, String userName, String label, User.UserType userType, User.UserStatus userStatus, LocalDateTime createdOn) {
        this.userId = userId;
        this.loginId = loginId;
        this.userName = userName;
        this.label = label;
        this.userType = userType;
        this.userStatus = userStatus;
        this.createdOn = createdOn;
    }

    public Log(long userId, String loginId, String userName, String label) {
        this.userId = userId;
        this.loginId = loginId;
        this.userName = userName;
        this.label = label;
        this.createdOn = LocalDateTime.now();
    }

    public User.UserType getUserType() {
        return userType;
    }

    public User.UserStatus getUserStatus() {
        return userStatus;
    }

    public long getUserId() {
        return userId;
    }

    public String getLoginId() {
        return loginId;
    }

    public String getUserName() {
        return userName;
    }

    public String getLabel() {
        return label;
    }

    public LocalDateTime getCreatedOn() {
        return createdOn;
    }
}
