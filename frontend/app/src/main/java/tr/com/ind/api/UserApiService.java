package tr.com.ind.api;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import tr.com.ind.api.dto.request.RegisterRequest;
import tr.com.ind.api.dto.request.UserLoginRequest;
import tr.com.ind.api.dto.response.User;
import tr.com.ind.api.dto.response.UserToken;

public interface UserApiService {

    // POST isteği - Yeni bir kullanıcı oluştur
    @POST("api/v1/users/register")
    Call<UserToken> register(@Body RegisterRequest registerRequest);


    // POST isteği - kullanıcı girişi
    @POST("api/v1/users/login")
    @Headers({"Accept: application/json"})
    Call<UserToken> login(@Body UserLoginRequest userLoginRequest);

    // GET isteği - Kullanıcı listesini al
    @GET("api/v1/users/users")
    Call<List<User>> getUsers();

    // GET isteği - Kullanıcıdan telefon numarası al
    @GET("api/v1/users/login/phone/{phone}")
    Call<User> getPhoneUser(@Path("phone") String phone);

    // GET isteği - Kullanıcıdan id al
    @GET("api/v1/users/login/user/{id}")
    Call<User> getIdUser(@Path("id") long idUser);

    // PUT isteği - Kullanıcı bilgilerini güncelle
    @PUT("api/v1/users/login/update/{id}")
    Call<User> updateUser(@Path("id") long idUser, @Body User user);

    // PATCH isteği - Kullanıcının aktif olması
    @PATCH("api/v1/users/login/{id}/deactive")
    Call<User> deactiveUser(@Path("id") long idUser);

    //PATCH isteği - Kullanıcının aktif olmaması
    @PATCH("api/v1/users/login/{id}/active")
    Call<User> activeUser(@Path("id") long idUser);






}
