package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MyProjectsContractor extends AppCompatActivity {

    RecyclerView recyclerView;
    ContractorAdapter adapter;
    JSONArray jsonArray;
    JSONObject server_responce;
    List<ContractorProjectModel> list;
    ProgressDialog progressDialog;
    String id;
    String jobid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_projects_contractor);
        getSupportActionBar().hide();


        recyclerView = findViewById(R.id.myprojectcontractor);
        recyclerView.hasFixedSize();
        recyclerView.setLayoutManager(new LinearLayoutManager(MyProjectsContractor.this));

        progressDialog = new ProgressDialog(MyProjectsContractor.this);
        progressDialog.setMessage("Loading...Please wait");
        list = new ArrayList<ContractorProjectModel>();


        final JsonObjectRequest request1=new JsonObjectRequest(Request.Method.POST, Utils.MyprojectsContractors, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("text", "Login Response: " + response.toString());


                        try {



                            jsonArray = response.getJSONArray("renex");
                            for (int i=0;i<jsonArray.length();i++){
                                server_responce=jsonArray.getJSONObject(i);



                                String error= server_responce.getString ("error");

                                if (error.equals("true")) {


                                    Toast.makeText(MyProjectsContractor.this,"No Projects Available Come back Later", Toast.LENGTH_LONG).show();
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
                                     jobid = server_responce.getString("id");
                                    //String image = server_responce.getString("Image");



                                    ContractorProjectModel listData = new ContractorProjectModel(jobtitle,jobdesc,jobloc,jobtime,jobbudget,image,imagee,
                                            image2,image3,imagee2,imagee3,jobid);
                                    list.add(listData);




                                }

                            }
                            adapter=new ContractorAdapter(MyProjectsContractor.this,list);

                            recyclerView.setAdapter(adapter);




                        } catch (JSONException e) {
                            e.printStackTrace();
                            progressDialog.hide();
                        }


                    }




                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });


        Singleton.getInstance( MyProjectsContractor.this).addToRequestQueue(request1);
    }
}