package tr.com.ind.api.dto.request;

public class RegisterRequest {
    private String name;
    private String phone;
    private String surname;
    private String password;
    private byte[] photo;


    public RegisterRequest() {

    }

    public RegisterRequest(String name, String phone, String surname, String password, byte[] photo) {
        this.name = name;
        this.phone = phone;
        this.surname = surname;
        this.password = password;
        this.photo = photo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
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

    public byte[] getPhoto() {
        return photo;
    }

    public void setPhoto(byte[] photo) {
        this.photo = photo;
    }
}
