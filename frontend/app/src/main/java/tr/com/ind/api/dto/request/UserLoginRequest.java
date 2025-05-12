package tr.com.ind.api.dto.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class UserLoginRequest implements Serializable {
    @SerializedName("phone")
    @Expose
    String phone;
    @SerializedName("password")
    @Expose
    String password;

    public UserLoginRequest() {

    }

    public UserLoginRequest(String phone, String password) {
        this.phone = phone;
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
