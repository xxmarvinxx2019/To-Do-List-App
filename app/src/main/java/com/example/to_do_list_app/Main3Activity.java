package com.example.to_do_list_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputEditText;

public class Main3Activity extends AppCompatActivity {

    Intent intent;
    TextView t,t2;
    String s,t1,i;
    TextInputEditText edit1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
        intent = getIntent();
        s = intent.getStringExtra("desc");
        t1 = intent.getStringExtra("date");
        i = intent.getStringExtra("time");
        t = findViewById(R.id.text1);
        t2 = findViewById(R.id.texts2);
        edit1 = findViewById(R.id.todo);
        edit1.setText(s);
        t.setText(t1);
        t2.setText(i);
    }
}
