package com.compiler.tourpanse.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.compiler.tourpanse.R;
import com.compiler.tourpanse.dbhelper.UserDataSource;
import com.compiler.tourpanse.helper.SaveUserCredentialsToSharedPreference;
import com.compiler.tourpanse.models.User;

public class LoginActivity extends AppCompatActivity {

    private EditText usernameEt;
    private EditText passwordEt;


    private Intent intent;
    private User user;
    private UserDataSource userDataSource;
    private SaveUserCredentialsToSharedPreference saveUserCredentialsToSharedPreference;

    private String username;
    private String password;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        userDataSource = new UserDataSource(LoginActivity.this);
        usernameEt = (EditText) findViewById(R.id.usernameEt);
        passwordEt = (EditText) findViewById(R.id.passwordEt);

        saveUserCredentialsToSharedPreference = new SaveUserCredentialsToSharedPreference(LoginActivity.this);

    }

    public void login(View view) {
        username = usernameEt.getText().toString().trim();
        password = passwordEt.getText().toString().trim();

        if(username.equals("")) {
            usernameEt.setError("Username is required.");
        } else if(password.equals("")) {
            passwordEt.setError("Password is required.");
        } else {
            user = userDataSource.loginUser(username, password);
            if(user != null) {
                saveUserCredentialsToSharedPreference = new SaveUserCredentialsToSharedPreference(LoginActivity.this);
                saveUserCredentialsToSharedPreference.saveUserCredentials(user.getUserId());
                intent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent);
            } else {
                Toast.makeText(this, "Invalid Credential. Try again.", Toast.LENGTH_SHORT).show();
            }
        }

    }

    public void createAccount(View view) {
        Intent intent = new Intent(LoginActivity.this, RegistrationActivity.class);
        startActivity(intent);
    }
    public boolean onKeyDown(int keyCode, KeyEvent event){
        if(keyCode == KeyEvent.KEYCODE_BACK) {
            Intent Act2Intent = new Intent(this, LoginActivity.class);
            startActivity(Act2Intent);
            finish();
            return true;
        }
        return false;
    }
}
