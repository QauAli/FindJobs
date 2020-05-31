package com.example.ammara.FindJobs;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import ModelClasses.Advertiser;
import ModelClasses.Applicant;
import ModelClasses.Validation;
import RestfullServices.Client;
import RestfullServices.FindJobService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ForgetPassword extends AppCompatActivity {
    private EditText email;
    private Button btn;
    private FindJobService service = Client.getClient().create(FindJobService.class);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);

        getReferences();
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Validation.isEmail(email)) {
                    email.setError("Wrong Email");
                    return;
                }

                Call<String> call = service.getPassword(new Advertiser(email.getText().toString(), ""));
                call.enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {

                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {

                    }
                });
                Toast.makeText(ForgetPassword.this, "Email Sent!", Toast.LENGTH_SHORT).show();
                Intent myIntent = new Intent(ForgetPassword.this, Login.class);
                ForgetPassword.this.startActivity(myIntent);
            }
        });

    }

    private void getReferences() {
        email = findViewById(R.id.email);
        btn = findViewById(R.id.btn);
    }
}
