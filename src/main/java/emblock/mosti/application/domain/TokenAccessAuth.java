package emblock.mosti.application.domain;

import java.sql.Timestamp;
import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.Assert;

import emblock.framework.domain.SnowflakeIdGenerator;
import emblock.mosti.application.domain.common.CommonEnum;

public class TokenAccessAuth {
    @Value("${expiration}")
    private int expiration;
    
    private long id;
    private String authKey;
    private long studentId;
    private LocalDateTime createdOn;
    private LocalDateTime expiredOn;
    
    public long getId(){
        return id;
    }

    public String getAuthKey(){
        return authKey;
    }

    public long getStudentId(){
        return studentId;
    }

    public LocalDateTime getCreatedOn(){
        return createdOn;
    }

    public LocalDateTime getExpiredOn(){
        return expiredOn;
    }

    private TokenAccessAuth(String authKey, long studentId){
        this.id = SnowflakeIdGenerator.genId();
        this.authKey = authKey;
        this.studentId = studentId;
        this.createdOn = LocalDateTime.now();
        this.expiredOn = LocalDateTime.now().plusMinutes(expiration);        
    }

    private TokenAccessAuth(long id, String authKey, long studentId, Timestamp createdOn, Timestamp expiredOn){
        this.id = id;
        this.authKey = authKey;
        this.studentId = studentId;
        this.createdOn = createdOn.toLocalDateTime();
        this.expiredOn = expiredOn.toLocalDateTime();
    }

    public static final class Builder {
        private long id;
        private String authKey;
        private long studentId;
        private Timestamp createdOn;
        private Timestamp expiredOn;

        private final CommonEnum.CrudType crudType;

        public Builder(String authKey, long studentId) {
            this.id = SnowflakeIdGenerator.genId();
            this.authKey = authKey;
            this.studentId = studentId;
            this.crudType = CommonEnum.CrudType.isCreate;
        }

        public Builder(long studentId) {
            this.studentId = studentId;
            this.crudType = CommonEnum.CrudType.isFind;
        }

        public static Builder builder등록(String authKey, long studentId) {
            return new Builder(authKey, studentId);
        }

        public static Builder builder찾기(long studnetId) {
            return new Builder(studnetId);
        }

        public Builder id(long val) {
            id = val;
            return this;
        }

        public Builder authKey(String val) {
            authKey = val;
            return this;
        }

        public Builder studentId(long val) {
            studentId = val;
            return this;
        }

        public Builder createOn(Timestamp createdOn) {
            this.createdOn = createdOn;
            return this;
        }

        public Builder expiredOn(Timestamp expiredOn) {
            this.expiredOn = expiredOn;
            return this;
        }

        public TokenAccessAuth build() {
            return switch (this.crudType){
                case isCreate -> {
                    Assert.notNull(id, "필수값입니다.");
                    Assert.notNull(authKey, "필수값입니다.");
                    Assert.notNull(studentId, "필수값입니다.");
                    yield new TokenAccessAuth(authKey, studentId);
                }

                case isFind -> {
                    yield  new TokenAccessAuth(id, authKey, studentId, createdOn, expiredOn);
                }
                default -> throw new IllegalStateException("Unexpected value: " + this.crudType);
            };
        }
    }
}
