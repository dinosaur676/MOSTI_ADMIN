package emblock.mosti.application.domain;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class School {
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

    public School(String schoolName, long schoolTokenId) {
        this.schoolName = schoolName;
        this.schoolTokenId = schoolTokenId;
        this.createdOn = LocalDateTime.now();
    }
}
