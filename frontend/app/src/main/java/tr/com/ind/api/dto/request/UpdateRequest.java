package tr.com.ind.api.dto.request;

public class UpdateRequest {

    private String name;
    private String surname;
    private String password;
    private String status;
    private byte[] photo;
    private Boolean isActive;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public byte[] getPhoto() {
        return photo;
    }

    public void setPhoto(byte[] photo) {
        this.photo = photo;
    }

    public Boolean getActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }



    public UpdateRequest(String name, String surname, String password, String status, byte[] photo, Boolean isActive) {
        this.name = name;
        this.surname = surname;
        this.password = password;
        this.status = status;
        this.photo = photo;
        this.isActive = isActive;
    }




}
