package com.example.trip.auth;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.trip.R;
import com.example.trip.callback.UserCallBack;
import com.example.trip.home.HomeActivity;
import com.example.trip.utils.Database;
import com.google.android.gms.tasks.Task;
import com.google.android.material.progressindicator.CircularProgressIndicator;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;

public class LoginActivity extends AppCompatActivity {

    private TextInputLayout login_TF_email;
    private TextInputLayout login_TF_password;
    private Button login_BTN_login;
    private Button login_BTN_createAccount;
    private CircularProgressIndicator login_PB_loading;
    private Database database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        findViews();
        initVars();
    }

    private void initVars() {
        database = new Database();
        database.setUserCallBack(new UserCallBack() {
            @Override
            public void onCreateAccountComplete(Task<Void> task) {

            }

            @Override
            public void onLoginComplete(Task<AuthResult> task) {
                login_PB_loading.setVisibility(View.INVISIBLE);
                if(task.isSuccessful()){
                    Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                    startActivity(intent);
                    finish();
                }else{
                    String err = task.getException().getMessage().toString();
                    Toast.makeText(LoginActivity.this, err, Toast.LENGTH_SHORT).show();
                }
            }
        });
        login_BTN_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // do login
                if(!checkInput()) return;
                String email = login_TF_email.getEditText().getText().toString();
                String password = login_TF_password.getEditText().getText().toString();
                login_PB_loading.setVisibility(View.VISIBLE);
                database.loginUser(email, password);
            }
        });

        login_BTN_createAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // go to signup screen
                Intent intent = new Intent(LoginActivity.this, SignupActivity.class);
                startActivity(intent);
            }
        });
    }

    private void findViews() {
        login_TF_email = findViewById(R.id.login_TF_email);
        login_TF_password = findViewById(R.id.login_TF_password);
        login_BTN_login = findViewById(R.id.login_BTN_login);
        login_BTN_createAccount = findViewById(R.id.login_BTN_createAccount);
        login_PB_loading = findViewById(R.id.login_PB_loading);

    }

    private boolean checkInput() {
        String email = login_TF_email.getEditText().getText().toString();
        String password = login_TF_password.getEditText().getText().toString();

        if(email.isEmpty()){
            Toast.makeText(this, "email is required!", Toast.LENGTH_SHORT).show();
            return  false;
        }

        if(password.length() < 6){
            Toast.makeText(this, "password is required!", Toast.LENGTH_SHORT).show();
            return  false;
        }

        return true;
    }

    @Override
    protected void onStart() {
        super.onStart();
        Database db = new Database();
        if(db.getCurrentUser() != null){
            Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
            startActivity(intent);
            finish();
        }
    }
}