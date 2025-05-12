package tr.com.ind;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.view.View;

import tr.com.ind.SharedPrefs.SharedPreferencesHelper;
import tr.com.ind.api.ApiClient;
import tr.com.ind.api.UserApiService;
import tr.com.ind.api.dto.request.RegisterRequest;
import tr.com.ind.api.dto.request.UserLoginRequest;
import tr.com.ind.api.dto.response.UserToken;
import tr.com.ind.auth.SignInActivity;
import tr.com.ind.auth.SignUpActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;




public class MainActivity extends AppCompatActivity {
    private SharedPreferencesHelper sharedPreferencesHelper = null;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        //örnek
        // ilk başlangıç ekranında uygulamanın servis bağlantısı kontrol edebilirsin,

        // kullanıcı daha önce giriş yaptıysa bunu sharedddan kontrol edebilirsin
//        if (kullanıcı uygulamaya her girdiğinde kayıtlı olan telno ve şifre ile burada login isteği at gelen cevabı yani tokeni
//    diğer istek atacağın alanların headerına yerleştir)
//        if (token var ise sunucuya istek at geçerli olup olmadığını öğren geçerli ise direkt chat ekranın yönlendir)
//        {}
//        else sunucudan 400 dönüyorsa login ekranına yönlendir tekrar giriş yapsın
        sharedPreferencesHelper = new SharedPreferencesHelper();
        UserLoginRequest userLoginRequest = checkSharedPrefences();




        //15.08
//        RegisterRequest registerRequest = checkSharedPrefencesRegister();
//        if(registerRequest != null){
//            UserApiService client = ApiClient.getClient().create(UserApiService.class);
//            Call<UserToken> call =client.register(registerRequest);
//            call.enqueue(new Callback<UserToken>() {
//                @Override
//                public void onResponse(Call<UserToken> call, Response<UserToken> response) {
//                    if(response.code()==201){
//                        sharedPreferencesHelper = new SharedPreferencesHelper();
//                        sharedPreferencesHelper.setSharedPreference(getApplicationContext(), "token", response.body().getToken());
//                        Intent intent = new Intent(MainActivity.this, SignInActivity.class);
//                        startActivity(intent);
//                        finish();
//                    }
//                }
//
//                @Override
//                public void onFailure(Call<UserToken> call, Throwable t) {
//
//                }
//            });
//
//        }



        if (userLoginRequest  != null) {
            UserApiService client = ApiClient.getClient().create(UserApiService.class);
            Call<UserToken> call = client.login(userLoginRequest);
            call.enqueue(new Callback<UserToken>() {

                @Override
                public void onResponse(Call<UserToken> call, Response<UserToken> response) {
                    if (response.code() == 200) {
                        sharedPreferencesHelper = new SharedPreferencesHelper();
                        sharedPreferencesHelper.setSharedPreference(getApplicationContext(), "token", response.body().getToken());
                        Intent intent = new Intent(MainActivity.this, RealMainActivity.class);
                        startActivity(intent);
                        finish();


                    } else {
                        //giriş başarısız ise bilgiler hatalı mesajı döndürür
                        Toast.makeText(getApplicationContext(), "Information Error " + response.code(), Toast.LENGTH_SHORT).show();
                    }

                }

                @Override
                public void onFailure(Call<UserToken> call, Throwable t) {
                    Log.i("Connection", t.getLocalizedMessage());
                    //sunucuya erişim sağlanamadığı zaman Sunucuya erişim sağlanamadı mesajı döndürür
                    Toast.makeText(getApplicationContext(), "The server could not be accessed " + t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }

        Intent intent = new Intent(MainActivity.this, SignUpActivity.class);
        startActivity(intent);
        finish();

    }

    public UserLoginRequest checkSharedPrefences() {
        UserLoginRequest userLoginRequest = null;
        String phoneText = sharedPreferencesHelper.getSharedPreference(getApplicationContext(), "loginPhone", null);
        String passText = sharedPreferencesHelper.getSharedPreference(getApplicationContext(), "loginPassword", null);
        if (phoneText != null && passText != null) {
            userLoginRequest = new UserLoginRequest(phoneText, passText);
        }
        return userLoginRequest;
    }


    //15.08
//    public RegisterRequest checkSharedPrefencesRegister(){
//        RegisterRequest registerRequest = null;
//        String rnameText = sharedPreferencesHelper.getSharedPreference(getApplicationContext(), "logupName",null);
//        String rsurnameText = sharedPreferencesHelper.getSharedPreference(getApplicationContext(),"logupSurname",null);
//        String rphoneText = sharedPreferencesHelper.getSharedPreference(getApplicationContext(),"logupPhone",null);
//        String rfpassText = sharedPreferencesHelper.getSharedPreference(getApplicationContext(),"logupPassword",null);
//        String rlpassText = sharedPreferencesHelper.getSharedPreference(getApplicationContext(), "logupsecondPassword",null);
//        if(rnameText != null && rsurnameText != null && rphoneText != null && rfpassText !=null && rfpassText.equals(rlpassText)){
//            registerRequest = new RegisterRequest(rnameText,rphoneText,rsurnameText,rfpassText,null);
//        }
//        return registerRequest;
//    }



    public void signuppp(View view){
        Intent intent = new Intent(MainActivity.this, SignUpActivity.class);
        startActivity(intent);
        finish();
        //SignInActivity'de signup butonuna basıldığında SignUPActivity gitme metodu.
    }

}


