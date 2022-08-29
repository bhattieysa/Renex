package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class ContractorsProject extends AppCompatActivity {
    RecyclerView recyclerView;
    jobreqAdapter jobreqAdapter;
    ProgressDialog progressDialog;
    String contid;
    JSONObject server_responce;
    List<MyProjectContractorModel> list;
    ConractorInfo conractorInfo;
    MyProjectContractorModel myProjectContractorModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contractors_project);
        getSupportActionBar().hide();

        recyclerView = findViewById(R.id.contractorprojectsrecview);
        recyclerView.hasFixedSize();
        recyclerView.setLayoutManager(new LinearLayoutManager(ContractorsProject.this));

        progressDialog = new ProgressDialog(ContractorsProject.this);
        progressDialog.setMessage("Loading...Please wait");

        list = new ArrayList<>();




        conractorInfo=new ConractorInfo(this);
        contid= conractorInfo.getKeyId();


        StringRequest postRequest = new StringRequest(Request.Method.POST, Utils.myprojectcontractors,
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


                                if (error.equals("true")) {


                                    Toast.makeText(ContractorsProject.this,"My Projects Is Empty", Toast.LENGTH_LONG).show();
                                    progressDialog.hide();


                                } else {
                                    progressDialog.hide();


                                    String jobtitle = server_responce.getString("jobtitle");
                                    String jobdesc = server_responce.getString("jobdesc");
                                    String jobloc = server_responce.getString("jobloc");
                                    String jobtime = server_responce.getString("jobtime");
                                    String jobbudget = server_responce.getString("jobbudget");
                                    String image = server_responce.getString("image");
                                    String image2 = server_responce.getString("image2");
                                    String image3 = server_responce.getString("image3");
                                    String  imagee=Utils.imageUrl+image;
                                    String  imagee2=Utils.imageUrl2+image2;
                                    String  imagee3=Utils.imageUrl3+image3;
                                    String jobid = server_responce.getString("id");
                                    //Toast.makeText(getActivity(),"asas00",Toast.LENGTH_LONG).show();



                                    MyProjectContractorModel listData = new MyProjectContractorModel(jobtitle,jobdesc,jobloc,jobtime,jobbudget,image,imagee
                                            ,image2,imagee2,image3,imagee3,jobid);
                                    list.add(listData);


//


                                }

                            }
                            jobreqAdapter=new jobreqAdapter(ContractorsProject.this,list);

                            recyclerView.setAdapter(jobreqAdapter);

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

                UserInfo info = new UserInfo(ContractorsProject.this);

                params.put("contractor_id", contid);




                return params;


            }
        };
        Singleton.getInstance (ContractorsProject.this).addToRequestQueue (postRequest );

    }
}