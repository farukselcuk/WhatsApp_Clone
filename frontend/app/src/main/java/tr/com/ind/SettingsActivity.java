package tr.com.ind;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import tr.com.ind.SharedPrefs.SharedPreferencesHelper;

public class SettingsActivity extends AppCompatActivity {

    TextView number;
    EditText status,name,surname,newpass,newrepass;
    Button savechangesbutton,logout;

    private SharedPreferencesHelper sharedPreferencesHelper;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_settings);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        sharedPreferencesHelper =   new SharedPreferencesHelper();

        number = findViewById(R.id.settingsNumber);
        status = findViewById(R.id.settingsStatus);
        name = findViewById(R.id.settingsName);
        surname = findViewById(R.id.settingsSurname);
        newpass = findViewById(R.id.settingsPass);
        newrepass = findViewById(R.id.settingsRePass);
        savechangesbutton = findViewById(R.id.savebutton);

        savechangesbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String setnumber = sharedPreferencesHelper.getSharedPreference(getApplicationContext(),"setphone",null);
                String setstatus = sharedPreferencesHelper.getSharedPreference(getApplicationContext(),"setstatus",null);
                String setname = sharedPreferencesHelper.getSharedPreference(getApplicationContext(),"setname",null);
                String setsurname = sharedPreferencesHelper.getSharedPreference(getApplicationContext(), "setsurname",null);
                String setnewpass = sharedPreferencesHelper.getSharedPreference(getApplicationContext(),"setnewpass",null);
                String setnewrepass = sharedPreferencesHelper.getSharedPreference(getApplicationContext(),"setnewrepass",null);

                number.setText(setnumber);
                name.setText(setname);
                surname.setText(setsurname);



            }
        });





    }
}