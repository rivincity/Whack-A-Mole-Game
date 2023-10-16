package com.example.animationdemo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;


import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.atomic.AtomicInteger;


public class MainActivity extends AppCompatActivity {
    ConstraintLayout layout;
    TextView textView;
    TextView textView2;
    Button play;
    float vertical;
    float horizontal;
    AtomicInteger score = new AtomicInteger(0);
    AtomicInteger totalMoles = new AtomicInteger(0);
    AtomicInteger seconds = new AtomicInteger(60);
    AtomicInteger addNew = new AtomicInteger(0);
    int random;
    int randomBonus;
    int randomPenalty;
    boolean angel;
    boolean penalty;
    int randomSelection;
    int aClicked;
    int pClicked;
    int missed;
    boolean firstImage = true;
    boolean firstRow = true;
    ImageView imageViews [] = new ImageView[9];
    Boolean notClicked[] = new Boolean[9];
    static final String intentKey = "ABCD";
    static final String intentKey2 = "DCBA";
    static final String intentKey3 = "DCBAABCD";
    static final String intentKey4 = "DCBADCBA";
    static final String intentKey5 = "XYZWXYZWXYZ";
    static final String intentKey6 = "GHIFH";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = findViewById(R.id.id_timer);
        textView2 = findViewById(R.id.id_score);
        layout = findViewById(R.id.id_layout);
        play = findViewById(R.id.id_start);
        layout = findViewById(R.id.id_layout);
        imageViews[0] = findViewById(R.id.id_vold1);
        imageViews[1] = findViewById(R.id.id_vold2);
        imageViews[2] = findViewById(R.id.id_vold3);
        imageViews[3] = findViewById(R.id.id_vold4);
        imageViews[4] = findViewById(R.id.id_vold5);
        imageViews[5] = findViewById(R.id.id_vold6);
        imageViews[6] = findViewById(R.id.id_vold7);
        imageViews[7] = findViewById(R.id.id_vold8);
        imageViews[8] = findViewById(R.id.id_vold9);
        textView.setText("Time: " + (seconds.get()));
        textView2.setText(("Score: " + score + "   Missed: " + missed));
        ScaleAnimation startPos = new ScaleAnimation(1.0f, 0f, 1.0f, 0f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        startPos.setDuration(100);
        startPos.setFillAfter(true);
        for(int i = 0; i < imageViews.length;i++)
        {
            int x = i;
            imageViews[i].setEnabled(false);
            imageViews[i].startAnimation(startPos);
            imageViews[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(!angel && !penalty)
                    {
                        addNew.set(addNew.get()+1);
                        addImage();
                    }
                    ScaleAnimation zoomOut = new ScaleAnimation(1.0f, 0f, 1.0f, 0f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
                    zoomOut.setDuration(1000);
                    zoomOut.setFillAfter(true);
                    imageViews[x].setEnabled(false);
                    imageViews[x].startAnimation(zoomOut);
                    imageViews[x].setImageResource(R.drawable.voldemortreal);
                    notClicked[x] = false;
                    if(angel)
                    {
                        seconds.set(seconds.get()+5);
                        textView.setText("Time: " + seconds.get());
                        Toast.makeText(MainActivity.this, "You earned 5 seconds", Toast.LENGTH_SHORT).show();
                        aClicked++;
                    }
                    if(penalty)
                    {
                        score.set(score.get()-5);
                        Toast.makeText(MainActivity.this, "You lost 5 points", Toast.LENGTH_SHORT).show();
                        pClicked++;
                    }
                    textView2.setText("Score: " + score + "   Missed: " + missed);
                }
            });
        }
        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Timer timer = new Timer();
                MyTimerTask timer1 = new MyTimerTask();
                timer.schedule(timer1, 1000, 1000);
                play.setVisibility(View.GONE);
            }
        });
    }
    public class MyTimerTask extends  TimerTask{

        @Override
        public void run() {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    textView.setText("Time: " + String.valueOf(seconds.get()));
                }
            });
            if(seconds.get()==0)
            {
                gameOver();
                cancel();
            }
            else
            {
                if(seconds.get()%4==0 && seconds.get() >= 4) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            for(int i = 0; i < imageViews.length;i++)
                            {
                                imageViews[i].setImageResource(R.drawable.voldemortgood);
                            }
                            angel = false;
                            penalty = false;
                            randomSelection = (int)(Math.random()*9)+1;
                            randomBonus = (int)(Math.random()*9)+1;
                            randomPenalty = (int)(Math.random()*9)+1;
                            Log.d("randoms", randomSelection + " " + randomBonus + " " + randomPenalty);
                            if(randomBonus ==randomSelection)
                                angel = true;
                            if(randomPenalty ==randomSelection && !angel)
                                penalty = true;

                            if(angel)
                            {
                                imageViews[randomBonus-1].setImageResource(R.drawable.harrypotter);
                                Mole(imageViews[randomBonus-1], randomBonus-1);
                            }
                            else if(penalty)
                            {
                                imageViews[randomPenalty-1].setImageResource(R.drawable.black);
                                Mole(imageViews[randomPenalty-1], randomPenalty-1);
                            }
                            else
                            {
                                int randomFirst;
                                int randomSecond;
                                int randomThird;
                                randomFirst = (int)(Math.random()*8);
                                randomSecond = (int)(Math.random()*8);
                                randomThird = (int)(Math.random()*8);
                                if(randomFirst==randomSecond)
                                {
                                    while(randomFirst==randomSecond)
                                        randomSecond = (int)(Math.random()*8);
                                }
                                if(randomFirst==randomThird)
                                {
                                    while(randomFirst==randomThird)
                                        randomThird = (int)(Math.random()*8);
                                }
                                if(randomSecond==randomThird)
                                {
                                    while(randomSecond==randomThird)
                                        randomSecond = (int)(Math.random()*8);
                                }
                                Log.d("randoms 2", randomFirst + " " + randomSecond + " " + randomThird);
                                Handler handler = new Handler();
                                handler.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        Mole(imageViews[randomFirst], randomFirst);
                                    }
                                    },600);
                                if(randomSecond!=randomFirst)
                                    Mole(imageViews[randomSecond], randomSecond);
                                Handler handler2 = new Handler();
                                handler2.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                    }
                                },600);
                                if(randomThird!=randomSecond && randomThird !=randomFirst)
                                    Mole(imageViews[randomThird], randomThird);
                            }
                        }
                    });
                }
            }
            seconds.set(seconds.get()-1);
        }
    }
    public void addImage()
    {
        ImageView image = new ImageView(MainActivity.this);
        image.setId(View.generateViewId());
        image.setImageResource(R.drawable.voldemortgood);
        ConstraintLayout.LayoutParams params = new ConstraintLayout.LayoutParams(100, 100);
        image.setLayoutParams(params);
        layout.addView(image);
        ConstraintSet constraintSet = new ConstraintSet();
        constraintSet.clone(layout);
        constraintSet.connect(image.getId(), ConstraintSet.TOP, layout.getId(), ConstraintSet.TOP);
        constraintSet.connect(image.getId(), ConstraintSet.BOTTOM, layout.getId(), ConstraintSet.BOTTOM);
        constraintSet.connect(image.getId(), ConstraintSet.LEFT, layout.getId(), ConstraintSet.LEFT);
        constraintSet.connect(image.getId(), ConstraintSet.RIGHT, layout.getId(), ConstraintSet.RIGHT);
        if(firstImage)
            horizontal = (float) 0.4;
        if(firstRow && addNew.get()==7)
            firstRow = false;
        score.set(score.get()+1);
        firstImage= false;
        constraintSet.setVerticalBias(image.getId(), vertical);
        constraintSet.setHorizontalBias(image.getId(), horizontal);
        constraintSet.applyTo(layout);
        horizontal+=0.1;
        if(!firstRow && addNew.get()%11-7==0){
            vertical+=0.1;
            horizontal = 0;
        }
    }
    public void gameOver()
    {
        ScaleAnimation finishGame = new ScaleAnimation(0.5f, 0f, 0.5f, 0f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        finishGame.setDuration(10);
        finishGame.setFillAfter(true);
        for(int i = 0; i < imageViews.length; i++)
        {
            imageViews[i].setEnabled(false);
            imageViews[i].startAnimation(finishGame);
        }
        runOnUiThread(new Runnable() {
            @Override
            public void run() {

                Intent intent = new Intent(MainActivity.this, MainActivityTwo.class);
                intent.putExtra(intentKey, String.valueOf(addNew));
                intent.putExtra(intentKey2, String.valueOf(totalMoles));
                intent.putExtra(intentKey3, String.valueOf(aClicked));
                intent.putExtra(intentKey4, String.valueOf(pClicked));
                intent.putExtra(intentKey5, String.valueOf(missed));
                intent.putExtra(intentKey6, String.valueOf(score));
                startActivity(intent);
                textView.setTextColor(Color.RED);
                textView.setText("Time is up!");
            }
        });
    }
    public void Mole(@NonNull ImageView imageView, int i)
    {
        random = (int)(Math.random()*2)+1;
        ScaleAnimation zoomIn = new ScaleAnimation(0f, 1.0f, 0f, 1.0f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        zoomIn.setDuration((random*1000));
        zoomIn.setFillAfter(true);
        imageView.startAnimation(zoomIn);
        imageView.setEnabled(true);
        notClicked[i] = true;
        if(!angel && !penalty)
            totalMoles.set(totalMoles.get()+1);
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if(notClicked[i])
                {
                    imageView.setImageResource(R.drawable.voldemortreal);
                    ScaleAnimation zoomOut = new ScaleAnimation(1.0f, 0f, 1.0f, 0f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
                    zoomOut.setDuration(1000);
                    zoomOut.setFillAfter(true);
                    imageView.setEnabled(false);
                    imageView.startAnimation(zoomOut);
                    if(!angel && !penalty)
                        missed++;
                    textView2.setText(("Score: " + score + "   Missed: " + missed));
                }
                Log.d("notClicked", String.valueOf(notClicked[i]));
            }
        }, random*1000+600);
    }
}
