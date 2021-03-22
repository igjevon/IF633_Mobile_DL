package id.ac.umn.uts_b_26850;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {
    private Button btnVerify;
    private EditText username;
    private EditText password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        btnVerify = findViewById(R.id.buttonVerify);
        username = findViewById(R.id.etUsername);
        password = findViewById(R.id.etPassword);

        btnVerify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkValidation();
            }
        });
    }

    void checkValidation() {
        boolean isValid = true;
        if(isEmpty(username)) {
            username.setError("Username required!");
            isValid = false;
        }
        if(isEmpty(password)) {
            password.setError("Password required!");
            isValid = false;
        }

        if(isValid) {
            String usernameValue = username.getText().toString();
            String passwordValue = password.getText().toString();
            if(usernameValue.equals("uasmobile") && passwordValue.equals("uasmobilegenap")) {
                Intent intentVerify = new Intent(LoginActivity.this, MainSongActivity.class);
                startActivity(intentVerify);
                this.finish();
            } else {
                Toast t = Toast.makeText(this, "Wrong username or password", Toast.LENGTH_SHORT);
                t.show();
            }
        }
    }

    boolean isEmpty(EditText text) {
        CharSequence str = text.getText().toString();
        return TextUtils.isEmpty(str);
    }

}