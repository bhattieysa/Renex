package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.PointF;
import android.os.Bundle;
import android.util.FloatMath;
import android.util.Log;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import static android.app.usage.UsageEvents.Event.NONE;

public class FullScreenImage extends AppCompatActivity {
    ImageView imageView;
    private ScaleGestureDetector mScaleGestureDetector;
    private float mScaleFactor = 1.0f;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_screen_image);
        imageView = findViewById(R.id.fullimage);
        mScaleGestureDetector = new ScaleGestureDetector(this, new ScaleListener());
        getSupportActionBar().hide();

        Bitmap bitmap = getIntent().getParcelableExtra("Bitmap");
        imageView.setImageBitmap(bitmap);

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return mScaleGestureDetector.onTouchEvent(event);
    }

    private class ScaleListener extends ScaleGestureDetector.SimpleOnScaleGestureListener {

        // when a scale gesture is detected, use it to resize the image
        @Override
        public boolean onScale(ScaleGestureDetector scaleGestureDetector){
            mScaleFactor *= scaleGestureDetector.getScaleFactor();
            imageView.setScaleX(mScaleFactor);
            imageView.setScaleY(mScaleFactor);
            return true;
        }
    }
}