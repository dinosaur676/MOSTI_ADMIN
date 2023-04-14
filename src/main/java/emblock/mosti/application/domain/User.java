package emblock.mosti.application.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import emblock.framework.domain.SnowflakeIdGenerator;
import emblock.mosti.application.domain.common.CommonEnum;
import emblock.mosti.application.domain.common.CommonEnumType;
import org.springframework.util.Assert;

import java.sql.Timestamp;
import java.time.LocalDateTime;

public class User extends BaseDomain{

    private User(String loginId, String password, String userName, String email, String address, String phone,
                String cellPhone, String school, UserType type, UserStatus status) {
        this.userId = SnowflakeIdGenerator.genId();
        this.loginId = loginId;
        this.password = password;
        this.userName = userName;
        this.email = email;
        this.address = address;
        this.phone = phone;
        this.cellPhone = cellPhone;
        this.type = type;
        this.status = status;
        this.school = school;
        super.현재일시설정();
    }

    private User(long userId, String loginId, String password, String userName, String email, String address,
                String phone, String cellPhone, UserType type, UserStatus status) {
        this.userId = userId;
        this.loginId = loginId;
        this.password = password;
        this.userName = userName;
        this.email = email;
        this.address = address;
        this.phone = phone;
        this.cellPhone = cellPhone;
        this.type = type;
        this.status = status;
        super.수정일시설정();
    }

    private User(long userId, String loginId, String password, String userName, String email, String address, String phone, String cellPhone, String school, UserType type, UserStatus status, Timestamp createdOn, Timestamp updatedOn, int roleId) {
        this.userId = userId;
        this.loginId = loginId;
        this.password = password;
        this.userName = userName;
        this.email = email;
        this.address = address;
        this.phone = phone;
        this.cellPhone = cellPhone;
        this.type = type;
        this.status = status;
        this.roleId = roleId;
        this.school = school;
        super.날짜조회(createdOn,updatedOn);
    }

    private long userId;
    private String loginId;
    private String password;
    private String userName;
    private String email;
    private String address;
    private String phone;
    private String cellPhone;
    private UserType type;
    private UserStatus status;
    private int roleId;
    private String school;

    public enum UserType implements CommonEnumType {
        //A 2 : admin
        //B 0 : 교직원
        //S 1 : 학생

        S("학생", 1),B("교직원", 0),A("관리자", 2);
        private final String codeName;
        private final int roleId;
        @Override
        public String getCode(){
            return name();
        }
        @Override
        public String getCodeName(){
            return this.codeName;
        }

        public int getRoleId() {
            return roleId;
        }

        UserType(String codeName, int roleId) {
            this.codeName = codeName;
            this.roleId = roleId;
        }

        @JsonCreator( mode= JsonCreator.Mode.DELEGATING)
        public static UserType fromValue(String value){
            return CommonEnumType.jsonCreator(value, UserType.class);
        }
    }

    public enum UserStatus implements CommonEnumType {
        //P("가입대기"),A("가입승인"),R("가입거절"),D("비활성화");
        Y("사용"),N("미사용");
        private final String codeName;

        @Override
        public String getCode(){
            return name();
        }
        @Override
        public String getCodeName(){
            return this.codeName;
        }

        UserStatus(String codeName){
            this.codeName = codeName;
        }

        @JsonCreator( mode= JsonCreator.Mode.DELEGATING)
        public static UserStatus fromValue(String value){
            return CommonEnumType.jsonCreator(value, UserStatus.class);
        }
    }

    public long getUserId() {
        return userId;
    }

    public String getLoginId() {
        return loginId;
    }

    public String getPassword() {
        return password;
    }

    public String getUserName() {
        return userName;
    }

    public String getEmail() {
        return email;
    }

    public String getAddress() {
        return address;
    }

    public String getPhone() {
        return phone;
    }

    public String getCellPhone() {
        return cellPhone;
    }

    public UserType getType() {
        return type;
    }

    public UserStatus getStatus() {
        return status;
    }

    public int getRoleId() {
        return roleId;
    }

    public String getSchool() {
        return school;
    }

    public void 암호화패스워드설정(String encPassword){
        this.password = encPassword;
    }

    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", loginId='" + loginId + '\'' +
                ", password='" + password + '\'' +
                ", userName='" + userName + '\'' +
                ", email='" + email + '\'' +
                ", address='" + address + '\'' +
                ", phone='" + phone + '\'' +
                ", cellPhone='" + cellPhone + '\'' +
                ", type=" + type +
                ", status=" + status +
                ", roleId=" + roleId +
                ", school=" + school +
                '}';


    }

    public static final class Builder {
        private long userId;
        private String loginId;
        private String password;
        private String userName;
        private String email;
        private String address;
        private String phone;
        private String cellPhone;
        private UserType type;
        private UserStatus status;
        private int roleId;
        private String school;
        private Timestamp createdOn;
        private Timestamp updatedOn;


        private final CommonEnum.CrudType crudType;

        public Builder(String loginId, String password) {
            this.loginId = loginId;
            this.password = password;
            this.crudType = CommonEnum.CrudType.isCreate;
        }

        public Builder(long userId) {
            this.crudType = CommonEnum.CrudType.isUpdate;
            this.userId = userId;
        }

        public Builder(long userId, String userName, String school) {
            this.crudType = CommonEnum.CrudType.isCreate;
            this.school = school;
            this.userId = userId;
            this.userName = userName;
        }

        public Builder(long userId, String userName) {
            this.crudType = CommonEnum.CrudType.isFind;
            this.userId = userId;
            this.userName = userName;
        }

        public static Builder builder등록(String loginId, String password) {
            return new Builder(loginId, password);
        }

        public static Builder builder등록(long userId, String userName, String school) {
            return new Builder(userId, userName, school);
        }

        public static Builder builder수정(long userId) {
            return new Builder(userId);
        }

        public static Builder builder찾기(long userId, String userName) {
            return new Builder(userId, userName);
        }

        public Builder userId(long val) {
            userId = val;
            return this;
        }

        public Builder loginId(String val) {
            loginId = val;
            return this;
        }

        public Builder password(String val) {
            password = val;
            return this;
        }

        public Builder userName(String val) {
            userName = val;
            return this;
        }

        public Builder email(String val) {
            email = val;
            return this;
        }

        public Builder address(String val) {
            address = val;
            return this;
        }

        public Builder phone(String val) {
            phone = val;
            return this;
        }

        public Builder cellPhone(String val) {
            cellPhone = val;
            return this;
        }

        public Builder type(UserType val) {
            type = val;
            return this;
        }

        public Builder status(UserStatus val) {
            status = val;
            return this;
        }

        public Builder roleId(int val) {
            roleId = val;
            return this;
        }

        public Builder school(String val) {
            this.school = val;
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

        public User build() {
            return switch (this.crudType){
                case isCreate -> {
                    Assert.notNull(loginId, "필수값입니다.");
                    yield new User(loginId, password, userName, email, address, phone, cellPhone, school, type, status);
                }
                case isUpdate -> {
                    yield new User(userId, loginId, password, userName, email, address, phone, cellPhone, type, status);
                }
                case isFind -> {
                    yield  new User( userId,  loginId,  password,  userName,  email,  address,  phone,  cellPhone, school, type,  status, createdOn, updatedOn, roleId);
                }
                default -> throw new IllegalStateException("Unexpected value: " + this.crudType);
            };
        }
    }
}
