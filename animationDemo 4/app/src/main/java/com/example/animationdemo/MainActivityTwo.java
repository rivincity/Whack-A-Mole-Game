package com.example.animationdemo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivityTwo extends AppCompatActivity {
    Button playAgain;
    TextView score;
    TextView accuracy;
    TextView finalStatement;
    TextView angels;
    TextView penalties;
    TextView text6;
    String total;
    String clicked;
    String bonus;
    String minus;
    String molesMissed;
    String finalScore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_two);
        playAgain = findViewById(R.id.id_main2_button1);
        score = findViewById(R.id.id_main2_text1);
        accuracy = findViewById(R.id.id_main2_text2);
        finalStatement = findViewById(R.id.id_main2_text3);
        angels = findViewById(R.id.id_main2_text4);
        penalties = findViewById(R.id.id_main2_text5);
        text6 = findViewById(R.id.id_main2_text6);
        clicked = getIntent().getStringExtra(MainActivity.intentKey);
        Log.d("clicked", clicked);
        score.setTextColor(Color.WHITE);
        accuracy.setTextColor(Color.WHITE);
        finalStatement.setTextColor(Color.WHITE);
        angels.setTextColor(Color.WHITE);
        penalties.setTextColor(Color.WHITE);
        text6.setTextColor(Color.WHITE);
        finalScore = getIntent().getStringExtra(MainActivity.intentKey6);
        score.setText("Final Score: " + finalScore);
        total = getIntent().getStringExtra(MainActivity.intentKey2);
        bonus = getIntent().getStringExtra(MainActivity.intentKey3);
        minus = getIntent().getStringExtra(MainActivity.intentKey4);
        molesMissed = getIntent().getStringExtra(MainActivity.intentKey5);
        Log.d("total", total);
        if(Integer.valueOf(total)==0)
            accuracy.setText("Accuracy: 0%");
        else
            accuracy.setText("Accuracy: " + Math.round(Double.valueOf(clicked)/Double.valueOf(total)*100) + " %");
        finalStatement.setText("You Clicked " + clicked + " out of " + total + " Moles");
        angels.setText("You Earned " + Integer.valueOf(bonus)*5 + " Extra Seconds");
        penalties.setText("You Lost " + Integer.valueOf(minus)*5 + " Points");
        text6.setText("You Missed " + molesMissed + " Moles");

        playAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent sendInfoBack = new Intent(MainActivityTwo.this, MainActivity.class);
                startActivity(sendInfoBack);
            }
        });

    }
}