package emblock.mosti.application.domain;

import java.sql.Timestamp;
import java.time.LocalDateTime;

public abstract class BaseDomain {
    private LocalDateTime createdOn;
    private LocalDateTime updatedOn;

    public LocalDateTime getCreatedOn() {
        return createdOn;
    }
    public LocalDateTime getUpdatedOn() {
        return updatedOn;
    }

    public void 현재일시설정(){
        this.createdOn = LocalDateTime.now();
        this.updatedOn = LocalDateTime.now();
    }

    public void 수정일시설정(){
        this.updatedOn = LocalDateTime.now();
    }

    public void 날짜조회(Timestamp createdOn, Timestamp updatedOn){
        this.createdOn = createdOn.toLocalDateTime();
        this.updatedOn = updatedOn.toLocalDateTime();
    }

}
