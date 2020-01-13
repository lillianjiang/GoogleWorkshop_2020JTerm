/* Copyright 2016 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.google.engedu.ghost;

import android.content.res.AssetManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.util.Random;


public class GhostActivity extends AppCompatActivity {
    private static final String COMPUTER_TURN = "Computer's turn";
    private static final String USER_TURN = "Your turn";
    private GhostDictionary dictionary;
    private boolean userTurn = false;
    private Random random = new Random();
    private String fragment = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ghost);
        AssetManager assetManager = getAssets();
        try {
            InputStream is = assetManager.open("words.txt");
            dictionary = new SimpleDictionary(is);
        } catch (IOException e) {
            e.printStackTrace();
        }
        onStart(null);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_ghost, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * Handler for the "Reset" button.
     * Randomly determines whether the game starts with a user turn or a computer turn.
     * @param view
     * @return true
     */
    public boolean onStart(View view) {
        Button button = (Button) findViewById(R.id.button);
        button.setEnabled(true);
        userTurn = random.nextBoolean();
        fragment ="";
        TextView text = (TextView) findViewById(R.id.ghostText);
        text.setText("");
        TextView label = (TextView) findViewById(R.id.gameStatus);
        if (userTurn) {
            label.setText(USER_TURN);
        } else {
            Log.d("hya","computer first");
            label.setText(COMPUTER_TURN);
            computerTurn();
        }
        return true;
    }

    private void computerTurn() {
        TextView label = (TextView) findViewById(R.id.gameStatus);
        TextView window = (TextView) findViewById(R.id.ghostText);
        Button button = (Button) findViewById(R.id.button);
        // Do computer turn stuff then make it the user's turn again
        if(fragment.length() >=4 && dictionary.isWord(fragment)){
            label.setText("Computer Win. It is a vaild word.");
            button.setEnabled(false);
        }else{
           // Log.d("hya","1");
            String remaining = dictionary.getAnyWordStartingWith(fragment);
           // Log.d("fragment1",fragment);
            if(remaining == null){
              //  Log.d("hya","computer win because it is not a word");
                label.setText("Challenged. Computer Win.");
                button.setEnabled(false);
            }else{
                Log.d("hya","3");
                fragment += remaining.substring(fragment.length(),fragment.length()+1);
               // Log.d("fragment",fragment);
                window.setText(fragment);
                userTurn = true;
                label.setText(USER_TURN);
            }
        }

    }

    /**
     * Handler for user key presses.
     * @param keyCode
     * @param event
     * @return whether the key stroke was handled.
     */
    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        TextView label = (TextView) findViewById(R.id.gameStatus);
        TextView window = (TextView) findViewById(R.id.ghostText);
        char unicodeChar = (char)event.getUnicodeChar();
        if(Character.isLetter(unicodeChar)){
            fragment += unicodeChar;
            window.setText(fragment);
            computerTurn();
            return true;
        }
        return super.onKeyUp(keyCode, event);
    }

    public void challenge(View view){
        TextView label = (TextView) findViewById(R.id.gameStatus);
        Button button = (Button) findViewById(R.id.button);
        if(fragment.length()>=4 && dictionary.isWord(fragment)){
            label.setText("You Win.");
        }else if(dictionary.getAnyWordStartingWith(fragment) != null){
            label.setText("Computer Wins. "+dictionary.getAnyWordStartingWith(fragment));
        }
        if(dictionary.getAnyWordStartingWith(fragment) == null){
            label.setText("You win.");
        }
        button.setEnabled(false);
    }
}
