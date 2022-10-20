package com.company.mathgame;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import java.util.Random;

public class Game extends AppCompatActivity {

    TextView mScore, mLife, mTime, mQuestion;
    EditText mAnswer;

    Button mOk, mNext;

    Random mRandom = new Random();
    int mNum1, mNum2, mUserAnswer, mSolution, mUserScore = 0, mUserLife = 3;

    CountDownTimer mTimer;
    private static final long START_TIMER_IN_MILLIS = 60_000L;
    long time_left_in_millis = START_TIMER_IN_MILLIS;
    Boolean timer_running;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        mScore = findViewById(R.id.score_value_textView_game_act);
        mLife = findViewById(R.id.life_value_textView_game_act);
        mTime = findViewById(R.id.time_value_textView_game_act);

        mQuestion = findViewById(R.id.questions_game_act);
        mAnswer = findViewById(R.id.answer_game_act);

        mOk = findViewById(R.id.btn_ok_game_act);
        mNext = findViewById(R.id.btn_next_game_act);

        gameContinue();

        mOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                mUserAnswer = Integer.parseInt( mAnswer.getText().toString());
                if(mUserAnswer == mSolution){
                    mUserScore+=10;
                    mScore.setText(String.valueOf(mUserScore));
                    mQuestion.setText(R.string.question_success);
                }else{
                    mUserLife -= 1;
                    mLife.setText(String.valueOf(mUserLife));
                    mQuestion.setText(R.string.answer_wrong);
                }
            }
        });

        mNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAnswer.setText("");
                gameContinue();
            }
        });
    }

    @SuppressLint("SetTextI18n")
    public void gameContinue() {
        mNum1 = mRandom.nextInt(100);
        mNum2 = mRandom.nextInt(100);

        String[] operation = {"+", "-", "/", "*"};
        String op1 = operation[mRandom.nextInt(3)];


        switch (op1) {
            case "+":
                mSolution = mNum1 + mNum2;
                break;
            case "-":
                mSolution = mNum1 - mNum2;
                break;
            case "/":
                mSolution = mNum1 / mNum2;
                break;
            case "*":
                mSolution = mNum1 * mNum2;
                break;
            default:
                break;


        }

        mQuestion.setText(mNum1 + op1 + mNum2);
    }
}