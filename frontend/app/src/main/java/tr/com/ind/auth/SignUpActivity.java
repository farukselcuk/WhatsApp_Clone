package tr.com.ind.auth;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.snackbar.Snackbar;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.Arrays;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import tr.com.ind.R;
import tr.com.ind.SharedPrefs.SharedPreferencesHelper;
import tr.com.ind.api.ApiClient;
import tr.com.ind.api.UserApiService;
import tr.com.ind.api.dto.request.RegisterRequest;
import tr.com.ind.api.dto.response.UserToken;

public class SignUpActivity extends AppCompatActivity {

    ActivityResultLauncher<Intent> activityResultLauncher;
    ActivityResultLauncher<String> permissionLauncher;

    private SharedPreferencesHelper sharedPreferencesHelper;
    //Varsayılan görsel için
    private final int DEFAULT_IMAGE_RESOURCE = R.drawable.image10;


    EditText name,surname,phone,password,repassword;
    Button signupbutton,imageaddbutton;
    ImageView imageView;
     //görsel eklemek için nesne üretme
    //veritabanı için nesne üretme
    //Galeriye gitmek için izin ister



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_sign_up);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        sharedPreferencesHelper =   new SharedPreferencesHelper();

        name = findViewById(R.id.editTextText);
        surname = findViewById(R.id.editTextText2);
        phone = findViewById(R.id.editTextPhone);
        password = findViewById(R.id.editTextTextPassword2);
        repassword = findViewById(R.id.editTextTextPassword3);
        signupbutton= findViewById(R.id.signinbutton);
        imageView = findViewById(R.id.imageView);
        imageaddbutton = findViewById(R.id.imagebutton);

        //Varsayılan görseli ayarlama
        imageView.setImageResource(DEFAULT_IMAGE_RESOURCE);

        signupbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String fname = name.getText().toString();
                String lname= surname.getText().toString();
                String upphone= phone.getText().toString();
                String fpass= password.getText().toString();
                String lpass= repassword.getText().toString();

                Bitmap bitmap = ((BitmapDrawable) imageView.getDrawable()).getBitmap();
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG,100,stream);
                byte[] imageBytes = stream.toByteArray();



                sharedPreferencesHelper.setSharedPreference(getApplicationContext(), "name", fname);
                sharedPreferencesHelper.setSharedPreference(getApplicationContext(), "surname", lname);
                sharedPreferencesHelper.setSharedPreference(getApplicationContext(), "phone", upphone);
                sharedPreferencesHelper.setSharedPreference(getApplicationContext(), "password", fpass.equals(lpass) ? fpass : null);
                sharedPreferencesHelper.setSharedPreference(getApplicationContext(),"photo", Arrays.toString(imageBytes));


                //kullanıcıdan telefon numarasının ilk rakamı 0 ve toplam 11 rakam girmesini söylüyor
                phone.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        String text= s.toString();
                        if (!text.startsWith("0") && !text.isEmpty()) {
                            phone.setError("Phone number must start with 0");
                        }
                        if (text.length() != 11) {
                            phone.setError("The phone number must be 11 digits");
                        }
                    }

                    @Override
                    public void afterTextChanged(Editable s) {
                    }

                });

                //signup kısmındaki tüm verilerin boş olup olmadığına,karakter sayısına,şifrelerin uyumlu olma durumuna bakar.
                if(TextUtils.isEmpty(fname) || TextUtils.isEmpty(lname) ||
                        TextUtils.isEmpty(upphone) || TextUtils.isEmpty(fpass) || TextUtils.isEmpty(lpass)){
                    Toast.makeText(SignUpActivity.this, "Please complete the information in full", Toast.LENGTH_SHORT).show();
                } else if (fpass.length()<6) {
                    password.setError("Your password must be at least 6 characters");
                } else if (!fpass.equals(lpass)) {
                    password.setError("Your passwords didn't match");
                }else {
                    //sunucudan signup metodu
                    UserApiService client = ApiClient.getClient().create(UserApiService.class);
                    //byte[] image = new byte[]{};
                    Call<UserToken> call = client.register(new RegisterRequest(fname,upphone,lname,fpass, imageBytes));
                    call.enqueue(new Callback<UserToken>() {
                        @Override
                        public void onResponse(Call<UserToken> call, Response<UserToken> response) {
                            if (response.code() == 201){
                                sharedPreferencesHelper = new SharedPreferencesHelper();
                                sharedPreferencesHelper.setSharedPreference(getApplicationContext(),"token", response.body().getToken());
                                Intent intent = new Intent(SignUpActivity.this, SignInActivity.class);
                                startActivity(intent);
                                finish();
                            }else{
                                //Kayıt başarısız ise geçersiz bilgi bulunmaktadır kontrol ediniz mesajı döndürür
                                Toast.makeText(getApplicationContext(),"Invalid data are available. Please check! "+ response.code(),Toast.LENGTH_SHORT).show();
                            }
                        }
                        @Override
                        public void onFailure(Call<UserToken> call, Throwable t) {
                            Log.i("Connection", t.getLocalizedMessage().toString());
                            //sunucuya erişim sağlanamadığı zaman Sunucuya erişim sağlanamadı mesajı döndürür
                            Toast.makeText(getApplicationContext(),"The server could not be accessed "+ t.getMessage(),Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });
        imageaddbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectImage(v);
            }
        });
            registerLauncher();
    }

        public void selectImage(View view){
            if(ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED){
                if(ActivityCompat.shouldShowRequestPermissionRationale(this,Manifest.permission.READ_EXTERNAL_STORAGE)){
                    Snackbar.make(view,"Permission needed for gallery",Snackbar.LENGTH_INDEFINITE).setAction("Give permission", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            //ask permission
                            permissionLauncher.launch(Manifest.permission.READ_EXTERNAL_STORAGE);
                        }
                    }).show();

                }else{
                    //ask permission
                    permissionLauncher.launch(Manifest.permission.READ_EXTERNAL_STORAGE);
                }
            }else{
                Intent intentToGallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                activityResultLauncher.launch(intentToGallery);
            }
        }

        private void registerLauncher(){
            activityResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if(result.getResultCode()== RESULT_OK){
                        Intent intentFromResult = result.getData();
                        if(intentFromResult != null){
                             try {
                                 InputStream inputStream = getContentResolver().openInputStream(intentFromResult.getData());
                                 Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                                 imageView.setImageBitmap(bitmap);
                                 inputStream.close();
                             }catch (Exception e){
                                 e.printStackTrace();
                             }

                        }
                    }
                }
            });
        }
        public void signinActivity(View view){
        Intent intent = new Intent(SignUpActivity.this, SignInActivity.class);
        startActivity(intent);
        //SignUPActivity'de signin butonuna basıldığında SignInActivity gitme metodu.
        }
}