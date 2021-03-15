package id.ac.umn.week04a_26850;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MenuActivity extends AppCompatActivity {
    private Button btnHalaman1, btnHalaman2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        btnHalaman1 = findViewById(R.id.main_button_change_1);
        btnHalaman2 = findViewById(R.id.main_button_change_2);

        btnHalaman1.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intentSecond = new Intent(MenuActivity.this, SecondActivity.class);
                startActivityForResult(intentSecond, 1);
            }
        });

        btnHalaman2.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intentThird = new Intent(MenuActivity.this, ThirdActivity.class);
                startActivityForResult(intentThird, 1);
            }
        });
    }
}