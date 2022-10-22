package com.company.mathgame;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

public class Result extends AppCompatActivity {

    TextView mResult;
    Button mPlayAgainBtn, mExitBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        mResult = findViewById(R.id.score_txtvw_result_act);
        mPlayAgainBtn = findViewById(R.id.play_again_btn_result_act);
        mExitBtn = findViewById(R.id.exit_btn_result_act);

        Intent intent = getIntent();
        Integer score =  intent.getIntExtra(Game.SCORE_TAG, 0);
        mResult.setText(getString(R.string.score)+ " " + String.valueOf(score));


        mResult.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Result.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        mExitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }
}