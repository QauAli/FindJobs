package Fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;

import com.example.ammara.FindJobs.R;
import com.myhexaville.smartimagepicker.ImagePicker;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import Api.DataService;
import Api.RetrofitClientInstance;
import Models.ApplicantWithImage;
import Models.Education;
import Models.Skill;
import Models.Validation;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EducationFragment extends Fragment {
    private ImageView imageView;
    private static final int REQUEST_CAPTURE_IMAGE = 100;
    private EducationFragment.SendEducation SE;
    private ArrayList<Education> ApplicantEducation = new ArrayList<>();
    private LinkedList<String> selectedSkills = new LinkedList<>();

    public interface SendEducation {
        void sendEducationInfo(ApplicantWithImage applicantWithImage);
    }

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

    public EducationFragment() {
        // Required empty public constructor
    }

    public static EducationFragment newInstance() {
        return new EducationFragment();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_education, container, false);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            SE = (EducationFragment.SendEducation) getActivity();
        } catch (ClassCastException e) {
            throw new ClassCastException("Error in retrieving data. Please try again");
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        getReferences();
        DataService service = RetrofitClientInstance.getRetrofitInstance().create(DataService.class);
        Call<List<Skill>> call = service.getAllSkills();
        call.enqueue(new Callback<List<Skill>>() {
            @Override
            public void onResponse(Call<List<Skill>> call, Response<List<Skill>> response) {
                skills = response.body();
                if (response.isSuccessful()) {
                    TableLayout my_layout = getView().findViewById(R.id.skills_layout);

                    for (int i = 0; i < skills.size(); i++) {
                        TableRow row = new TableRow(getContext());
                        row.setId(i);
                        row.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT));
                        CheckBox checkBox = new CheckBox(getContext());
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
                if (imagePicker != null) {
                    ApplicantEducation.clear();
                    ApplicantWithImage applicantWithImage = new ApplicantWithImage();
                    applicantWithImage.setJob_status(job_status.getSelectedItem().toString());
                    applicantWithImage.setProfile_image(imagePicker.getImageFile());

                    checkEligibility("ssc", sscYear, sscGrade);
                    checkEligibility("hssc", hsscYear, hsscGrade);
                    checkEligibility("bsc", bscYear, bscGrade);
                    checkEligibility("msc", mscYear, mscGrade);
                    checkEligibility("mphil", mphilYear, mphilGrade);
                    checkEligibility("bs", bsYear, bsGrade);
                    checkEligibility("ms", msYear, msGrade);
                    checkEligibility("Diploma", diplomaYear, diplomaTitle);

                    applicantWithImage.setSkills(selectedSkills);

                    applicantWithImage.setEducations(ApplicantEducation);

                    applicantWithImage.setExperianceDetails(experianceDiscription.getText().toString());

                    SE.sendEducationInfo(applicantWithImage);
                }
            }
        });

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // showPictureDialog();
                imagePicker = new ImagePicker(getActivity(),
                        EducationFragment.this,
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
        next = getView().findViewById(R.id.education_next_btn);
        job_status = getView().findViewById(R.id.job_status);
        education_status = getView().findViewById(R.id.education_status);

        //table rows
        ssc_row = getView().findViewById(R.id.ssc);
        hssc_row = getView().findViewById(R.id.hssc);
        bsc_row = getView().findViewById(R.id.bsc);
        bs_row = getView().findViewById(R.id.bs);
        msc_row = getView().findViewById(R.id.msc);
        mphil_row = getView().findViewById(R.id.mphil);
        ms_row = getView().findViewById(R.id.ms);
        diploma_row = getView().findViewById(R.id.diploma_row);
        experience_row = getView().findViewById(R.id.experience_row);


        //imageView
        imageView = getView().findViewById(R.id.image_id);

        //checkboxes
        diploma_check = getView().findViewById(R.id.diploma_check);
        experience_check = getView().findViewById(R.id.experience_check);


        //educational
        sscYear = getView().findViewById(R.id.matric_year);
        sscGrade = getView().findViewById(R.id.matric_grades);
        hsscYear = getView().findViewById(R.id.hssc_year);
        hsscGrade = getView().findViewById(R.id.hssc_grade);
        bscYear = getView().findViewById(R.id.bsc_year);
        bscGrade = getView().findViewById(R.id.bsc_grade);
        mscYear = getView().findViewById(R.id.msc_year);
        mscGrade = getView().findViewById(R.id.msc_grade);
        mphilYear = getView().findViewById(R.id.mphil_year);
        mphilGrade = getView().findViewById(R.id.mphil_grade);
        bsYear = getView().findViewById(R.id.bs_year);
        bsGrade = getView().findViewById(R.id.bs_grade);
        msYear = getView().findViewById(R.id.ms_year);
        msGrade = getView().findViewById(R.id.ms_grade);
        //diploma
        diplomaYear = getView().findViewById(R.id.diploma_year);
        diplomaTitle = getView().findViewById(R.id.diploma_title);

        //experience
        experianceDiscription = getView().findViewById(R.id.experience_info);


    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        imagePicker.handleActivityResult(resultCode, requestCode, data);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        imagePicker.handlePermission(requestCode, grantResults);
    }
}
