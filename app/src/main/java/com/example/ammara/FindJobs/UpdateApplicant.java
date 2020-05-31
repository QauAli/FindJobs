package com.example.ammara.FindJobs;

import android.content.Intent;
import android.content.SharedPreferences;
import android.media.Image;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.Toast;

import com.myhexaville.smartimagepicker.ImagePicker;

import java.io.File;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import Fragments.EducationF;
import ModelClasses.Applicant;
import ModelClasses.ApplicantWithImage;
import ModelClasses.Education;
import ModelClasses.Skill;
import ModelClasses.UserImage;
import ModelClasses.Validation;
import RestfullServices.Client;
import RestfullServices.FindJobService;
import RestfullServices.ImageLibrary;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UpdateApplicant extends AppCompatActivity {
    private ImageView imageView;
    private int applicantId;
    private static final int REQUEST_CAPTURE_IMAGE = 100;
    private ArrayList<Education> ApplicantEducation = new ArrayList<>();
    private LinkedList<String> selectedSkills = new LinkedList<>();
    private FindJobService service = Client.getClient().create(FindJobService.class);
    private Spinner job_status, education_status;
    private TableRow ssc_row, hssc_row, bsc_row, msc_row,
            mphil_row, bs_row, ms_row, experience_row, diploma_row;
    private CheckBox diploma_check, experience_check;
    private Button next;
    private List<Skill> skills;// = new ArrayList<Skill>();
    private ImagePicker imagePicker;

    private EditText sscYear, sscGrade,
            hsscYear, hsscGrade,
            bscYear, bscGrade,
            mscYear, mscGrade,
            mphilYear, mphilGrade,
            bsYear, bsGrade,
            msYear, msGrade,
            diplomaYear, diplomaTitle,
            experianceDiscription;
    private EditText postelAddress, password, mobile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_applicant);
        getReferences();

        SharedPreferences prefs = getSharedPreferences(Constants.file, MODE_PRIVATE);
        String mobilenum = prefs.getString("mobile", "No");
        String postal_address = prefs.getString("postal address", "No");
        String imageName = prefs.getString("imageName", "No");
        String pass = prefs.getString("ApplicantPassword", "No");
        applicantId = prefs.getInt("ApplicantId", 0);

        ImageLibrary.LoadImage(imageView, imageName, UpdateApplicant.this);
        postelAddress.setText(postal_address);
        mobile.setText(mobilenum);
        password.setText(pass);


        Call<List<Skill>> call = service.getAllSkills();
        call.enqueue(new Callback<List<Skill>>() {
            @Override
            public void onResponse(Call<List<Skill>> call, Response<List<Skill>> response) {
                skills = response.body();
                if (response.isSuccessful()) {
                    TableLayout my_layout = findViewById(R.id.skills_layout);

                    for (int i = 0; i < skills.size(); i++) {
                        TableRow row = new TableRow(UpdateApplicant.this);
                        row.setId(i);
                        row.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT));
                        CheckBox checkBox = new CheckBox(UpdateApplicant.this);
                        checkBox.setId(skills.get(i).getId());
                        checkBox.setText(skills.get(i).getName());
                        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                            @Override
                            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                                if (isChecked) {
                                    //Toast.makeText(getContext(),buttonView.getText()+" checked", Toast.LENGTH_SHORT).show();
                                    selectedSkills.add(buttonView.getText().toString());
                                } else {
                                    //Toast.makeText(getContext(),buttonView.getText()+" Unchecked",Toast.LENGTH_SHORT).show();
                                    if (selectedSkills.size() == 1) {
                                        selectedSkills.clear();
                                    }
                                    selectedSkills.remove(new String(buttonView.getText().toString()));

                                }
                            }
                        });
                        row.addView(checkBox);
                        my_layout.addView(row);
                    }
                }
            }

            @Override
            public void onFailure(Call<List<Skill>> call, Throwable t) {

            }
        });

        education_status.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String education = education_status.getSelectedItem().toString();

                switch (education) {
                    case "Middle":
                        ssc_row.setVisibility(View.GONE);
                        hssc_row.setVisibility(View.GONE);
                        bsc_row.setVisibility(View.GONE);
                        msc_row.setVisibility(View.GONE);
                        mphil_row.setVisibility(View.GONE);
                        bs_row.setVisibility(View.GONE);
                        ms_row.setVisibility(View.GONE);
                        //clear fields
                        sscYear.getText().clear();
                        sscGrade.getText().clear();
                        hsscYear.getText().clear();
                        hsscGrade.getText().clear();
                        bscYear.getText().clear();
                        bscGrade.getText().clear();
                        mscYear.getText().clear();
                        mscGrade.getText().clear();
                        mphilYear.getText().clear();
                        mphilGrade.getText().clear();
                        bsYear.getText().clear();
                        bsGrade.getText().clear();
                        msYear.getText().clear();
                        msGrade.getText().clear();
                        break;
                    case "Matric":
                        ssc_row.setVisibility(View.VISIBLE);
                        hssc_row.setVisibility(View.GONE);
                        bsc_row.setVisibility(View.GONE);
                        msc_row.setVisibility(View.GONE);
                        mphil_row.setVisibility(View.GONE);
                        bs_row.setVisibility(View.GONE);
                        ms_row.setVisibility(View.GONE);
                        //clear fields
                        sscYear.getText().clear();
                        sscGrade.getText().clear();
                        hsscYear.getText().clear();
                        hsscGrade.getText().clear();
                        bscYear.getText().clear();
                        bscGrade.getText().clear();
                        mscYear.getText().clear();
                        mscGrade.getText().clear();
                        mphilYear.getText().clear();
                        mphilGrade.getText().clear();
                        bsYear.getText().clear();
                        bsGrade.getText().clear();
                        msYear.getText().clear();
                        msGrade.getText().clear();

                        break;
                    case "HSSC":
                        ssc_row.setVisibility(View.VISIBLE);
                        hssc_row.setVisibility(View.VISIBLE);
                        bsc_row.setVisibility(View.GONE);
                        msc_row.setVisibility(View.GONE);
                        mphil_row.setVisibility(View.GONE);
                        bs_row.setVisibility(View.GONE);
                        ms_row.setVisibility(View.GONE);
                        //clear fields
                        hsscYear.getText().clear();
                        hsscGrade.getText().clear();
                        bscYear.getText().clear();
                        bscGrade.getText().clear();
                        mscYear.getText().clear();
                        mscGrade.getText().clear();
                        mphilYear.getText().clear();
                        mphilGrade.getText().clear();
                        bsYear.getText().clear();
                        bsGrade.getText().clear();
                        msYear.getText().clear();
                        msGrade.getText().clear();

                        break;
                    case "BSC/BA":
                        ssc_row.setVisibility(View.VISIBLE);
                        hssc_row.setVisibility(View.VISIBLE);
                        bsc_row.setVisibility(View.VISIBLE);
                        msc_row.setVisibility(View.GONE);
                        mphil_row.setVisibility(View.GONE);
                        bs_row.setVisibility(View.GONE);
                        ms_row.setVisibility(View.GONE);
                        //clear fields
                        bscYear.getText().clear();
                        bscGrade.getText().clear();
                        mscYear.getText().clear();
                        mscGrade.getText().clear();
                        mphilYear.getText().clear();
                        mphilGrade.getText().clear();
                        bsYear.getText().clear();
                        bsGrade.getText().clear();
                        msYear.getText().clear();
                        msGrade.getText().clear();

                        break;
                    case "MSC/MA":
                        ssc_row.setVisibility(View.VISIBLE);
                        hssc_row.setVisibility(View.VISIBLE);
                        bsc_row.setVisibility(View.VISIBLE);
                        msc_row.setVisibility(View.VISIBLE);
                        mphil_row.setVisibility(View.GONE);
                        bs_row.setVisibility(View.GONE);
                        ms_row.setVisibility(View.GONE);
                        //clear fields
                        mphilYear.getText().clear();
                        mphilGrade.getText().clear();
                        bsYear.getText().clear();
                        bsGrade.getText().clear();
                        msYear.getText().clear();
                        msGrade.getText().clear();
                        break;
                    case "Mphil":
                        ssc_row.setVisibility(View.VISIBLE);
                        hssc_row.setVisibility(View.VISIBLE);
                        bsc_row.setVisibility(View.VISIBLE);
                        msc_row.setVisibility(View.VISIBLE);
                        mphil_row.setVisibility(View.VISIBLE);
                        bs_row.setVisibility(View.GONE);
                        ms_row.setVisibility(View.GONE);
                        //clear fields
                        mphilYear.getText().clear();
                        mphilGrade.getText().clear();
                        bsYear.getText().clear();
                        bsGrade.getText().clear();
                        msYear.getText().clear();
                        msGrade.getText().clear();
                        break;
                    case "BS":
                        ssc_row.setVisibility(View.VISIBLE);
                        hssc_row.setVisibility(View.VISIBLE);
                        bsc_row.setVisibility(View.GONE);
                        msc_row.setVisibility(View.GONE);
                        mphil_row.setVisibility(View.GONE);
                        bs_row.setVisibility(View.VISIBLE);
                        ms_row.setVisibility(View.GONE);
                        //clear fields
                        bscYear.getText().clear();
                        bscGrade.getText().clear();
                        mscYear.getText().clear();
                        mscGrade.getText().clear();
                        mphilYear.getText().clear();
                        mphilGrade.getText().clear();
                        bsYear.getText().clear();
                        bsGrade.getText().clear();
                        msYear.getText().clear();
                        msGrade.getText().clear();
                        break;
                    case "MS":
                        ssc_row.setVisibility(View.VISIBLE);
                        hssc_row.setVisibility(View.VISIBLE);
                        bsc_row.setVisibility(View.GONE);
                        msc_row.setVisibility(View.GONE);
                        mphil_row.setVisibility(View.GONE);
                        bs_row.setVisibility(View.VISIBLE);
                        ms_row.setVisibility(View.VISIBLE);
                        //clear fields
                        bscYear.getText().clear();
                        bscGrade.getText().clear();
                        mscYear.getText().clear();
                        mscGrade.getText().clear();
                        mphilYear.getText().clear();
                        mphilGrade.getText().clear();
                        msYear.getText().clear();
                        msGrade.getText().clear();
                        break;
                    default:
                        break;
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ApplicantWithImage applicantWithImage = new ApplicantWithImage();
                applicantWithImage.setJob_status(job_status.getSelectedItem().toString());
                ApplicantEducation.clear();
//                if (imagePicker != null)
//                    applicantWithImage.setProfile_image(imagePicker.getImageFile());

                checkEligibility("ssc", sscYear, sscGrade);
                checkEligibility("hssc", hsscYear, hsscGrade);
                checkEligibility("bsc", bscYear, bscGrade);
                checkEligibility("msc", mscYear, mscGrade);
                checkEligibility("mphil", mphilYear, mphilGrade);
                checkEligibility("bs", bsYear, bsGrade);
                checkEligibility("ms", msYear, msGrade);
                checkEligibility("Diploma", diplomaYear, diplomaTitle);

                applicantWithImage.setSkills(selectedSkills);
                applicantWithImage.setPostal_address(postelAddress.getText().toString());
                applicantWithImage.setMobile(mobile.getText().toString());

                applicantWithImage.setEducations(ApplicantEducation);

                applicantWithImage.setExperianceDetails(experianceDiscription.getText().toString());

//                if(imagePicker!=null)
//                    File profileImg = applicantWithImage.getProfile_image();

                Applicant applicant = new Applicant("",
                        "",
                        "",
                        "",
                        "",
                        "",
                        password.getText().toString(),
                        "",
                        "",
                        applicantWithImage.getPostal_address(),
                        "",
                        applicantWithImage.getMobile(),
                        "",
                        applicantWithImage.getJob_status(),
                        applicantWithImage.getEducations(),
                        applicantWithImage.getSkills(),
                        applicantWithImage.getExperianceDetails()
                );
                applicant.setId(applicantId);
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
                Call<Applicant> call = service.updateApplicant(applicant);
                call.enqueue(new Callback<Applicant>() {
                    @Override
                    public void onResponse(Call<Applicant> call, Response<Applicant> response) {
                        if (response.isSuccessful()) {
                            //Toast.makeText(ApplicantRegistration.this, response.body().getId() + "", Toast.LENGTH_SHORT).show();
                            if(imagePicker != null){
                                RequestBody imagee = RequestBody.create(MediaType.parse("image/*"), imagePicker.getImageFile());
                                RequestBody idd = RequestBody.create(MediaType.parse("text/plain"), "" + applicantId);
                                Call<UserImage> call2 = service.createApplicantImage(imagee, idd);
                                call2.enqueue(new Callback<UserImage>() {
                                    @Override
                                    public void onResponse(Call<UserImage> call2, Response<UserImage> response) {
                                        if (response.isSuccessful()) {
                                            Toast.makeText(UpdateApplicant.this, "Updated", Toast.LENGTH_SHORT).show();
                                            Intent myIntent = new Intent(UpdateApplicant.this, ApplicantCycle.class);
                                            myIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                            UpdateApplicant.this.startActivity(myIntent);
                                        }
                                    }

                                    @Override
                                    public void onFailure(Call<UserImage> call2, Throwable t) {
                                        Toast.makeText(UpdateApplicant.this, "Server Not Responding!", Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }
                            Toast.makeText(UpdateApplicant.this, "Updated", Toast.LENGTH_SHORT).show();
                            Intent myIntent = new Intent(UpdateApplicant.this, ApplicantCycle.class);
                            myIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            UpdateApplicant.this.startActivity(myIntent);
                        }
                    }

                    @Override
                    public void onFailure(Call<Applicant> call, Throwable t) {
                        Toast.makeText(UpdateApplicant.this, "Server Not Responding!", Toast.LENGTH_SHORT).show();
                    }
                });

                //SE.sendEducationInfo(applicantWithImage);

            }
        });

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // showPictureDialog();
                imagePicker = new ImagePicker(UpdateApplicant.this,
                        null,
                        imageUri -> {
                            imageView.setImageURI(imageUri);
                        }).setWithImageCrop(
                        1,
                        1);
                imagePicker.choosePicture(true);
            }
        });

        diploma_check.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    diploma_row.setVisibility(View.VISIBLE);
                } else {
                    diploma_row.setVisibility(View.GONE);
                    diplomaYear.getText().clear();
                    diplomaTitle.getText().clear();
                }


            }
        });
        experience_check.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked)
                    experience_row.setVisibility(View.VISIBLE);
                else
                    experience_row.setVisibility(View.GONE);
            }
        });


    }

    private void checkEligibility(String title, EditText year, EditText grade) {
//        Toast.makeText(getContext(),title+year.getText().toString()+
//                year.getText().toString(),Toast.LENGTH_SHORT).show();
        if (Validation.isEmpty(year) || Validation.isEmpty(grade)) {
            return;
        } else {
            Education education = new Education(title,
                    year.getText().toString(),
                    grade.getText().toString());
            ApplicantEducation.add(education);
            return;
        }
    }

    public void getReferences() {
        next = findViewById(R.id.education_next_btn);
        password = findViewById(R.id.password);
        job_status = findViewById(R.id.job_status);
        education_status = findViewById(R.id.education_status);

        //table rows
        ssc_row = findViewById(R.id.ssc);
        hssc_row = findViewById(R.id.hssc);
        bsc_row = findViewById(R.id.bsc);
        bs_row = findViewById(R.id.bs);
        msc_row = findViewById(R.id.msc);
        mphil_row = findViewById(R.id.mphil);
        ms_row = findViewById(R.id.ms);
        diploma_row = findViewById(R.id.diploma_row);
        experience_row = findViewById(R.id.experience_row);


        //imageView
        imageView = findViewById(R.id.image_id);

        //checkboxes
        diploma_check = findViewById(R.id.diploma_check);
        experience_check = findViewById(R.id.experience_check);


        //educational
        sscYear = findViewById(R.id.matric_year);
        sscGrade = findViewById(R.id.matric_grades);
        hsscYear = findViewById(R.id.hssc_year);
        hsscGrade = findViewById(R.id.hssc_grade);
        bscYear = findViewById(R.id.bsc_year);
        bscGrade = findViewById(R.id.bsc_grade);
        mscYear = findViewById(R.id.msc_year);
        mscGrade = findViewById(R.id.msc_grade);
        mphilYear = findViewById(R.id.mphil_year);
        mphilGrade = findViewById(R.id.mphil_grade);
        bsYear = findViewById(R.id.bs_year);
        bsGrade = findViewById(R.id.bs_grade);
        msYear = findViewById(R.id.ms_year);
        msGrade = findViewById(R.id.ms_grade);
        //diploma
        diplomaYear = findViewById(R.id.diploma_year);
        diplomaTitle = findViewById(R.id.diploma_title);

        //experience
        experianceDiscription = findViewById(R.id.experience_info);

        postelAddress = findViewById(R.id.postal_address);
        mobile = findViewById(R.id.mobile);


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        imagePicker.handleActivityResult(resultCode, requestCode, data);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        imagePicker.handlePermission(requestCode, grantResults);
    }
}
