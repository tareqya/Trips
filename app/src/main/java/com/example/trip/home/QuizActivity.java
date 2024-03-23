package com.example.trip.home;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.trip.R;
import com.example.trip.callback.ActivityCallBack;
import com.example.trip.entity.BookedTrip;
import com.example.trip.entity.Question;
import com.example.trip.entity.Trip;
import com.example.trip.utils.Database;
import com.example.trip.utils.Exam;
import com.tareq.alertdialog.AlertMsg;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public class QuizActivity extends AppCompatActivity {
    private TextView quiz_TV_questionNumber;
    private TextView quiz_TV_question;
    private RadioGroup quiz_RG_options;
    private Database database;
    private Exam exam;
    private Button quiz_BTN_next;
    private Question currentQuestion;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        findViews();
        initVars();
        Intent intent = getIntent();
        BookedTrip trip = (BookedTrip) intent.getSerializableExtra("SELECTED_TRIP");
        String id = trip.getUid();
        fetchQuestions(id);
    }

    private void initVars() {
        quiz_BTN_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(exam.isComplete()){
                    finish();
                }else {
                    checkAnswer();
                    quiz_RG_options.clearCheck();
                    currentQuestion = exam.nextQuestion();
                    displayQuestion();
                }
            }
        });

        quiz_RG_options.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // Find the selected radio button within the RadioGroup
                RadioButton selectedRadioButton = findViewById(checkedId);

                // Check if any radio button is selected
                if (selectedRadioButton != null) {
                    // Get the text of the selected radio button
                    String selectedText = selectedRadioButton.getText().toString();
                    // Show a toast with the selected text
                    currentQuestion.setAnswer(selectedText);
                }
            }
        });
    }

    private void checkAnswer() {
        boolean isCorrect = currentQuestion.getCorrectAnswer().equals(currentQuestion.getAnswer());
        String msg = isCorrect ?
                "You answer is correct!" :
                "You answer is not correct, the correct answer is " + currentQuestion.getCorrectAnswer();
        AlertMsg dialog = new AlertMsg(this)
                .setTitle("Answer Status")
                .setMessage(msg)
                .setPosButtonText("Ok")
                .setMsgType(!isCorrect ? AlertMsg.ERROR_MSG: AlertMsg.SUCCESS_MSG)
                .setPosClickListener(new AlertMsg.OnPosButtonClickListener() {
                    @Override
                    public void onPosButtonClicked() {

                    }
                });

        dialog.show();
    }

    private void findViews() {
        quiz_RG_options = findViewById(R.id.quiz_RG_options);
        quiz_TV_question = findViewById(R.id.quiz_TV_question);
        quiz_TV_questionNumber = findViewById(R.id.quiz_TV_questionNumber);
        quiz_BTN_next = findViewById(R.id.quiz_BTN_next);
    }

    private void fetchQuestions(String id) {
        database = new Database();
        database.setActivityCallBack(new ActivityCallBack() {
            @Override
            public void onActivitiesFetchComplete(ArrayList<BookedTrip> trips) {

            }

            @Override
            public void onActivityQuestionsFetchComplete(ArrayList<Question> questions) {
                Collections.sort(questions);
                exam = new Exam(questions);
                currentQuestion = exam.nextQuestion();
                displayQuestion();
            }
        });

        database.fetchActivityQuestions(id);
    }

    private void displayQuestion() {
        quiz_TV_questionNumber.setText("Question " + currentQuestion.getOrder());
        quiz_TV_question.setText(currentQuestion.getQuestion());

        String[] options = currentQuestion.generateOptions();
        for(int i = 0 ; i < quiz_RG_options.getChildCount(); i++){
            RadioButton radioButton = (RadioButton) quiz_RG_options.getChildAt(i);
            radioButton.setText(options[i]);
        }
    }
}