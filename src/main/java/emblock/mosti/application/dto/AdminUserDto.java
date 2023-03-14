package emblock.mosti.application.dto;

public class AdminUserDto {
    private String id;
    private String name;
    private String email;
    private String status;
    private String createdOn;

    public AdminUserDto() {
        this.id = "";
        this.name = "";
        this.email = "";
        this.status = "";
        this.createdOn = "";
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

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(String createdOn) {
        this.createdOn = createdOn;
    }
}

