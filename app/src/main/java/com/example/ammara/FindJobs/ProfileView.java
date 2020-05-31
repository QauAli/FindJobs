package com.example.ammara.FindJobs;

import android.content.Intent;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import Adapters.AdvertisementSkillsAdapter;
import Adapters.ProfileEducationAdapter;
import ModelClasses.Applicant;
import ModelClasses.Conversation;
import RestfullServices.Client;
import RestfullServices.FindJobService;
import RestfullServices.ImageLibrary;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileView extends AppCompatActivity {
    private TextView name,cnic,birth_date,father_name,
            province,district,postal_address,permanent_address,
            mobile,phone,job_status, email;
    private Button msgbtn;
    private ImageView profileImage;
    private RecyclerView childRv;
    private RecyclerView childRvEducation;
    private int selectedApplicantId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        getReferences();
        
        Intent intent = getIntent();
        final Applicant profile = (Applicant) intent.getSerializableExtra("profile");
        final int advertiserId = intent.getIntExtra("advertiserId",0);

        ImageLibrary.LoadImage(profileImage,profile.getImage_path(),ProfileView.this);
        name.setText(profile.getLast_name());
        cnic.setText(profile.getCnic());
        birth_date.setText(profile.getBirth_date());
        father_name.setText(profile.getFather_name());
        email.setText(profile.getEmail());
        province.setText((profile.getProvince()));
        district.setText((profile.getDistrict()));
        postal_address.setText((profile.getPostal_address()));
        permanent_address.setText((profile.getPermanent_address()));
        mobile.setText((profile.getMobile()));
        phone.setText((profile.getPhone()));
        job_status.setText((profile.getJob_status()));
        selectedApplicantId = profile.getId();
        AdvertisementSkillsAdapter childAdapter = new AdvertisementSkillsAdapter(profile.getSkills(),ProfileView.this);
        childRv.setAdapter(childAdapter);
        childRv.setLayoutManager(new LinearLayoutManager(ProfileView.this));
        ProfileEducationAdapter childEducationAdapter = new ProfileEducationAdapter(profile.getEducations(),ProfileView.this);
        childRvEducation.setAdapter(childEducationAdapter);
        childRvEducation.setLayoutManager(new LinearLayoutManager(ProfileView.this));

        msgbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FindJobService service = Client.getClient().create(FindJobService.class);
                Call<Conversation> call = service.createCon(selectedApplicantId,
                        advertiserId,
                        new Conversation());
                call.enqueue(new Callback<Conversation>() {
                    @Override
                    public void onResponse(Call<Conversation> call, Response<Conversation> response) {
                        if(response.isSuccessful()){
                            Intent myIntent = new Intent(ProfileView.this, Messages.class);
                            myIntent.putExtra("conId",response.body().getId());
                            ProfileView.this.startActivity(myIntent);
                        }else if(response.code() == 200){
                            Toast.makeText(ProfileView.this, "200", Toast.LENGTH_SHORT).show();
//                                Intent myIntent = new Intent(context, Messages.class);
//                                myIntent.putExtra("conId",response.body().getId());
//                                context.startActivity(myIntent);
                        }
                    }

                    @Override
                    public void onFailure(Call<Conversation> call, Throwable t) {

                    }
                });
            }
        });

    }

    private void getReferences() {
        childRv = findViewById(R.id.rv);
        childRvEducation = findViewById(R.id.rv_education);
        name = findViewById(R.id.name);
        cnic = findViewById(R.id.cnic);
        email = findViewById(R.id.email);
        birth_date = findViewById(R.id.dob);
        father_name = findViewById(R.id.father_name);
        province = findViewById(R.id.province);
        district = findViewById(R.id.district);
        postal_address = findViewById(R.id.postal_address);
        permanent_address = findViewById(R.id.permanent);
        mobile = findViewById(R.id.mobile);
        phone = findViewById(R.id.phone);
        job_status = findViewById(R.id.job_status);
        msgbtn = findViewById(R.id.msg);
        profileImage = findViewById(R.id.imageView);
    }
}
