package com.example.to_do_list_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    Button next;
    SharedPreferences sharedPreferences;
    Boolean firstTime;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        next = findViewById(R.id.next);

        sharedPreferences = getSharedPreferences("MyPrefs",MODE_PRIVATE);
        firstTime = sharedPreferences.getBoolean("firstTime",true);

        if(firstTime){
            next.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    firstTime = false;
                    editor.putBoolean("firstTime", firstTime);
                    editor.apply();
                    Intent intent = new Intent(MainActivity.this,Main2Activity.class);
                    startActivity(intent);
                    finish();
                }
            });
        }else{
            Intent intent = new Intent(MainActivity.this,Main2Activity.class);
            startActivity(intent);
            finish();
        }

    }
}
