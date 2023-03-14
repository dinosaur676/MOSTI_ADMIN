package emblock.mosti.application.domain.builder;

import java.sql.Timestamp;

abstract class BaseFindBuilder<C> {
    private Timestamp createdOn;
    private Timestamp updatedOn;

    public C 날짜조회(Timestamp createdOn, Timestamp updatedOn){
        this.createdOn = createdOn;
        this.updatedOn = updatedOn;
        return self();
    }

    public Timestamp getCreatedOn() {
        return createdOn;
    }

    public Timestamp getUpdatedOn() {
        return updatedOn;
    }

    protected abstract C self();
    public abstract <T> T build();
}
