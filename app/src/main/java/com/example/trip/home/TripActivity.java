package com.example.trip.home;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.trip.R;
import com.example.trip.callback.TripCallBack;
import com.example.trip.entity.BookedTrip;
import com.example.trip.entity.Trip;
import com.example.trip.utils.Database;
import com.google.android.gms.tasks.Task;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class TripActivity extends AppCompatActivity {
    private ImageView trip_IV_image;
    private TextView trip_TV_title;
    private TextView trip_TV_description;
    private Button trip_BTN_book;
    private TextView trip_TV_length;
    private Button trip_BTN_pickDate;
    private TextView trip_TV_pickDate;
    private Trip trip;
    private Database database;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trip);

        Intent intent = getIntent();
        trip = (Trip) intent.getSerializableExtra("SELECTED_TRIP");

        findViews();
        initVars();
        displayTrip();

    }

    private void displayTrip() {
        Glide.with(this).load(trip.getImageUrl()).into(trip_IV_image);
        trip_TV_title.setText(trip.getTitle());
        trip_TV_description.setText(trip.getDescription());
        trip_TV_length.setText(trip.toString());
    }

    private void findViews() {
        trip_IV_image = findViewById(R.id.trip_IV_image);
        trip_TV_title = findViewById(R.id.trip_TV_title);
        trip_TV_description = findViewById(R.id.trip_TV_description);
        trip_BTN_book = findViewById(R.id.trip_BTN_book);
        trip_TV_length = findViewById(R.id.trip_TV_length);
        trip_BTN_pickDate = findViewById(R.id.trip_BTN_pickDate);
        trip_TV_pickDate= findViewById(R.id.trip_TV_pickDate);
    }

    private void initVars() {
        database = new Database();
        database.setTripCallBack(new TripCallBack() {
            @Override
            public void onTripsFetchComplete(ArrayList<Trip> trips) {

            }

            @Override
            public void onBookTripComplete(Task<Void> task) {
                if(task.isSuccessful()){
                    Toast.makeText(TripActivity.this, "Your trip booked successfully", Toast.LENGTH_SHORT).show();
                    finish();
                }else{
                    String err = task.getException().getMessage().toString();
                    Toast.makeText(TripActivity.this, err, Toast.LENGTH_SHORT).show();
                }
            }
        });
        trip_BTN_pickDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toggleDatePicker();
            }
        });

        trip_BTN_book.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try{
                    String dateTimeString = trip_TV_pickDate.getText().toString();
                    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
                    Date tripDate = sdf.parse(dateTimeString);

                    BookedTrip bookedTrip = new BookedTrip(trip, tripDate);
                    String uid = database.getCurrentUser().getUid();
                    database.bookTrip(uid, bookedTrip);
                }catch (Exception e){
                    Toast.makeText(TripActivity.this, "Please select a trip date!", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    private void toggleDatePicker() {
        MaterialDatePicker.Builder<Long> builder = MaterialDatePicker.Builder.datePicker();
        builder.setTitleText("Select trip date");
        MaterialDatePicker<Long> datePicker = builder.build();
        datePicker.show(getSupportFragmentManager(), "datePicker");
        datePicker.addOnPositiveButtonClickListener(new MaterialPickerOnPositiveButtonClickListener<Long>() {
            @Override
            public void onPositiveButtonClick(Long selection) {
                Date date = new Date(selection);
                // Create a SimpleDateFormat object with the desired format
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

                // Use the format() method to convert Date to String
                String formattedDate = sdf.format(date);
                trip_TV_pickDate.setText(formattedDate);
            }
        });
    }
}