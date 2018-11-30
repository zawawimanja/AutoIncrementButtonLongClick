/*
 * Copyright (C) 2018 Google Inc.
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

package com.example.android.hellotoast;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Displays two Buttons and a TextView.
 * - Pressing the TOAST button shows a Toast.
 * - Pressing the COUNT button increases the displayed mCount.
 *
 * Note: Fixing behavior when device is rotated is a challenge exercise for the student.
 */

public class MainActivity extends AppCompatActivity {

    private Handler repeatUpdateHandler = new Handler();
    private boolean mAutoIncrement = false;
    private boolean mAutoDecrement = false;
    private int mCount ;
    private TextView mShowCount;
    Button btn;
    private final long REPEAT_DELAY = 50;
    private final int MINIMUM = 0;
    private final int MAXIMUM = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mShowCount = (TextView) findViewById(R.id.show_count);
        btn=(Button)findViewById(R.id.button_count);
        String b="10,20";
        String c[]=b.split(",");
        String number = c[0];
        mCount = Integer.parseInt(number);

        class RptUpdater implements Runnable {
            public void run() {
                if( mAutoIncrement ){
                    increment();
                    repeatUpdateHandler.postDelayed( new RptUpdater(), REPEAT_DELAY );

                }
            }
        }


        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               increment();
            }
        });


        btn.setOnLongClickListener(
                new View.OnLongClickListener(){
                    public boolean onLongClick(View arg0) {
                        mAutoIncrement = true;
                        repeatUpdateHandler.post( new RptUpdater() );
                        return false;
                    }
                }
        );

        // When the button is released, if we're auto incrementing, stop
       btn.setOnTouchListener( new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                if( event.getAction() == MotionEvent.ACTION_UP && mAutoIncrement ){
                    mAutoIncrement = false;
                }
                return false;
            }
        });

    }

    /*
    * Shows a Toast when the TOAST button is clicked.
    *
    * @param view The view that triggered this onClick handler.
    *             Since a toast always shows on the top,
    *             the passed in view is not used.
    */
    public void showToast(View view) {
        Toast toast = Toast.makeText(this, R.string.toast_message,
                Toast.LENGTH_SHORT);
        toast.show();
    }



    public void increment(){
        if(  mCount < MAXIMUM ){
             mCount++;
            mShowCount.setText( ""+mCount );
        }
    }

}
