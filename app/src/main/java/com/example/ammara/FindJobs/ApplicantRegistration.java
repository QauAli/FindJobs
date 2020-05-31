
package com.example.ammara.FindJobs;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import RestfullServices.Client;
import RestfullServices.FindJobService;
import Fragments.ContactF;
import Fragments.EducationF;
import Fragments.PersonalF;

import ModelClasses.Applicant;
import ModelClasses.UserImage;
import ModelClasses.ApplicantWithImage;
import ModelClasses.Education;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ApplicantRegistration extends AppCompatActivity implements PersonalF.SendMessage, ContactF.SendContact, EducationF.SendEducation {

    PersonalF personalF = PersonalF.newInstance();
    ApplicantWithImage applicantWithImage;
    private FindJobService service = Client.getClient().create(FindJobService.class);
    private Applicant applicant;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_applicant_registration);

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction =
                fragmentManager.beginTransaction();

        fragmentTransaction.add(R.id.fragment_container, personalF);
        //fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();

    }
    @Override
    public void sendPersonalInfo(ApplicantWithImage applicantWithImage) {

        ContactF contactF = ContactF.newInstance();
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, contactF);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
        this.applicantWithImage = applicantWithImage;
    }

    @Override
    public void sendContactInfo(ApplicantWithImage applicantWithImage) {
        this.applicantWithImage.setPostal_address(applicantWithImage.getPostal_address());
        this.applicantWithImage.setPermanent_address(applicantWithImage.getPermanent_address());
        this.applicantWithImage.setMobile(applicantWithImage.getMobile());
        this.applicantWithImage.setPhone(applicantWithImage.getPhone());
        EducationF educationF = EducationF.newInstance();
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, educationF);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        List<Fragment> fragments = getSupportFragmentManager().getFragments();
        if (fragments != null) {
            for (Fragment f : fragments) {
                if (f instanceof EducationF) {
                    f.onActivityResult(requestCode, resultCode, data);
                }
            }
        }
    }

    @Override
    public void sendEducationInfo(ApplicantWithImage applicantWithImage) {
        File profileImg = applicantWithImage.getProfile_image();
        this.applicantWithImage.setProfile_image(applicantWithImage.getProfile_image());
        this.applicantWithImage.setJob_status(applicantWithImage.getJob_status());
        this.applicantWithImage.setEducations(applicantWithImage.getEducations());
        this.applicantWithImage.setSkills(applicantWithImage.getSkills());
        this.applicantWithImage.setExperianceDetails((applicantWithImage.getExperianceDetails()));

        applicant = new Applicant(this.applicantWithImage.getFirst_name(),
                this.applicantWithImage.getLast_name(),
                this.applicantWithImage.getCnic(),
                this.applicantWithImage.getBirth_date(),
                this.applicantWithImage.getFather_name(),
                this.applicantWithImage.getEmail(),
                this.applicantWithImage.getPassword(),
                this.applicantWithImage.getProvince(),
                this.applicantWithImage.getDistrict(),
                this.applicantWithImage.getPostal_address(),
                this.applicantWithImage.getPermanent_address(),
                this.applicantWithImage.getMobile(),
                this.applicantWithImage.getPhone(),
                this.applicantWithImage.getJob_status(),
                this.applicantWithImage.getEducations(),
                this.applicantWithImage.getSkills(),
                this.applicantWithImage.getExperianceDetails()
        );
        if (applicant.getEducations().size() == 0) {
            ArrayList<Education> emptyEducation = new ArrayList<>();
            emptyEducation.add(new Education("Middle", "", ""));
            applicant.setEducations(emptyEducation);
        }
        if (applicant.getSkills().size() == 0) {
            LinkedList<String> emptySkills = new LinkedList<>();
            emptySkills.add(new String(""));
            applicant.setSkills(emptySkills);
        }
        Call<Applicant> call = service.createApplicant(applicant);
        call.enqueue(new Callback<Applicant>() {
            @Override
            public void onResponse(Call<Applicant> call, Response<Applicant> response) {
                if (response.isSuccessful()) {
                    int id = response.body().getId();
                    //Toast.makeText(ApplicantRegistration.this, response.body().getId() + "", Toast.LENGTH_SHORT).show();
                    RequestBody imagee = RequestBody.create(MediaType.parse("image/*"), profileImg);
                    RequestBody idd = RequestBody.create(MediaType.parse("text/plain"), ""+id);
                    Call<UserImage> call2 = service.createApplicantImage(imagee,idd);
                    call2.enqueue(new Callback<UserImage>() {
                        @Override
                        public void onResponse(Call<UserImage> call2, Response<UserImage> response) {
                            if(response.isSuccessful()){
                                Toast.makeText(ApplicantRegistration.this, "Registration Complete", Toast.LENGTH_SHORT).show();
                                Intent myIntent = new Intent(ApplicantRegistration.this, Login.class);
                                myIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                ApplicantRegistration.this.startActivity(myIntent);
                            }
                            else if(response.code() == 200){
                                Toast.makeText(ApplicantRegistration.this, "Email Already Used!", Toast.LENGTH_SHORT).show();
                            }
                            else{
                                Toast.makeText(ApplicantRegistration.this, "Error "+response.code(), Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<UserImage> call2, Throwable t) {
                            Toast.makeText(ApplicantRegistration.this, "Server Not Responding!", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
            @Override
            public void onFailure(Call<Applicant> call, Throwable t) {
                Toast.makeText(ApplicantRegistration.this, "Error", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
