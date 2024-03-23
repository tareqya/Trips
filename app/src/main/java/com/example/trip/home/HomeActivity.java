package com.example.trip.home;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;

import com.example.trip.R;
import com.example.trip.callback.UserCallBack;
import com.example.trip.entity.User;
import com.example.trip.utils.Database;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.auth.AuthResult;

public class HomeActivity extends AppCompatActivity {

    private FrameLayout home_FL_home;
    private FrameLayout home_FL_activity;
    private FrameLayout home_FL_profile;
    private BottomNavigationView home_BN;

    private ProfileFragment profileFragment;
    private HomeFragment homeFragment;
    private ActivityFragment activityFragment;
    private Database database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        findViews();
        initVars();
        fetchCurrentUser();
    }

    private void fetchCurrentUser() {
        database = new Database();
        database.setUserCallBack(new UserCallBack() {
            @Override
            public void onCreateAccountComplete(Task<Void> task) {

            }

            @Override
            public void onLoginComplete(Task<AuthResult> task) {

            }

            @Override
            public void onFetchUserComplete(User user) {
                //
                profileFragment.setUser(user);
            }
        });

        String uid = database.getCurrentUser().getUid();
        database.fetchUserData(uid);
    }

    private void findViews() {
        home_FL_home = findViewById(R.id.home_FL_home);
        home_FL_activity = findViewById(R.id.home_FL_activity);
        home_FL_profile = findViewById(R.id.home_FL_profile);
        home_BN = findViewById(R.id.home_BN);
    }

    private void initVars() {
        profileFragment = new ProfileFragment(this);
        homeFragment = new HomeFragment(this);
        activityFragment = new ActivityFragment(this);

        getSupportFragmentManager().beginTransaction().add(R.id.home_FL_home, homeFragment).commit();
        getSupportFragmentManager().beginTransaction().add(R.id.home_FL_profile, profileFragment).commit();
        getSupportFragmentManager().beginTransaction().add(R.id.home_FL_activity, activityFragment).commit();

        home_FL_home.setVisibility(View.VISIBLE);
        home_FL_profile.setVisibility(View.INVISIBLE);
        home_FL_activity.setVisibility(View.INVISIBLE);

        home_BN.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if(item.getItemId() == R.id.activity){
                    home_FL_home.setVisibility(View.INVISIBLE);
                    home_FL_profile.setVisibility(View.INVISIBLE);
                    home_FL_activity.setVisibility(View.VISIBLE);
                }else if(item.getItemId() == R.id.home){
                    home_FL_home.setVisibility(View.VISIBLE);
                    home_FL_profile.setVisibility(View.INVISIBLE);
                    home_FL_activity.setVisibility(View.INVISIBLE);
                }else {
                    // profile
                    home_FL_home.setVisibility(View.INVISIBLE);
                    home_FL_profile.setVisibility(View.VISIBLE);
                    home_FL_activity.setVisibility(View.INVISIBLE);
                }
                return true;
            }
        });

    }
}