package tr.com.ind.api.dto.response;

import java.util.Date;

public class User {

    private long id;
    private String phone;
    private String name;
    private String surname;
    private byte[] photo;
    private Boolean isActive;
    private Date createdDate;


    public User(){

    }

    public User(long id, String phone, String name, String surname, byte[] photo, Boolean isActive, Date createdDate) {
        this.id = id;
        this.phone = phone;
        this.name = name;
        this.surname = surname;
        this.photo = photo;
        this.isActive = isActive;
        this.createdDate = createdDate;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

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

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }
}
