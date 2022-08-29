package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.google.android.material.checkbox.MaterialCheckBox;

import static android.view.View.VISIBLE;

public class UserContract extends AppCompatActivity {
    MaterialCheckBox checkBox;
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_contract);
        getSupportActionBar().hide();

        checkBox = findViewById(R.id.cb1);
        textView = findViewById(R.id.next);

        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
        {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
            {
                if ( isChecked )
                {
                    textView.setVisibility(VISIBLE);
                }
                else{
                    textView.setVisibility(View.GONE);
                }
            }
        });

        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),SignUp.class));
            }
        });
    }
}