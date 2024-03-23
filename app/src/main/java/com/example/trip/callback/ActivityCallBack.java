package com.example.trip.callback;

import com.example.trip.entity.BookedTrip;
import com.example.trip.entity.Question;

import java.util.ArrayList;

public interface ActivityCallBack {
    void onActivitiesFetchComplete(ArrayList<BookedTrip> trips);
    void onActivityQuestionsFetchComplete(ArrayList<Question> questions);
}
