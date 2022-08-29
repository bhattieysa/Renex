package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ForgotPassword extends AppCompatActivity {
    EditText mail;
    TextView button;
    String email;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...Please Wait");


        mail = findViewById(R.id.email2);
        button = findViewById(R.id.forgotpassword);





        button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                email = mail.getText().toString().trim();


                if (TextUtils.isEmpty(email)){
                    mail.setError("Please Enter Your Email");
                    return;
                }

                Toast.makeText(ForgotPassword.this, "Password Has Been Sent To Your Email", Toast.LENGTH_SHORT).show();
                progressDialog.show();






                StringRequest stringRequest=new StringRequest( Request.Method.POST ,


                        Utils.forgotpasswordclient, new Response.Listener <String> () {


                    @Override
                    public void onResponse(String response) {
                        Log.d("testerror",response);
                        try {
                            JSONObject jObj = new JSONObject(response);
                            boolean error = jObj.getBoolean("error");
                           // String error_msg = jObj.getString("msg");




                            if (!error) {

                                progressDialog.hide();



                                    progressDialog.dismiss();



                                    Toast.makeText(ForgotPassword.this, "Password Created Successfully", Toast.LENGTH_SHORT).show();


                                }

                        } catch (JSONException e) {
                            progressDialog.hide();
                            // JSON error
                            e.printStackTrace();


                            Toast.makeText (ForgotPassword.this,"Internet Error"+e.getMessage(),Toast.LENGTH_SHORT ).show ();
                        }



                    }
                } , new Response.ErrorListener () {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.hide();
                        //Log.e(TAG, "Login Error: " + error.getMessage());

                        Toast.makeText (ForgotPassword.this,error.getMessage(),Toast.LENGTH_SHORT ).show ();


                    }
                }) {

                    @Override
                    protected Map<String, String> getParams() {


                        // Posting parameters to login url


                        Map<String, String> params = new HashMap<>();



                        params.put("email", email);


                        return params;


                    }

                };

                // Adding request to request queue
                Singleton.getInstance( ForgotPassword.this ).addToRequestQueue(stringRequest);




            }
        });
    }
}