package emblock.mosti.application.domain;

import java.sql.Timestamp;

import org.springframework.util.Assert;

import emblock.framework.domain.SnowflakeIdGenerator;
import emblock.mosti.application.domain.common.CommonEnum;

public class Student extends BaseDomain{
    private long id;
    private String name;
    private String studentId;
    private String school;
    private String major;

    public long getId(){
        return id;
    }

    public String getName(){
        return name;
    }

    public String getStudentId(){
        return studentId;
    }
    
    public String getSchool(){
        return school;
    }

    public String getMajor(){
        return major;
    }

    private Student(String name, String studentId, String school, String major){
        this.id = SnowflakeIdGenerator.genId();
        this.name = name;
        this.studentId = studentId;
        this.school = school;
        this.major = major;
        super.현재일시설정();
    }

    private Student(long id, String name, String school, String major){
        this.id = id;
        this.name = name;
        // this.studentId = studentId;
        this.school = school;
        this.major = major;
        super.수정일시설정();
    }

    private Student(long id, String name, String studentId, String school, String major, Timestamp createdOn, Timestamp updatedOn){
        this.id = id;
        this.name = name;
        this.studentId = studentId;
        this.school = school;
        this.major = major;
        super.날짜조회(createdOn,updatedOn);
    }

    public static final class Builder {
        private long id;
        private String name;
        private String studentId;
        private String school;
        private String major;
        private Timestamp createdOn;
        private Timestamp updatedOn;


        private final CommonEnum.CrudType crudType;

        public Builder(String name, String studentId, String school, String major) {
            this.id = SnowflakeIdGenerator.genId();
            this.name = name;
            this.studentId = studentId;
            this.school = school;
            this.major = major;
            this.crudType = CommonEnum.CrudType.isCreate;
        }

        public Builder(long id, String name, String school, String major) {
            this.id = id;
            this.name = name;
            this.school = school;
            this.major = major;
            this.crudType = CommonEnum.CrudType.isUpdate;
        }

        public Builder(long id) {
            this.id = id;
            this.crudType = CommonEnum.CrudType.isFind;
        }

        public static Builder builder등록(String name, String studentId, String school, String major) {
            return new Builder(name, studentId, school, major);
        }

        public static Builder builder수정(long id, String name, String school, String major) {
            return new Builder(id, name, school, major);
        }

        public static Builder builder찾기(long id) {
            return new Builder(id);
        }

        public Builder id(long val) {
            id = val;
            return this;
        }

        public Builder name(String val) {
            name = val;
            return this;
        }

        public Builder studentId(String val) {
            studentId = val;
            return this;
        }

        public Builder school(String val) {
            school = val;
            return this;
        }

        public Builder major(String val) {
            major = val;
            return this;
        }

        public Builder createOn(Timestamp createdOn) {
            this.createdOn = createdOn;
            return this;
        }

        public Builder updatedOn(Timestamp updatedOn) {
            this.updatedOn = updatedOn;
            return this;
        }

        public Student build() {
            return switch (this.crudType){
                case isCreate -> {
                    Assert.notNull(id, "필수값입니다.");
                    Assert.notNull(name, "필수값입니다.");
                    Assert.notNull(studentId, "필수값입니다.");
                    Assert.notNull(school, "필수값입니다.");
                    Assert.notNull(major, "필수값입니다.");
                    yield new Student(name, studentId, school, major);
                }

                case isUpdate -> {
                    yield new Student(id, name, school, major);
                }
                case isFind -> {
                    yield  new Student(id, name, studentId, school, major, createdOn, updatedOn);
                }
                default -> throw new IllegalStateException("Unexpected value: " + this.crudType);
            };
        }
    }
}
