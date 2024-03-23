package com.example.trip.home;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.trip.R;
import com.example.trip.auth.LoginActivity;
import com.example.trip.entity.User;
import com.example.trip.utils.Database;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileFragment extends Fragment {
    public static final String CURRENT_USER = "CURRENT_USER";
    private User currentUser;
    private Context context;
    private Database database;

    private CircleImageView profile_image;
    private TextView profile_TV_name;
    private TextView profile_TV_email;
    private CardView profile_CV_editDetails;
    private CardView profile_CV_logout;

    public ProfileFragment(Context context) {
        // Required empty public constructor
        this.context = context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        findViews(view);
        initVars();
        return view;
    }

    private void initVars() {
        database = new Database();
        profile_CV_editDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ProfileEditActivity.class);
                intent.putExtra(CURRENT_USER, currentUser);
                startActivity(intent);
            }
        });

        profile_CV_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                database.logout();
                Intent intent = new Intent((Activity)context, LoginActivity.class);
                startActivity(intent);
                ((Activity) context).finish();
            }
        });
    }

    private void findViews(View view) {
        profile_image = view.findViewById(R.id.profile_image);
        profile_TV_name = view.findViewById(R.id.profile_TV_name);
        profile_TV_email = view.findViewById(R.id.profile_TV_email);
        profile_CV_editDetails = view.findViewById(R.id.profile_CV_editDetails);
        profile_CV_logout = view.findViewById(R.id.profile_CV_logout);
    }

    public void setUser(User user) {
        currentUser = user;
        displayUser();
    }

    private void displayUser() {
        profile_TV_name.setText(currentUser.getFullName());
        profile_TV_email.setText(currentUser.getEmail());
        if(currentUser.getImageUrl() != null && !currentUser.getImageUrl().isEmpty()){
            Glide.with(context).load(currentUser.getImageUrl()).into(profile_image);
        }

    }

}