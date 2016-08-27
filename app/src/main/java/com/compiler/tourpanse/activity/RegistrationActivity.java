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
import com.compiler.tourpanse.models.User;

public class RegistrationActivity extends AppCompatActivity {


    private EditText fullNameEt;
    private EditText phoneNumberEt;
    private EditText emailEt;
    private EditText usernameEt;
    private EditText passwordEt;
    private EditText confirmPasswordEt;
    private EditText emergencyContactNumberEt;


    private String fullName;
    private String phoneNumber;
    private String email;
    private String username;
    private String password;
    private String confirmPassword;
    private String emergencyContactNumber;
    private boolean status;




    private Intent intent;
    private User user;
    private UserDataSource userDataSource;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        userDataSource = new UserDataSource(RegistrationActivity.this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        fullNameEt = (EditText) findViewById(R.id.fullNameEt);
        phoneNumberEt = (EditText) findViewById(R.id.phoneNumberEt);
        emailEt = (EditText) findViewById(R.id.emailEt);
        usernameEt = (EditText) findViewById(R.id.usernameEt);
        passwordEt = (EditText) findViewById(R.id.passwordEt);
        confirmPasswordEt = (EditText) findViewById(R.id.confirmPasswordEt);
        emergencyContactNumberEt = (EditText) findViewById(R.id.emergencyContactNumberEt);


    }

    public void clear(View view) {
        intent = new Intent(RegistrationActivity.this, MainActivity.class);
        startActivity(intent);

    }

    public void save(View view) {
        fullName = fullNameEt.getText().toString().trim();
        phoneNumber = phoneNumberEt.getText().toString().trim();
        email = emailEt.getText().toString().trim();
        username = usernameEt.getText().toString().trim();
        password = passwordEt.getText().toString().trim();
        confirmPassword = confirmPasswordEt.getText().toString().trim();
        emergencyContactNumber = emergencyContactNumberEt.getText().toString().trim();

       if(fullName.equals("")) {
           fullNameEt.setError("Name is required.");
       } else if(phoneNumber.equals("")) {
           phoneNumberEt.setError("Phone number is required.");
       } else if(emergencyContactNumber.equals("")) {
           emergencyContactNumberEt.setError("Enter a emergency contact number.");
       } else if(email.equals("")) {
           emailEt.setError("Email is required.");
       } else if(username.equals("")) {
           usernameEt.setError("Username is required.");
       } else if(password.equals("")) {
           passwordEt.setError("Password is required.");
       } else if(confirmPassword.equals("")) {
           confirmPasswordEt.setError("Re-enter your password.");
       } else if(!password.equals(confirmPassword)) {
           passwordEt.setError("Password miss match.");
           confirmPasswordEt.setError("Must match with previous password.");
       } else if( !userDataSource.isUniqueEmail(email) ) {
           emailEt.setError("Email has already been taken.");
       } else if( !userDataSource.isUniqueUsername(username) ) {
           usernameEt.setError("Username has already been taken.");
       } else {
            user = new User(fullName, email, phoneNumber, emergencyContactNumber, username, password);
           status = userDataSource.saveUser(user);

           if(status) {
               Toast.makeText(this, "Registration successful. Log in to start your session.", Toast.LENGTH_SHORT).show();
               intent = new Intent(RegistrationActivity.this, LoginActivity.class);
               startActivity(intent);
           } else {
               Toast.makeText(this, "Something went wrong. Please try again.", Toast.LENGTH_SHORT).show();
           }
       }

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(this, LoginActivity.class));
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
