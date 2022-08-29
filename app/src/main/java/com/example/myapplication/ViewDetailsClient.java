package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

public class ViewDetailsClient extends AppCompatActivity {
TextView jobtitle, jobdesc, joblocation, estimatedtime, estimatedbudget;
ImageView imageView1,imageView2,imageView3;
TextView pay;
    String jobid;
    String budget;

    boolean isImageFitToScreen;
    Bitmap bitmap;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_details_client);
        getSupportActionBar().hide();




        jobtitle = findViewById(R.id.etjobtitle);
        jobdesc = findViewById(R.id.etjobdesc);
        joblocation = findViewById(R.id.etjobloc);
        estimatedtime = findViewById(R.id.etjobtime);
        estimatedbudget = findViewById(R.id.etbudget);
        imageView1 = findViewById(R.id.addpic1);
        imageView2 = findViewById(R.id.addpic2);
        imageView3 = findViewById(R.id.addpic3);
        budget = estimatedbudget.getText().toString();
        pay = findViewById(R.id.Pay);
        pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),Payment.class);
                intent.putExtra("jobid", jobid);
                intent.putExtra("budget",budget);
                startActivity(intent);
              //  startActivity(new Intent(getApplicationContext(), Payment.class));

            }
        });



        Intent intent = getIntent();
        String title = intent.getStringExtra("JobTitle");
        jobtitle.setText(title);
        String desc = intent.getStringExtra("JobDesc");
        jobdesc.setText(desc);
        String loc = intent.getStringExtra("JobLoc");
        joblocation.setText(loc);
        String time = intent.getStringExtra("JobTime");
        estimatedtime.setText(time);
        budget = intent.getStringExtra("JobBudget");
        estimatedbudget.setText(budget);
         jobid = intent.getStringExtra("jobid");
        String url = intent.getStringExtra("imageurl");
        Glide.with(ViewDetailsClient.this).load(url).into(imageView1);
        String url2 = intent.getStringExtra("imageurl2");
        Glide.with(ViewDetailsClient.this).load(url2).into(imageView2);
        String url3 = intent.getStringExtra("imageurl3");
        Glide.with(ViewDetailsClient.this).load(url3).into(imageView3);







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

    public void showFragment(View view) {
        imageView1.setDrawingCacheEnabled(true);
        Bitmap bitmap = imageView1.getDrawingCache();
        Intent intent = new Intent(getApplicationContext(),FullScreenImage.class);
        intent.putExtra("Bitmap", bitmap);
        startActivity(intent);

    }


}