package com.humber.saynn.humbercalc;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Calculator extends AppCompatActivity {
    //Calculator calculator;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculator);
        //calculator = new Calculator();
    }

    protected void numberClicked(View v){
        TextView display = findViewById(R.id.tv1);
        int number = Integer.valueOf(((Button)v).getText().toString());
        display.setText(display.getText().toString() + number);
    }
}
