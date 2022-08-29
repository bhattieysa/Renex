package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.ListAdapter;
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
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class MyProjectsClient extends AppCompatActivity {
    RecyclerView recyclerView;
    ClientsAdapter adapter;
    JSONArray jsonArray;
    JSONObject server_responce;
    List<ClientprojectsModel> list;
    ProgressDialog progressDialog;
    String user_id;
    UserInfo userInfo;
    String jobid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_projects_client);
        getSupportActionBar().hide();
        userInfo=new UserInfo(this);
        user_id=userInfo.getKeyId();
        Log.d(user_id, "getParams: ");
        recyclerView = findViewById(R.id.myprojectclient);
        recyclerView.hasFixedSize();
        recyclerView.setLayoutManager(new LinearLayoutManager(MyProjectsClient.this));

        progressDialog = new ProgressDialog(MyProjectsClient.this);
        progressDialog.setMessage("Loading...Please wait");
        list = new ArrayList<>();





        StringRequest postRequest = new StringRequest(Request.Method.POST, Utils.MyprojectsClient,
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


                                    Toast.makeText(MyProjectsClient.this,"My Projects Is Empty", Toast.LENGTH_LONG).show();
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





                                    ClientprojectsModel listData = new ClientprojectsModel(jobtitle,jobdesc,jobloc,jobtime,jobbudget,image,imagee
                                    ,image2,imagee2,image3,imagee3,jobid);
                                    list.add(listData);


//


                                }

                            }
                            adapter=new ClientsAdapter(MyProjectsClient.this,list);

                            recyclerView.setAdapter(adapter);

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

                UserInfo info = new UserInfo(MyProjectsClient.this);

                params.put("user_id", user_id);




                return params;


            }
        };
        Singleton.getInstance (MyProjectsClient.this).addToRequestQueue (postRequest );

    }

}