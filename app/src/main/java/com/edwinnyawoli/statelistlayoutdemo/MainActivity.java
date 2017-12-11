package com.edwinnyawoli.statelistlayoutdemo;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import com.edwinnyawoli.statelistlayout.State;
import com.edwinnyawoli.statelistlayout.StateListLayout;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final StateListLayout stateListLayout = findViewById(R.id.state_list_layout);
        Handler handler = new Handler();

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                stateListLayout.setState(State.EMPTY);
            }
        }, 2000);
    }
}
