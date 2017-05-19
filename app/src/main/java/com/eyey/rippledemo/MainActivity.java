package com.eyey.rippledemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.eyey.rippleview.HexagonalRippleView;

public class MainActivity extends AppCompatActivity {

    HexagonalRippleView view;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        view= (HexagonalRippleView) findViewById(R.id.hexagonalRippleView);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(view.isStart()) {
                    view.stop();
                }else{
                    view.start();
                }
            }
        });
    }
}
