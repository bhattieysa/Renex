package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.bumptech.glide.Glide;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ViewDetailsContractors extends AppCompatActivity {
    TextView jobtitle, jobdesc, joblocation, estimatedtime, estimatedbudget,takelead;
    ImageView imageView1,imageView2,imageView3;
    String jobid;
    String contractorid;
    String title;
    String paypalid;
    ConractorInfo conractorInfo;
    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_details_contractors);
        getSupportActionBar().hide();
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...Please Wait");



        conractorInfo=new ConractorInfo(this);
        contractorid=conractorInfo.getKeyId();




        jobtitle = findViewById(R.id.etjobtitle);
        jobdesc = findViewById(R.id.etjobdesc);
        joblocation = findViewById(R.id.etjobloc);
        estimatedtime = findViewById(R.id.etjobtime);
        estimatedbudget = findViewById(R.id.etbudget);
        imageView1 = findViewById(R.id.addpic1);
        imageView2 = findViewById(R.id.addpic2);
        imageView3 = findViewById(R.id.addpic3);
        takelead = findViewById(R.id.takelead);
        takelead.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                progressDialog.show();

                StringRequest stringRequest=new StringRequest( Request.Method.POST ,


                        Utils.projectsContractors, new Response.Listener <String> () {


                    @Override
                    public void onResponse(String response) {
                        Log.d("testerror",response);
                        try {
                            JSONObject jObj = new JSONObject(response);
                            boolean error = jObj.getBoolean("error");
                            String error_msg = jObj.getString("msg");


                            if (!error) {

                                progressDialog.hide();

                                if(error_msg.equals("User Exist")){

                                    progressDialog.dismiss();

                                }else {

                                    progressDialog.dismiss();



                                    Toast.makeText(ViewDetailsContractors.this, "Project Assigned Successfully", Toast.LENGTH_SHORT).show();

                                }
                            } else {
                                // Error in login. Get the error message
                                progressDialog.hide();

                                Toast.makeText ( ViewDetailsContractors.this,"Project Assigned Failed",Toast.LENGTH_SHORT ).show ();

                            }
                        } catch (JSONException e) {
                            progressDialog.hide();
                            // JSON error
                            e.printStackTrace();


                            Toast.makeText (ViewDetailsContractors.this,"Internet Error"+e.getMessage(),Toast.LENGTH_SHORT ).show ();
                        }



                    }
                } , new Response.ErrorListener () {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.hide();
                        //Log.e(TAG, "Login Error: " + error.getMessage());

                        Toast.makeText (ViewDetailsContractors.this,error.getMessage(),Toast.LENGTH_SHORT ).show ();


                    }
                }) {

                    @Override
                    protected Map<String, String> getParams() {


                        // Posting parameters to login url


                        Map<String, String> params = new HashMap<>();

                        params.put("job_id", jobid);
                        params.put("contractor_id", contractorid);
                        params.put("job_title", title);




                        return params;


                    }

                };

                // Adding request to request queue
                Singleton.getInstance( ViewDetailsContractors.this ).addToRequestQueue(stringRequest);



            }
        });



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
        Glide.with(ViewDetailsContractors.this).load(url).into(imageView1);
        String url2 = intent.getStringExtra("imageurl2");
        Glide.with(ViewDetailsContractors.this).load(url2).into(imageView2);
        String url3 = intent.getStringExtra("imageurl3");
        Glide.with(ViewDetailsContractors.this).load(url3).into(imageView3);










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
