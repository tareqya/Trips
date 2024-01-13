package com.example.trip.callback;

import com.example.trip.entity.User;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;

public interface UserCallBack {
    void onCreateAccountComplete(Task<Void> task);
    void onLoginComplete(Task<AuthResult> task);
    void onFetchUserComplete(User user);
}
