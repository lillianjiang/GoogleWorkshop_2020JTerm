package com.example.scarnesdice;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity {
    private int USER_OVERALL_SCORE;
    private int USER_TURN_SCORE;
    private int COMP_OVERALL_SCORE;
    private int COMP_TURN_SCORE;

    Button roll;
    ImageView img;
    Button hold;
    Button reset;
    TextView userturn;
    TextView usertotal;
    TextView compturn;
    TextView comptotal;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        img = (ImageView) findViewById(R.id.dice);
        userturn = (TextView) findViewById(R.id.textView8);
        usertotal = (TextView) findViewById(R.id.textView);
        compturn = (TextView) findViewById(R.id.textView9);
        comptotal = (TextView) findViewById(R.id.textView2);
        roll = (Button) findViewById(R.id.button);
        reset = (Button) findViewById(R.id.button3);
        hold = (Button) findViewById(R.id.button2);



        roll.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        int rand = handleClick();
                                        if (rand == 1){
                                            USER_TURN_SCORE = 0;
                                            userturn.setText("Your Turn:"+USER_TURN_SCORE);
                                            computerTurn();
                                        }
                                        else if (rand == 2) {
                                            USER_TURN_SCORE += 2;
                                            userturn.setText("Your Turn:"+USER_TURN_SCORE);
                                        }
                                        else if (rand == 3){
                                            USER_TURN_SCORE += 3;
                                            userturn.setText("Your Turn:"+USER_TURN_SCORE);
                                        }
                                        else if (rand == 4) {
                                            USER_TURN_SCORE += 4;
                                            userturn.setText("Your Turn:"+USER_TURN_SCORE);
                                        }
                                        else if (rand == 5){
                                            USER_TURN_SCORE += 5;
                                            img.setImageResource(R.drawable.dice5);
                                            userturn.setText("Your Turn:"+USER_TURN_SCORE);
                                        }
                                        else{
                                            USER_TURN_SCORE += 6;
                                            userturn.setText("Your Turn:"+USER_TURN_SCORE);
                                        }

                                    }
                                });


        reset.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                roll.setEnabled(true);
                hold.setEnabled(true);
                USER_TURN_SCORE = 0;
                USER_OVERALL_SCORE = 0;
                COMP_TURN_SCORE = 0;
                COMP_OVERALL_SCORE = 0;
                userturn.setText("Your Turn:0");
                usertotal.setText("Your Score:0");
                compturn.setText("Computer's Turn: 0");
                comptotal.setText("Computer's Score:0");

            }
        });

        hold.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                USER_OVERALL_SCORE += USER_TURN_SCORE;
                USER_TURN_SCORE = 0;
                usertotal.setText("Your Score:"+USER_OVERALL_SCORE);
                userturn.setText("Your Turn:"+USER_TURN_SCORE);
                computerTurn();
            }
        });

        }

    public int handleClick(){
        int rand = (int) (Math.random() * 6 + 1);
        if (rand == 1){
            img.setImageResource(R.drawable.dice1);
        }
        else if (rand == 2) {
            img.setImageResource(R.drawable.dice2);
        }
        else if (rand == 3){
            img.setImageResource(R.drawable.dice3);
        }
        else if (rand == 4) {
            img.setImageResource(R.drawable.dice4);
        }
        else if (rand == 5){
            img.setImageResource(R.drawable.dice5);
        }
        else{
            img.setImageResource(R.drawable.dice6);
        }
        return rand;
    }


    Handler myHandler = new Handler();
    Runnable myRunnable = new Runnable() {
        @Override
        public void run() {
            computerTurn();
        }
    };


    public void computerTurn(){
        roll.setEnabled(false);
        hold.setEnabled(false);
        int rand = -1;
        while(COMP_TURN_SCORE < 20 ){
            rand = handleClick();
            if (rand == 1){
                COMP_TURN_SCORE = 0;
                compturn.setText("Computer rolled a one");
                roll.setEnabled(true);
                hold.setEnabled(true);
                return;
            }
            else if (rand == 2) {
                COMP_TURN_SCORE += 2;
                compturn.setText("Computer's Turn:"+COMP_TURN_SCORE);
            }
            else if (rand == 3){
                COMP_TURN_SCORE += 3;
                compturn.setText("Computer's Turn:"+COMP_TURN_SCORE);
            }
            else if (rand == 4) {
                COMP_TURN_SCORE += 4;
                compturn.setText("Computer's Turn:"+COMP_TURN_SCORE);
            }
            else if (rand == 5){
                COMP_TURN_SCORE += 5;
                img.setImageResource(R.drawable.dice5);
                compturn.setText("Computer's Turn:"+COMP_TURN_SCORE);
            }
            else{
                COMP_TURN_SCORE += 6;
                compturn.setText("Computer's Turn:"+COMP_TURN_SCORE);
            }
        COMP_OVERALL_SCORE += COMP_TURN_SCORE;
        compturn.setText("Computer holds.");
        comptotal.setText("Computer's Score:"+COMP_OVERALL_SCORE);
            roll.setEnabled(true);
            hold.setEnabled(true);
        return;


        }
    }


}

