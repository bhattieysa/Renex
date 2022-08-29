package com.example.myapplication;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
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
import java.util.regex.Pattern;

public class ContractorSignupFragment extends Fragment {
    EditText email, password,username,paypalid;
    TextView login,Already;
    String emails, passwords, usernames,contractortoken,ClientId;
    ProgressDialog progressDialog;
    ImageView imageView;
    private static final Pattern PASSWORD_PATTERN =
            Pattern.compile("^" +
                    "(?=.*[0-9])" +         //at least 1 digit
                    "(?=.*[a-z])" +         //at least 1 lower case letter
                    "(?=.*[A-Z])" +         //at least 1 upper case letter
                    "(?=.*[a-zA-Z])" +      //any letter
                    //at least 1 special character
                    //no white spaces
                    ".{8,}" +               //at least 4 characters
                    "$");

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view2 =  inflater.inflate(R.layout.fragment_contractor_signup, container, false);
        username = view2.findViewById(R.id.et1);
        email = view2.findViewById(R.id.et2);
        password = view2.findViewById(R.id.et3);
        paypalid = view2.findViewById(R.id.et4);
        imageView = view2.findViewById(R.id.passwordtogg);
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Loading...Please Wait");

        FirebaseMessaging.getInstance().getToken()
                .addOnCompleteListener(new OnCompleteListener<String>() {
                    @Override
                    public void onComplete(@NonNull Task<String> task) {
                        contractortoken = task.getResult();
                        Log.d("TAG", "onComplete: Token"+contractortoken);
                    }
                });

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowHidePass();
            }
        });


        Already = view2.findViewById(R.id.textView9);

        Already.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(),Login.class));
            }
        });





        login = view2.findViewById(R.id.contractorsignupbtn);
        login.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                emails = email.getText().toString();
                passwords = password.getText().toString();
                usernames = username.getText().toString();
                ClientId = paypalid.getText().toString();

                if (TextUtils.isEmpty(usernames)) {
                    username.setError("Username Required");
                    return;
                }
                if (emails.isEmpty()) {
                    email.setError("Field can't be empty");
                    return;
                }
                else
                    if (!Patterns.EMAIL_ADDRESS.matcher(emails).matches()) {
                    email.setError("Please enter a valid email address");
                    return;
                }

                if (passwords.isEmpty()) {
                    password.setError("Field can't be empty");
                    return ;
                }
                else
                    if (!PASSWORD_PATTERN.matcher(passwords).matches()) {
                    password.setError("Must contain Atleast 8 charcters \nAtleast one Small & one Capital letter \nAtleast one Digit");
                    return ;
                }

                if (ClientId.isEmpty()){
                    paypalid.setError("Field Cannot Be Empty");
                }

                progressDialog.show();


                StringRequest stringRequest=new StringRequest( Request.Method.POST ,


                        Utils.ContractorSignupFragment, new Response.Listener <String> () {


                    @Override
                    public void onResponse(String response) {
                        Log.d("testerror",response);
                        try {
                            JSONObject jObj = new JSONObject(response);
                            boolean error = jObj.getBoolean("error");
                            String error_msg = jObj.getString("msg");


                            if (!error) {

                                progressDialog.hide();

                                Toast.makeText(getActivity(),error_msg,Toast.LENGTH_LONG).show();

                                if(error_msg.equals("User Exist")){

                                    progressDialog.dismiss();

                                    Toast.makeText ( getActivity(),"EmailAlready Exist",Toast.LENGTH_SHORT ).show ();

                                }else {

                                    progressDialog.dismiss();



                                    Toast.makeText(getActivity(), "Account Created Successfull", Toast.LENGTH_SHORT).show();
                                    Intent intent =new Intent(getActivity(),ContractorHome.class);
                                    startActivity(intent);

                                }








                            } else {
                                // Error in login. Get the error message
                                progressDialog.hide();

                                Toast.makeText ( getActivity(),"Creating account Failed",Toast.LENGTH_SHORT ).show ();

                            }
                        } catch (JSONException e) {
                            progressDialog.hide();
                            // JSON error
                            e.printStackTrace();


                            Toast.makeText (getActivity(),"Internet Error",Toast.LENGTH_SHORT ).show ();
                        }



                    }
                } , new Response.ErrorListener () {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.hide();
                        //Log.e(TAG, "Login Error: " + error.getMessage());



                    }
                }) {

                    @Override
                    protected Map<String, String> getParams() {


                        // Posting parameters to login url


                        Map<String, String> params = new HashMap<>();

                        params.put("username", usernames);
                        params.put("password", passwords);
                        params.put("email", emails);
                        params.put("contractortoken", contractortoken);
                        params.put("paypalid" , ClientId);


                        return params;


                    }

                };

                // Adding request to request queue
                Singleton.getInstance( getActivity() ).addToRequestQueue(stringRequest);
            }
        });




        return view2;
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