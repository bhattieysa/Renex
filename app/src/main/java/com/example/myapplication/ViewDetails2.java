package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

public class ViewDetails2 extends AppCompatActivity {
    TextView jobtitle, jobdesc, joblocation, estimatedtime, estimatedbudget;
    ImageView imageView1,imageView2,imageView3;
    String title,jobid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_details2);
        getSupportActionBar().hide();

        jobtitle = findViewById(R.id.etjobtitle);
        jobdesc = findViewById(R.id.etjobdesc);
        joblocation = findViewById(R.id.etjobloc);
        estimatedtime = findViewById(R.id.etjobtime);
        estimatedbudget = findViewById(R.id.etbudget);
        imageView1 = findViewById(R.id.addpic1);
        imageView2 = findViewById(R.id.addpic2);
        imageView3 = findViewById(R.id.addpic3);


        Intent intent = getIntent();
        title = intent.getStringExtra("JobTitle");
        jobtitle.setText(title);
        String desc = intent.getStringExtra("JobDesc");
        jobdesc.setText(desc);
        String loc = intent.getStringExtra("JobLoc");
        joblocation.setText(loc);
        String time = intent.getStringExtra("JobTime");
        estimatedtime.setText(time);
        String budget = intent.getStringExtra("JobBudget");
        estimatedbudget.setText(budget);
        jobid = intent.getStringExtra("Jobid");
        String url = intent.getStringExtra("imageurl");
        Glide.with(ViewDetails2.this).load(url).into(imageView1);
        String url2 = intent.getStringExtra("imageurl2");
        Glide.with(ViewDetails2.this).load(url2).into(imageView2);
        String url3 = intent.getStringExtra("imageurl3");
        Glide.with(ViewDetails2.this).load(url3).into(imageView3);



        imageView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageView1.setDrawingCacheEnabled(true);
                Bitmap bitmap = imageView1.getDrawingCache();
                Intent intent = new Intent(getApplicationContext(),FullScreenImage.class);
                intent.putExtra("Bitmap", bitmap);
                startActivity(intent);
            }

        });

        imageView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageView2.setDrawingCacheEnabled(true);
                Bitmap bitmap = imageView2.getDrawingCache();
                Intent intent = new Intent(getApplicationContext(),FullScreenImage.class);
                intent.putExtra("Bitmap", bitmap);
                startActivity(intent);
            }
        });

        imageView3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageView3.setDrawingCacheEnabled(true);
                Bitmap bitmap = imageView3.getDrawingCache();
                Intent intent = new Intent(getApplicationContext(),FullScreenImage.class);
                intent.putExtra("Bitmap", bitmap);
                startActivity(intent);
            }

        });
    }


    }
