package emblock.mosti.application.domain;


import emblock.framework.domain.GuidFactory;

import java.time.LocalDateTime;

public class AdminUser {
    private String id;
    private String name;
    private String password;
    private String email;
    private String status;
    private LocalDateTime createdOn;

    public AdminUser(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    public String getStatus() {
        return status;
    }

    public LocalDateTime getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(LocalDateTime createdOn) {
        this.createdOn = createdOn;
    }


    public static AdminUser 생성(String name, String password, String email) {
        AdminUser adminUser = new AdminUser(GuidFactory.시간기준_생성());
        adminUser.setName(name);
        adminUser.setPassword(password);
        adminUser.setEmail(email);
        adminUser.setStatus("Y");

        return adminUser;
    }

}
