package com.example.trip.auth;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.trip.R;
import com.example.trip.callback.UserCallBack;
import com.example.trip.entity.User;
import com.example.trip.utils.Database;
import com.google.android.gms.tasks.Task;
import com.google.android.material.progressindicator.CircularProgressIndicator;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;

public class SignupActivity extends AppCompatActivity {
    private TextInputLayout signup_TF_fullName;
    private TextInputLayout signup_TF_email;
    private TextInputLayout signup_TF_password;
    private TextInputLayout signup_TF_confirmPassword;
    private Button signup_BTN_createAccount;
    private CircularProgressIndicator signup_PB_loading;
    private Database database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        findViews();
        initVars();
    }

    private void findViews() {
        signup_TF_fullName = findViewById(R.id.signup_TF_fullName);
        signup_TF_email = findViewById(R.id.signup_TF_email);
        signup_TF_password = findViewById(R.id.signup_TF_password);
        signup_TF_confirmPassword = findViewById(R.id.signup_TF_confirmPassword);
        signup_BTN_createAccount = findViewById(R.id.signup_BTN_createAccount);
        signup_PB_loading = findViewById(R.id.signup_PB_loading);
    }

    private void initVars() {
        database = new Database();
        database.setUserCallBack(new UserCallBack() {
            @Override
            public void onCreateAccountComplete(Task<Void> task) {
                signup_PB_loading.setVisibility(View.INVISIBLE);
                if(task.isSuccessful()){
                    Toast.makeText(SignupActivity.this, "account created successfully", Toast.LENGTH_SHORT).show();
                    database.logout();
                    finish();
                }else{
                    String error = task.getException().getMessage().toString();
                    Toast.makeText(SignupActivity.this, error, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onLoginComplete(Task<AuthResult> task) {

            }

            @Override
            public void onFetchUserComplete(User user) {

            }
        });

        signup_BTN_createAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // create account
                if(!checkInput()) return;
                String fullName = signup_TF_fullName.getEditText().getText().toString();
                String email = signup_TF_email.getEditText().getText().toString();
                String password = signup_TF_password.getEditText().getText().toString();
                User user = new User()
                        .setEmail(email)
                        .setPassword(password)
                        .setFullName(fullName);
                signup_PB_loading.setVisibility(View.VISIBLE);
                database.createAccount(user);
            }
        });
    }

    private boolean checkInput() {
        String fullName = signup_TF_fullName.getEditText().getText().toString();
        String email = signup_TF_email.getEditText().getText().toString();
        String password = signup_TF_password.getEditText().getText().toString();
        String confirmPassword = signup_TF_confirmPassword.getEditText().getText().toString();

        if(fullName.isEmpty()){
            Toast.makeText(this, "name is required!", Toast.LENGTH_SHORT).show();
            return  false;
        }

        if(email.isEmpty()){
            Toast.makeText(this, "email is required!", Toast.LENGTH_SHORT).show();
            return  false;
        }

        if(password.length() < 6){
            Toast.makeText(this, "password must be at least with 6 characters", Toast.LENGTH_SHORT).show();
            return  false;
        }

        if(!confirmPassword.equals(password)){
            Toast.makeText(this, "mismatch between confirm password and password", Toast.LENGTH_SHORT).show();
            return  false;
        }
        return true;
    }
}