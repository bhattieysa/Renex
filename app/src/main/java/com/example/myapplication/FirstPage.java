package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class FirstPage extends AppCompatActivity {
TextView textView, textView1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.first_page);
        textView = findViewById(R.id.textView);
        textView1 = findViewById(R.id.textView2);
        getSupportActionBar().hide();

    }

    public void signup(View view) {

        startActivity(new Intent(getApplicationContext(),UserContract.class));

    }

    public void loginCC(View view) {
        startActivity(new Intent(getApplicationContext(),Login.class));
    }

}
