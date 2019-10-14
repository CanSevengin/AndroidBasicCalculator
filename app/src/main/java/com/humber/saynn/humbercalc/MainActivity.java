package com.humber.saynn.humbercalc;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    TextView tv1;
    TextView tv2;
    Calculator calculator;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv1 = (TextView) findViewById(R.id.tv1);
        tv2 = (TextView) findViewById(R.id.tv2);
        calculator = new Calculator();
    }

    public void numberClicked(View v){
        calculator.numberClicked(((Button)v).getText().toString());
        updateDisplay();
    }

    public void updateDisplay(){
        tv1.setText(calculator.getHistoryString());
        tv2.setText(calculator.getNumberString());
    }
}
