package com.company.mathgame;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {


    Button mAddBtn, mSubBtn, mMultiBtn, mRandGameBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAddBtn = findViewById(R.id.buttonAdd);
        mSubBtn = findViewById(R.id.buttonSub);
        mMultiBtn = findViewById(R.id.buttonMult);
        mRandGameBtn = findViewById(R.id.buttonRandGame);

        mRandGameBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Game.class);
                startActivity(intent);
                finish();
            }
        });
    }
}