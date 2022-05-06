package com.example.finalapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.util.ArrayList;


public class MainActivity4 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main4);
    }


    public void avgfund (View V){

        EditText a = (EditText) findViewById(R.id.editText);
        EditText b = (EditText) findViewById(R.id.editText1);
        EditText c = (EditText) findViewById(R.id.editText2);

        TextView t = (TextView) findViewById(R.id.textView8);

        Button calb = (Button) findViewById(R.id.buttonCalc);

        int num1 = Integer.parseInt(a.getText().toString());
        int num2 = Integer.parseInt(b.getText().toString());
        int num3 = Integer.parseInt(c.getText().toString());

        float avg = (num1 + num2 + num3) / 3;


        t.setText(Float.toString(avg));

    }
}