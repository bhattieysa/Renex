package com.example.myapplication;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ClientFL2 extends Fragment {
    EditText email, password;
    TextView login,forgot,signupclients;
    String emails, passwords;
    ProgressDialog progressDialog;
    UserSession userSession;
    ImageView imageView;
    String token;




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment


        View v1= inflater.inflate(R.layout.fragment_client_f_l2, container, false);


        userSession = new UserSession(getActivity());

        if (userSession.isUserLoggedin()){
           Intent intent = new Intent(getActivity(),ClientHome.class);
           startActivity(intent);

        }
        email = v1.findViewById(R.id.et2);
        password = v1.findViewById(R.id.et3);
        imageView = v1.findViewById(R.id.passwordtogg);
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Loading...Please Wait");

        FirebaseMessaging.getInstance().getToken()
                .addOnCompleteListener(new OnCompleteListener<String>() {
                    @Override
                    public void onComplete(@NonNull Task<String> task) {
                        token = task.getResult();
                        Log.d("TAG", "onComplete: Token"+token);
                    }
                });



        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowHidePass();
            }
        });

        forgot = v1.findViewById(R.id.textView10);
        forgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(),ForgotPassword.class));
            }
        });
        signupclients = v1.findViewById(R.id.textView9);
        signupclients.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(),SignUp.class));

            }
        });

        login = v1.findViewById(R.id.editTextTextPersonName7);
        login.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                emails = email.getText().toString();
                passwords = password.getText().toString();


                if (TextUtils.isEmpty(emails)) {
                    email.setError("Email Required");
                    return;
                }

                if (TextUtils.isEmpty(passwords)) {
                    email.setError("Password Required");
                    return;
                }

                progressDialog.show();

                StringRequest stringRequest=new StringRequest( Request.Method.POST ,


                        Utils.ClientFL2, new Response.Listener <String> () {


                    @Override
                    public void onResponse(String response) {
                        Log.d("testerror",response);
                        try {
                            JSONObject jObj = new JSONObject(response);
                            boolean error = jObj.getBoolean("error");
                            String error_msg = jObj.getString("msg");
                            String id = jObj.getString("id");


                            UserInfo info = new UserInfo(getActivity());

                            userSession.setLoggedin(true);
                            info.setId(id);


                            if (!error) {

                                progressDialog.hide();

                                Toast.makeText(getActivity(),error_msg,Toast.LENGTH_LONG).show();

                                if(error_msg.equals("User Exist")){

                                    progressDialog.dismiss();

                                    Toast.makeText ( getActivity(),"EmailAlready Exist",Toast.LENGTH_SHORT ).show ();

                                }else {

                                    progressDialog.dismiss();
                                   // Toast.makeText(getActivity(), "Login Successfull", Toast.LENGTH_SHORT).show();
                                    Intent intent =new Intent(getActivity(),ClientHome.class);
                                    startActivity(intent);

                                }

                            } else {
                                // Error in login. Get the error message
                                progressDialog.hide();


                            }
                        } catch (JSONException e) {
                            progressDialog.hide();
                            // JSON error
                            e.printStackTrace();


                            Toast.makeText(getActivity(), "Incorrect Email Or Password Try Again", Toast.LENGTH_SHORT).show();
                        }



                    }
                } , new Response.ErrorListener () {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.hide();
                        //Log.e(TAG, "Login Error: " + error.getMessage());

                        Toast.makeText (getActivity(),error.getMessage(),Toast.LENGTH_SHORT ).show ();


                    }
                }) {

                    @Override
                    protected Map<String, String> getParams() {


                        // Posting parameters to login url


                        Map<String, String> params = new HashMap<>();


                        params.put("password", passwords);
                        params.put("email", emails);
                        params.put("token", token);


                        return params;


                    }

                };

                // Adding request to request queue
                Singleton.getInstance( getActivity() ).addToRequestQueue(stringRequest);
            }
        });



        return v1;
    }

    private void ShowHidePass() {
        if(imageView.getId()==R.id.passwordtogg){

            if(password.getTransformationMethod().equals(PasswordTransformationMethod.getInstance())){


                //Show Password
                password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            }
            else{

                //Hide Password
                password.setTransformationMethod(PasswordTransformationMethod.getInstance());

            }
        }
    }
}
