package com.example.myapplication;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.paypal.android.sdk.payments.PayPalConfiguration;
import com.paypal.android.sdk.payments.PayPalPayment;
import com.paypal.android.sdk.payments.PayPalService;
import com.paypal.android.sdk.payments.PaymentActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Payment extends AppCompatActivity {
    private static final int PAYMENT_REQ_CODE = 3322;
    private PayPalConfiguration payPalConfiguration;
    ProgressDialog progressDialog;
    ImageView paynow, paynow1;
    JSONObject server_responce;
    String jobid;
    String paypalid;
    String budget;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);
        getSupportActionBar().hide();
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("please wait");
        paynow = findViewById(R.id.creditcard);



        Intent intent = getIntent();
        jobid = intent.getStringExtra("jobid");
        budget = intent.getStringExtra("budget");












        StringRequest postRequest = new StringRequest(Request.Method.POST, Utils.getClientId,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        // response
                        Log.d("Response", response);

                        try {
                            JSONObject obj = new JSONObject(response);

                            //we have the array named hero inside the object
                            //so here we are getting that json array
                            JSONArray jsonArray = obj.getJSONArray("renex");

                            for (int i = 0; i < jsonArray.length(); i++) {
                                server_responce = jsonArray.getJSONObject(i);


                                String error = server_responce.getString("error");

                                //Toast.makeText(getActivity(),error,Toast.LENGTH_LONG).show();

                                if (error.equals("true")) {


                                    Toast.makeText(Payment.this,"Internet Error", Toast.LENGTH_LONG).show();
                                    progressDialog.hide();


                                } else {
                                    progressDialog.hide();


                                     paypalid = server_responce.getString("paypalid").trim();
                                    //Toast.makeText(Payment.this, budget, Toast.LENGTH_SHORT).show();

                                    Log.d("eysa",budget);

                                    payPalConfiguration = new PayPalConfiguration()
                                            .environment(PayPalConfiguration.ENVIRONMENT_PRODUCTION)
                                            .clientId(paypalid);
                                    Intent paymentService = new Intent(Payment.this, PayPalService.class);
                                    paymentService.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, payPalConfiguration);
                                    startService(paymentService);






//


                                }

                            }


                        } catch (JSONException e) {
                            progressDialog.hide();
                            e.printStackTrace();


                        }
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // error
                        Log.d("Error.Response", Objects.requireNonNull(error.getMessage()));
                    }
                }
        ) {

            @Override
            protected Map<String, String> getParams() {


                // Posting parameters to login url


                Map<String, String> params = new HashMap<>();



                params.put("job_id", jobid);




                return params;


            }
        };
        Singleton.getInstance (Payment.this).addToRequestQueue (postRequest );
        paynow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                proceedPayment();

            }


               public void proceedPayment(){
                PayPalPayment YourTicket = new PayPalPayment(new BigDecimal(budget ), "USD",
                        "Total price:",
                        PayPalPayment.PAYMENT_INTENT_SALE);

                //payment service
                Intent intent = new Intent(Payment.this, PaymentActivity.class);
                intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, payPalConfiguration);
                intent.putExtra(PaymentActivity.EXTRA_PAYMENT, YourTicket);
                startActivityForResult(intent, PAYMENT_REQ_CODE);
            }
        });


        paynow1 = findViewById(R.id.paypalbtn);
        paynow1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                PayPalPayment YourTicket = new PayPalPayment(new BigDecimal(budget ), "USD",
                        "Total price:",
                        PayPalPayment.PAYMENT_INTENT_SALE);

                //payment service
                Intent intent = new Intent(Payment.this, PaymentActivity.class);
                intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, payPalConfiguration);
                intent.putExtra(PaymentActivity.EXTRA_PAYMENT, YourTicket);
                startActivityForResult(intent, PAYMENT_REQ_CODE);
            }
        });

    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PAYMENT_REQ_CODE) {
            if (resultCode == PaymentActivity.RESULT_OK) {
                startActivity(new Intent(getApplicationContext(),ClientHome.class));

                Toast.makeText(getApplicationContext(), "Payment successful! Please wait.",
                        Toast.LENGTH_SHORT)
                        .show();

               // addDays();

            } else {
                Toast.makeText(getApplicationContext(), "Payment Failed!",
                        Toast.LENGTH_SHORT)
                        .show();
               // grpBtns.setVisibility(View.VISIBLE);
                progressDialog.dismiss();
            }
        }
    }
}