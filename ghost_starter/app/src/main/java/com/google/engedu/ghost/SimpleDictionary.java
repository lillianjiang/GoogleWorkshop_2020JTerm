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


import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Random;

public class SimpleDictionary implements GhostDictionary {
    private ArrayList<String> words;

    public SimpleDictionary(InputStream wordListStream) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(wordListStream));
        words = new ArrayList<>();
        String line = null;
        while((line = in.readLine()) != null) {
            String word = line.trim();
            if (word.length() >= MIN_WORD_LENGTH)
              words.add(line.trim());
        }
    }

    @Override
    public boolean isWord(String word) {
        return words.contains(word);
    }

    @Override
    public String getAnyWordStartingWith(String prefix) {
        int length = prefix.length();
        if(prefix.length() == 0){
            Random r = new Random(System.currentTimeMillis());
            int rand = r.nextInt(words.size()-1);
            return words.get(rand);
        }else{
            Log.d("hya","binarysearch");
            int lo = 0;
            int hi = words.size();
            int mid;
            while(lo<=hi){
                mid = (lo+hi)/2;
             //   Log.d("hya","going bs");
                if(words.get(mid).indexOf(prefix) == 0)
                    return words.get(mid);
                if(prefix.compareTo(words.get(mid)) < 0)
                    hi = mid-1;
                else if(prefix.compareTo(words.get(mid)) > 0)
                    lo = mid+1;
            }
           // Log.d("hya","should return null");
            return null;
        }
    }

    @Override
    public String getGoodWordStartingWith(String prefix) {
        String selected = null;
        return selected;
    }

}
