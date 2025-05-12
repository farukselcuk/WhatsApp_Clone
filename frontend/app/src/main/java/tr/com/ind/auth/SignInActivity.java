package tr.com.ind.auth;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import tr.com.ind.R;
import tr.com.ind.RealMainActivity;
import tr.com.ind.SharedPrefs.SharedPreferencesHelper;
import tr.com.ind.api.ApiClient;
import tr.com.ind.api.UserApiService;
import tr.com.ind.api.dto.request.UserLoginRequest;
import tr.com.ind.api.dto.response.UserToken;

public class SignInActivity extends AppCompatActivity {
    private SharedPreferencesHelper sharedPreferencesHelper = null;
    Button signinbutton;
    EditText phone,password;
    CheckBox remembermecheck;
    //veritabanı için nesne üretme


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_sign_in);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        sharedPreferencesHelper = new SharedPreferencesHelper();



        //veritabanın başlatma getInstance();
        signinbutton = findViewById(R.id.signinbutton);
        phone = findViewById(R.id.editTextNumber);
        password = findViewById(R.id.editTextPassword);
        remembermecheck = findViewById(R.id.remembermebox);
        signinbutton.setOnClickListener(new View.OnClickListener() {
            // login butonuna tıklanıldığı zaman servisin login metotuna istek atılacak
            // remember me true(aktif) ise ve sunucudan başarılı olarak geri dönüş geldiyse shareda kaydedilecek
            // eğer ki sunucudan hata dönerse uyarı verilecek

            @Override
            public void onClick(View v) {
                String inphone = phone.getText().toString();
                String pass = password.getText().toString();

                if((TextUtils.isEmpty(inphone))){
                    Toast.makeText(SignInActivity.this,"enter phone",Toast.LENGTH_SHORT).show();
                } else if (TextUtils.isEmpty(pass)) {
                    Toast.makeText(SignInActivity.this,"enter password",Toast.LENGTH_SHORT).show();
                } else {
                    //sunucudan signin method

                    UserApiService client = ApiClient.getClient().create(UserApiService.class);
                    Call<UserToken> call = client.login(new UserLoginRequest(inphone,pass));
                    call.enqueue(new Callback<UserToken>() {

                        @Override
                        public void onResponse( Call<UserToken> call, Response<UserToken> response) {
                            if (response.code() == 200){
                                sharedPreferencesHelper = new SharedPreferencesHelper();
                                sharedPreferencesHelper.setSharedPreference(getApplicationContext(),"token", response.body().getToken());
                                Intent intent = new Intent(SignInActivity.this, RealMainActivity.class);
                                startActivity(intent);
                                finish();

                                if(remembermecheck.isChecked()){
                                    sharedPreferencesHelper.setSharedPreference(getApplicationContext(), "loginPhone", inphone);
                                    sharedPreferencesHelper.setSharedPreference(getApplicationContext(), "loginPassword", pass);
                                }

                            }else{
                                //giriş başarısız ise bilgiler hatalı mesajı döndürür
                                Toast.makeText(getApplicationContext(),"Information Error "+ response.code(),Toast.LENGTH_SHORT).show();
                            }

                        }

                        @Override
                        public void onFailure(Call<UserToken> call, Throwable t) {
                            Log.i("Connection", t.getLocalizedMessage());
                            //sunucuya erişim sağlanamadığı zaman Sunucuya erişim sağlanamadı mesajı döndürür
                            Toast.makeText(getApplicationContext(),"The server could not be accessed "+ t.getMessage(),Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });
    }
}




