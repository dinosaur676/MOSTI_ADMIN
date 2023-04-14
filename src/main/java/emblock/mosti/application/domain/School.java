package emblock.mosti.application.domain;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class School {
    private long userId;
    private String schoolName;
    private long schoolTokenId;

    LocalDateTime createdOn;

    public String getSchoolName() {
        return schoolName;
    }

    public long getSchoolTokenId() {
        return schoolTokenId;
    }

    public LocalDateTime getCreatedOn() {
        return createdOn;
    }

    public long getUserId() {
        return userId;
    }

    public School(long userId, String schoolName, long schoolTokenId) {
        this.userId = userId;
        this.schoolName = schoolName;
        this.schoolTokenId = schoolTokenId;
        this.createdOn = LocalDateTime.now();
    }
}
