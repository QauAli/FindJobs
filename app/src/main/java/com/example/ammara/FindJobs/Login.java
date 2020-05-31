package com.example.ammara.FindJobs;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import RestfullServices.Client;
import RestfullServices.FindJobService;
import ModelClasses.Advertiser;
import ModelClasses.Applicant;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Login extends AppCompatActivity {

    private Button reg_applicant, reg_advertiser, forget_pass, btn_login;
    private Spinner type;
    private EditText email,password;
    private FindJobService service = Client.getClient().create(FindJobService.class);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getReferences();
        reg_applicant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(Login.this, ApplicantRegistration.class);
                Login.this.startActivity(myIntent);
            }
        });
        reg_advertiser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(Login.this, AdvertiserRegistration.class);
                Login.this.startActivity(myIntent);
            }
        });
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userType = type.getSelectedItem().toString();
                if(userType.equalsIgnoreCase("Applicant")){
                    Call<Applicant> call = service.authenticateApplicant(new Applicant(email.getText().toString(),
                            password.getText().toString()));
                    call.enqueue(new Callback<Applicant>() {
                        @Override
                        public void onResponse(Call<Applicant> call, Response<Applicant> response) {
                            if(response.isSuccessful() && response.body()!=null){
                                SharedPreferences.Editor editor = getSharedPreferences(Constants.file, MODE_PRIVATE).edit();
                                editor.putString("name", response.body().getFirst_name()+response.body().getLast_name());
                                editor.putString("email", response.body().getEmail());
                                editor.putString("imageName", response.body().getImage_path());
                                editor.putString("mobile", response.body().getMobile());
                                editor.putString("postal address", response.body().getPostal_address());
                                editor.putString("ApplicantPassword", response.body().getPassword());

                                editor.putString("type", "Applicant");
                                editor.putInt("ApplicantId", response.body().getId());
                                editor.putInt("currentUserId", response.body().getId());
                                editor.apply();
                                Intent myIntent = new Intent(Login.this, ApplicantCycle.class);
                                Login.this.startActivity(myIntent);
                            }else {
                                Toast.makeText(Login.this,
                                        "Wrong Email/Password",Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<Applicant> call, Throwable t) {
                            Toast.makeText(Login.this,
                                    "Server Not Responding",Toast.LENGTH_SHORT).show();
                        }
                    });
                }else if(userType.equalsIgnoreCase("Advertiser")){
                    Call<Advertiser> call = service.authenticateAdvertiser(new Advertiser(email.getText().toString(),
                            password.getText().toString()
                    ));
                    call.enqueue(new Callback<Advertiser>() {
                        @Override
                        public void onResponse(Call<Advertiser> call, Response<Advertiser> response) {
                            if(response.isSuccessful() && response.body()!=null){
                                SharedPreferences.Editor editor = getSharedPreferences(Constants.file, MODE_PRIVATE).edit();
                                editor.putString("name", response.body().getName());
                                editor.putString("email", response.body().getEmail());
                                editor.putString("imageName", response.body().getImagename());
                                editor.putString("mobile", response.body().getMobile());
                                editor.putString("address", response.body().getAddress());
                                editor.putString("company", response.body().getCompany());
                                editor.putString("type", "Advertiser");
                                editor.putInt("AdvertiserId", response.body().getId());
                                editor.putString("AdvertiserPassword", response.body().getPassword());
                                editor.putInt("currentUserId", response.body().getId());
                                editor.apply();
                                Intent myIntent = new Intent(Login.this, AdvertiserCycle.class);
                                Login.this.startActivity(myIntent);
                            }else {
                                Toast.makeText(Login.this,
                                        "Wrong Email/Password",Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<Advertiser> call, Throwable t) {

                        }
                    });


                }else if(userType.equalsIgnoreCase("Admin")){
                    if(email.getText().toString().equalsIgnoreCase("admin@gmail.com") && password.getText().toString().equalsIgnoreCase("12345")){
                        SharedPreferences.Editor editor = getSharedPreferences(Constants.file, MODE_PRIVATE).edit();
                        editor.putString("type", "Admin");
                        editor.apply();
                        Intent myIntent = new Intent(Login.this, AdminCycle.class);
                        Login.this.startActivity(myIntent);
                    }
                }
            }
        });
        forget_pass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(Login.this, ForgetPassword.class);
                Login.this.startActivity(myIntent);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        SharedPreferences prefs = getSharedPreferences(Constants.file, MODE_PRIVATE);
        String Usertype = prefs.getString("type", "Logout");

        if(Usertype.equalsIgnoreCase("Advertiser")){
            Intent myIntent = new Intent(Login.this, AdvertiserCycle.class);
            Login.this.startActivity(myIntent);
        }else if(Usertype.equalsIgnoreCase("Applicant")){
            Intent myIntent = new Intent(Login.this, ApplicantCycle.class);
            Login.this.startActivity(myIntent);
        }else if(Usertype.equalsIgnoreCase("Admin")){
            Intent myIntent = new Intent(Login.this, AdminCycle.class);
            Login.this.startActivity(myIntent);
        }else {
        }
    }

    private void getReferences() {
        type = findViewById(R.id.type);
        email = findViewById(R.id.lEditEmail);
        password = findViewById(R.id.lEditPassword);
        reg_advertiser = findViewById(R.id.btn_advertiser_reg);
        reg_applicant = findViewById(R.id.btn_applicant_reg);
        forget_pass = findViewById(R.id.btnForgotPassword);
        btn_login = findViewById(R.id.btnLogin);
    }
}
