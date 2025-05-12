package tr.com.ind;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.PopupMenu;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.google.android.material.floatingactionbutton.FloatingActionButton;

import tr.com.ind.auth.SignInActivity;
import tr.com.ind.auth.SignUpActivity;
import tr.com.ind.chatusers.UserAdapter;

public class RealMainActivity extends AppCompatActivity {

    //veritabanı için nesne üretimi





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_real_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
//        Button fab = findViewById(R.id.addbutton);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                showPopupMenu(view);
//            }
//        });
    }

//    private void showPopupMenu(View view){
//        PopupMenu popupMenu = new PopupMenu(RealMainActivity.this, view);
//        MenuInflater inflater = popupMenu.getMenuInflater();
//        inflater.inflate(R.menu.chat_menu,popupMenu.getMenu());
//        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
//            @Override
//            public boolean onMenuItemClick(MenuItem item) {
//                int id = item.getItemId();
//                if(id == R.id.action_add_friend){
//                    return true;
//                } else if (id = R.id.action_add_group) {
//                    return true;
//                }
//                return false;
//            }
//        });
//        popupMenu.show();
//    }

    public void settingsActivity(View view){
        Intent intent = new Intent(RealMainActivity.this, SettingsActivity.class);
        startActivity(intent);
    }



}