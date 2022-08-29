package com.example.myapplication;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
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

public class ContractorFL1 extends Fragment {
    EditText email, password;
    TextView relativeLayout,forgot,signupcontractorss;
    String emails, passwords;
    ProgressDialog progressDialog;
    ContractorSession contractorSession;
    ImageView imageView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v2 = inflater.inflate(R.layout.fragment_contractor_f_l1, container, false);

        contractorSession = new ContractorSession(getActivity());

        if (contractorSession.isUserLoggedin()) {
            Intent intent = new Intent(getActivity(), ContractorHome.class);
            startActivity(intent);
        }

            email = v2.findViewById(R.id.et2);
            password = v2.findViewById(R.id.et3);
            imageView = v2.findViewById(R.id.passwordtogg);
            progressDialog = new ProgressDialog(getActivity());
            progressDialog.setMessage("Loading...Please Wait");

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowHidePass();
            }
        });


            forgot = v2.findViewById(R.id.textView10);
            forgot.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(getActivity(),ForgotPasswordContractor.class));
                }
            });

            signupcontractorss = v2.findViewById(R.id.textView9);
            signupcontractorss.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(getActivity(),SignUp.class));
                }
            });


            relativeLayout = v2.findViewById(R.id.editTextTextPersonName7);
            relativeLayout.setOnClickListener(new View.OnClickListener() {
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


                    StringRequest stringRequest = new StringRequest(Request.Method.POST,


                            Utils.ContractorFL1, new Response.Listener<String>() {


                        @Override
                        public void onResponse(String response) {
                            Log.d("testerror", response);
                            try {
                                JSONObject jObj = new JSONObject(response);
                                boolean error = jObj.getBoolean("error");
                                String error_msg = jObj.getString("msg");
                                String id = jObj.getString("id");

                                ConractorInfo contractorInfo = new ConractorInfo(getActivity());

                                contractorSession.setLoggedin(true);
                                contractorInfo.setId(id);


                                if (!error) {

                                    progressDialog.hide();

                                    Toast.makeText(getActivity(), error_msg, Toast.LENGTH_LONG).show();

                                    if (error_msg.equals("User Exist")) {

                                        progressDialog.dismiss();



                                    } else {

                                        progressDialog.dismiss();


                                        Toast.makeText(getActivity(), "Login Successfull", Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(getActivity(), ContractorHome.class);
                                        startActivity(intent);

                                    }


                                } else {
                                    // Error in login. Get the error message
                                    progressDialog.hide();

                                    Toast.makeText(getActivity(), "Login Failed", Toast.LENGTH_SHORT).show();

                                }
                            } catch (JSONException e) {
                                progressDialog.hide();
                                // JSON error
                                e.printStackTrace();


                                Toast.makeText(getActivity(), "Incorrect Email Or Password Try Again", Toast.LENGTH_SHORT).show();
                            }


                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            progressDialog.hide();
                            //Log.e(TAG, "Login Error: " + error.getMessage());

                            Toast.makeText(getActivity(), error.getMessage(), Toast.LENGTH_SHORT).show();


                        }
                    }) {

                        @Override
                        protected Map<String, String> getParams() {


                            // Posting parameters to login url


                            Map<String, String> params = new HashMap<>();


                            params.put("password", passwords);
                            params.put("email", emails);

                            return params;


                        }

                    };

                    // Adding request to request queue
                    Singleton.getInstance(getActivity()).addToRequestQueue(stringRequest);


                }

            });

            return v2;
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

