package com.example.ammara.FindJobs;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.List;

import Adapters.AdvertisementSkillsAdapter;
import Adapters.SkillsAdapter;
import ModelClasses.Advertisement;
import ModelClasses.Advertiser;
import ModelClasses.Skill;
import ModelClasses.Validation;
import RestfullServices.Client;
import RestfullServices.FindJobService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdminCycle extends AppCompatActivity {


    private RecyclerView rv;
    private Button add, logout;
    private EditText name;
    private FindJobService service = Client.getClient().create(FindJobService.class);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_cycle);
        getReferences();
        refreshSkills();

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = AdminCycle.this.getSharedPreferences(Constants.file, MODE_PRIVATE).edit();
                editor.clear();
                editor.apply();
                Intent myIntent = new Intent(AdminCycle.this, Login.class);
                myIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                AdminCycle.this.startActivity(myIntent);
            }
        });

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addskill();
            }
        });
    }
    private void refreshSkills() {
        Call<List<Skill>> call = service.getAllSkills();
        call.enqueue(new Callback<List<Skill>>() {
            @Override
            public void onResponse(Call<List<Skill>> call, Response<List<Skill>> response) {
                if(response.isSuccessful()){
                    SkillsAdapter adapter = new SkillsAdapter(response.body(),AdminCycle.this);
                    rv.setAdapter(adapter);
                    rv.setLayoutManager(new LinearLayoutManager(AdminCycle.this));
                }
            }

            @Override
            public void onFailure(Call<List<Skill>> call, Throwable t) {

            }
        });
        name.getText().clear();

    }

    private void addskill() {
        if(Validation.isEmpty(name)){
            name.setError("Required");
            return;
        }
        Skill skill = new Skill(0,name.getText().toString());
        Call<Skill> call = service.createSkill(skill,0);
        call.enqueue(new Callback<Skill>() {
            @Override
            public void onResponse(Call<Skill> call, Response<Skill> response) {
                if(response.isSuccessful()){
                    refreshSkills();
                }
            }

            @Override
            public void onFailure(Call<Skill> call, Throwable t) {
            }
        });


    }

    private void getReferences() {
        rv = findViewById(R.id.rv);
        add = findViewById(R.id.add);
        logout = findViewById(R.id.logout);
        name = findViewById(R.id.name);

    }
}
