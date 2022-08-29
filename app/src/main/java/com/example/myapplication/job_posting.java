package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.StringRequest;
import com.bumptech.glide.Glide;
import com.google.android.material.snackbar.Snackbar;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class job_posting extends AppCompatActivity {
   Uri filepath;

    ConstraintLayout constraintLayout;
    EditText jobtitle, jobdesc, joblocation, estimatedtime, estimatedbudget;
    String title, desc, location, time, budget;
    TextView postJob;
    ProgressDialog progressDialog;
    ImageView imageView1, imageView2, imageView3;
    Bitmap bitmaps, bitmap2, bitmap3;
    private static int RESULT_LOAD_IMG;
    private static final int REQUEST_ONE = 1;
    private static final int REQUEST_TWO = 2;
    UserInfo userInfo;
    private static final int REQUEST_THREE = 3;
    String u_id;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.job_posting);
        constraintLayout = findViewById(R.id.constraintlayout);
        getSupportActionBar().hide();

        progressDialog = new ProgressDialog(job_posting.this);
        progressDialog.setMessage("Loading...Please wait");
userInfo=new UserInfo(this);
u_id=userInfo.getKeyId();

        jobtitle = findViewById(R.id.etjobtitle);
        jobdesc = findViewById(R.id.etjobdesc);
        joblocation = findViewById(R.id.etjobloc);
        estimatedtime = findViewById(R.id.etjobtime);
        estimatedbudget = findViewById(R.id.etbudget);
        postJob = findViewById(R.id.postJob);

        imageView1 = findViewById(R.id.addpic1);
        imageView2 = findViewById(R.id.addpic2);
        imageView3 = findViewById(R.id.addpic3);





        imageView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
                photoPickerIntent.setType("image/*");
                startActivityForResult(photoPickerIntent, job_posting.REQUEST_ONE);
            }
        });

        imageView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
                photoPickerIntent.setType("image/*");
                startActivityForResult(photoPickerIntent, job_posting.REQUEST_TWO);
            }
        });

        imageView3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
                photoPickerIntent.setType("image/*");
                startActivityForResult(photoPickerIntent, job_posting.REQUEST_THREE);
            }
        });


        postJob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                title = jobtitle.getText().toString();
                desc = jobdesc.getText().toString();
                location = joblocation.getText().toString();
                time = estimatedtime.getText().toString();
                budget = estimatedbudget.getText().toString();



                if (TextUtils.isEmpty(title)) {
                    jobtitle.setError("Please Give Job Title");
                    return;
                }

                if (TextUtils.isEmpty(desc)) {
                    jobdesc.setError("Please Give Job Description");
                    return;
                }

                if (TextUtils.isEmpty(location)) {
                    joblocation.setError("Please Give Job Location");
                    return;
                }

                if (TextUtils.isEmpty(time)) {
                    estimatedtime.setError("Please Give Estimated Time");
                    return;
                }

                if (TextUtils.isEmpty(budget)) {
                    estimatedbudget.setError("Please Give Estimated Budget");
                    return;
                }
                if (imageView1.getDrawable()==null){
                    progressDialog.dismiss();
                }

                if (imageView2.getDrawable()==null){
                    progressDialog.dismiss();
                }

                if (imageView3.getDrawable()==null){
                    progressDialog.dismiss();
                }

                progressDialog.show();

                StringRequest stringRequest = new StringRequest(Request.Method.POST,


                        Utils.job_posting, new Response.Listener<String>() {


                    @Override
                    public void onResponse(String response) {
                        Log.d("testerror", response);
                        try {
                            JSONObject jObj = new JSONObject(response);
                            boolean error = jObj.getBoolean("error");


                            if (!error) {
                                progressDialog.hide();
                                Toast.makeText(job_posting.this, "Successfull", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(job_posting.this,MyProjectsClient.class));


                            } else {
                                // Error in login. Get the error message
                                progressDialog.hide();

                                Toast.makeText(job_posting.this, " Failed", Toast.LENGTH_SHORT).show();

                            }
                        } catch (JSONException e) {
                            progressDialog.hide();
                            // JSON error
                            e.printStackTrace();


                            Toast.makeText(job_posting.this, "Internet Error"+e.getMessage(), Toast.LENGTH_SHORT).show();
                        }


                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.hide();
                        //Log.e(TAG, "Login Error: " + error.getMessage());

                        Toast.makeText(job_posting.this,error.getMessage(), Toast.LENGTH_SHORT).show();


                    }
                }) {

                    @Override
                    protected Map<String, String> getParams() {


                        // Posting parameters to login url


                        Map<String, String> params = new HashMap<>();

                        params.put("jobtitle", title);
                        params.put("jobdesc", desc);
                        params.put("jobloc", location);
                        params.put("jobtime", time);
                        params.put("jobbudget", budget);
                        params.put("image" , imageToString(bitmaps));
                        params.put("image2", imageToString1(bitmap2));
                        params.put("image3", imageToString2(bitmap3));
                        params.put("user_id", u_id);


                        return params;


                    }

                };

                // Adding request to request queue
                Singleton.getInstance(job_posting.this).addToRequestQueue(stringRequest);
            }
        });
    }

         //First Image to string
       private String imageToString(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
        byte[] imageBytes = byteArrayOutputStream.toByteArray();
        return Base64.encodeToString(imageBytes, Base64.DEFAULT);
        }



         //Second Image To String
       private String imageToString1(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
        byte[] imageBytes = byteArrayOutputStream.toByteArray();
        return Base64.encodeToString(imageBytes, Base64.DEFAULT);
         }


         //Third Image to String
         private String imageToString2(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
        byte[] imageBytes = byteArrayOutputStream.toByteArray();
        return Base64.encodeToString(imageBytes, Base64.DEFAULT);
        }



        //Showing Images In The Image VIews
        @Override
        protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
            super.onActivityResult(requestCode, resultCode, data);


            //Getting And Setting First Image to ImageView
            if (requestCode == 1) {
                if (resultCode == RESULT_OK) {
                    try {
                        filepath = data.getData();
                        final InputStream imageStream = getContentResolver()
                                .openInputStream(filepath);
                        bitmaps = BitmapFactory.decodeStream(imageStream);
                        imageView1.setImageBitmap(bitmaps);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                        Toast.makeText(job_posting.this, "Something went wrong", Toast.LENGTH_LONG).show();
                    }
                }
                }



                     //Getting And Setting Second Image to ImageView
                 if (requestCode == 2) {
                 if (resultCode == RESULT_OK) {
                    if (resultCode == RESULT_OK) {
                        try {
                            filepath = data.getData();
                            final InputStream imageStream = getContentResolver()
                                    .openInputStream(filepath);
                            bitmap2 = BitmapFactory.decodeStream(imageStream);
                            imageView2.setImageBitmap(bitmap2);
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                            Toast.makeText(job_posting.this, "Something went wrong", Toast.LENGTH_LONG).show();
                        }
                    }
                }
                }



               //Getting And Setting Third Image to ImageView
                if (requestCode == 3) {
                    if (resultCode == RESULT_OK) {
                        if (resultCode == RESULT_OK) {
                            try {
                                filepath = data.getData();
                                final InputStream imageStream = getContentResolver()
                                        .openInputStream(filepath);
                                bitmap3 = BitmapFactory.decodeStream(imageStream);
                                imageView3.setImageBitmap(bitmap3);
                            } catch (FileNotFoundException e) {
                                e.printStackTrace();
                                Toast.makeText(job_posting.this, "Something went wrong", Toast.LENGTH_LONG).show();
                            }
                        }

                    }
                    }

        }
    }










