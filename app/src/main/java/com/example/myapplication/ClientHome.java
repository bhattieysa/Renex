package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

public class ClientHome extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_home);
        setTitle("Client's Home");
    }

    public void PostJob(View view) {
        startActivity(new Intent(ClientHome.this,job_posting.class));
    }

    public void ShowMyProjects(View view) {
        startActivity(new Intent(ClientHome.this,MyProjectsClient.class));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = new MenuInflater(this);
        menuInflater.inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.Logout:


                UserSession sessionManager = new UserSession(getApplicationContext());
                sessionManager.setLoggedin(false);
                startActivity(new Intent(getApplicationContext(),FirstPage.class));
                finish();

        }
        return super.onOptionsItemSelected(item);
    }
}