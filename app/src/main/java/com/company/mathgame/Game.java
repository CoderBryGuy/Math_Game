

package com.company.mathgame;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.core.widget.TextViewCompat;

import java.util.Locale;
import java.util.Random;

public class Game extends AppCompatActivity {

    TextView mScore, mLife, mTime, mQuestion;
    EditText mAnswer;

    Button mOk, mNext;

    Random mRandom = new Random();
    int mNum1, mNum2, mUserAnswer, mSolution, mUserScore = 0, mUserLife = 3;

    CountDownTimer mTimer;
    private static final long START_TIMER_IN_MILLIS = 10_000L;
    long time_left_in_millis = START_TIMER_IN_MILLIS;
    Boolean timer_running;

    final static String SCORE_TAG = "score";


    private static final String TAG = "Game";

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
                Log.d(TAG, "onClick: called " + mOk.getText().toString());

                pauseTimer();

                if (mAnswer.getText().toString().length() > 0) {
                    mUserAnswer = Integer.parseInt(mAnswer.getText().toString());
                    if (mUserAnswer == mSolution) {
                        mUserScore += 10;
                        mScore.setText(String.valueOf(mUserScore));
                        mQuestion.setText(R.string.question_success);
                        TextViewCompat.setAutoSizeTextTypeWithDefaults(mQuestion, TextViewCompat.AUTO_SIZE_TEXT_TYPE_UNIFORM);
                    } else {
                        mUserLife -= 1;
                        mLife.setText(String.valueOf(mUserLife));
                        mQuestion.setText(R.string.answer_wrong);
                        Toast.makeText(Game.this, "Correct Answer: " + mSolution, Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(Game.this, "Input an answer", Toast.LENGTH_SHORT).show();
                }
            }
        });

        mNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(timer_running){
                    pauseTimer();
                }                resetTimer();
                mAnswer.setText("");

                if(mUserLife <= 0){
                    Toast.makeText(Game.this, "Game Over", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(Game.this, Result.class);
                    intent.putExtra(SCORE_TAG, mUserScore);
                    startActivity(intent);
                    finish();
                }else{
                    gameContinue();
                }
            }
        });
    }

    @SuppressLint("SetTextI18n")
    public void gameContinue() {
        Log.d(TAG, "gameContinue: called");


        String[] operation = {"+", "-", "/", "*"};
        String op1 = operation[mRandom.nextInt(4)];
//        String op1 = "*";


        switch (op1) {
            case "+":
                getRandomOperands(op1);
                mSolution = mNum1 + mNum2;
                break;
            case "-":
                getRandomOperands(op1);
                mSolution = mNum1 - mNum2;
                break;
            case "/":
                getRandomOperands(op1);
                mSolution = mNum1 / mNum2;
                break;
            case "*":
                getRandomOperands(op1);
                mSolution = mNum1 * mNum2;
                break;
            default:
                break;


        }

        mQuestion.setText(mNum1 + " " + op1 + " " + mNum2);
        startTimer();
    }

    private void getRandomOperands(String operand) {

      switch (operand){
          case "+": case "-":
              mNum1 = mRandom.nextInt(100);
              mNum2 = mRandom.nextInt(100);
              break;
          case "/":
              getRandDivisionOperands();
              int i = 1;

              while (mNum1 % mNum2 != 0) {
                  Log.d(TAG, String.valueOf(i) + ": gameContinue(): mNum1 = " + mNum1 + " mNum2 = " + mNum2);
                  getRandDivisionOperands();
                  i++;
              }
              break;
          case"*":
                  mNum1 = mRandom.nextInt(13);
                  mNum2 = mRandom.nextInt(13);
              break;
      }

    }

    private void getRandDivisionOperands() {
        mNum1 = mRandom.nextInt(100 - 1) + 1;
        if (mNum1 > 1) {
            mNum2 = mRandom.nextInt(mNum1 - 1) + 1;
        } else {
            mNum2 = 1;
        }
    }

    public void startTimer() {
        mTimer = new CountDownTimer(time_left_in_millis, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                time_left_in_millis = millisUntilFinished;
                updateText();
            }

            @Override
            public void onFinish() {
                timer_running = false;
                pauseTimer();
                resetTimer();
                updateText();
                mUserLife -= 1;
                mLife.setText(String.valueOf(mUserLife));
                mQuestion.setText(R.string.time_up);
                Toast.makeText(Game.this, "Correct Answer: " + mSolution, Toast.LENGTH_SHORT).show();
            }
        }.start();

        timer_running = true;
    }

    private void pauseTimer() {
        mTimer.cancel();
        timer_running = false;

    }

    private void resetTimer() {
        time_left_in_millis = START_TIMER_IN_MILLIS;
        updateText();
    }

    private void updateText() {
        int second = (int) (time_left_in_millis / 1000) % 60;
        String time_left = String.format(Locale.getDefault(), "%02d", second);
        mTime.setText(time_left);
    }
}