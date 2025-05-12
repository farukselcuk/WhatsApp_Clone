package tr.com.ind.api.dto.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.Objects;

public class UserToken implements Serializable {
    @SerializedName("token")
    @Expose
    private String token;

    public UserToken(){

    }


    public UserToken(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

}
