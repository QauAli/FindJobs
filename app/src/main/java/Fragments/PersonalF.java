package Fragments;

import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.creativityapps.gmailbackgroundlibrary.BackgroundMail;
import com.example.ammara.FindJobs.R;

import java.util.Calendar;
import java.util.Random;

import ModelClasses.ApplicantWithImage;
import ModelClasses.Validation;

public class PersonalF extends Fragment {
    private EditText otp, fname,lname,birth_date,cnic,father_name,email,password,retype_password;
    private Spinner province, district;
    private SendMessage SM;
    private Button sendBtn, sendOtp;
    private String otpString="1111222223333";

    public interface SendMessage {
        void sendPersonalInfo(ApplicantWithImage applicantWithImage);
    }
    public PersonalF() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_personal, container, false);
    }
    public void onViewCreated(final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getReferences();
        final ArrayAdapter<CharSequence> Kpk = ArrayAdapter.createFromResource(getActivity(),
                R.array.Kpk, android.R.layout.simple_spinner_item);
        final ArrayAdapter<CharSequence> Punjab = ArrayAdapter.createFromResource(getActivity(),
                R.array.Punjab, android.R.layout.simple_spinner_item);
        final ArrayAdapter<CharSequence> Sindh = ArrayAdapter.createFromResource(getActivity(),
                R.array.Sindh, android.R.layout.simple_spinner_item);
        final ArrayAdapter<CharSequence> Balochistan = ArrayAdapter.createFromResource(getActivity(),
                R.array.Balochistan, android.R.layout.simple_spinner_item);
        final ArrayAdapter<CharSequence> Islamabad = ArrayAdapter.createFromResource(getActivity(),
                R.array.Islamabad, android.R.layout.simple_spinner_item);
        final ArrayAdapter<CharSequence> Gilgit = ArrayAdapter.createFromResource(getActivity(),
                R.array.Gilgit, android.R.layout.simple_spinner_item);
        final ArrayAdapter<CharSequence> Kashmir = ArrayAdapter.createFromResource(getActivity(),
                R.array.Kashmir, android.R.layout.simple_spinner_item);

        sendOtp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                otpString = random();

                BackgroundMail.newBuilder(getContext())
                        .withUsername("Ammarak631@gmail.com")
                        .withPassword("ammara1234")
                        .withMailto(email.getText().toString())
                        .withType(BackgroundMail.TYPE_PLAIN)
                        .withSubject("Otp")
                        .withBody(otpString)
                        .withOnSuccessCallback(new BackgroundMail.OnSuccessCallback() {
                            @Override
                            public void onSuccess() {
                                //Toast.makeText(getContext(),,Toast.LENGTH_SHORT).show();
                                //do some magic
                            }
                        })
                        .withOnFailCallback(new BackgroundMail.OnFailCallback() {
                            @Override
                            public void onFail() {
                                //do some magic
                            }
                        })
                        .send();

            }
        });
        province.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedProvince = province.getSelectedItem().toString();

                switch (selectedProvince) {
                    case "Kpk":
                        district.setAdapter(Kpk);
                        break;
                    case "Punjab":
                        district.setAdapter(Punjab);
                        break;
                    case "Sindh":
                        district.setAdapter(Sindh);
                        break;
                    case "Balochistan":
                        district.setAdapter(Balochistan);
                        break;
                    case "Islamabad(Captial Area)":
                        district.setAdapter(Islamabad);
                        break;
                    case "Gilgit/Baltistan":
                        district.setAdapter(Gilgit);
                        break;
                    case "Kashmir":
                        district.setAdapter(Kashmir);
                        break;
                    default:
                        district.setAdapter(Kpk);
                }

                //Toast.makeText(getContext(),province.getSelectedItem().toString(),Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(checkData()){
                    if(otpString.equalsIgnoreCase(otp.getText().toString())){
                        ApplicantWithImage applicantWithImage = new ApplicantWithImage();
                        applicantWithImage.setFirst_name(fname.getText().toString());
                        applicantWithImage.setLast_name(lname.getText().toString());
                        applicantWithImage.setCnic(cnic.getText().toString());
                        applicantWithImage.setBirth_date(birth_date.getText().toString());
                        applicantWithImage.setFather_name(father_name.getText().toString());
                        applicantWithImage.setEmail(email.getText().toString());
                        applicantWithImage.setPassword(password.getText().toString());
                        applicantWithImage.setProvince(province.getSelectedItem().toString());
                        applicantWithImage.setDistrict(district.getSelectedItem().toString());
                        String temp_password=retype_password.getText().toString();
                        SM.sendPersonalInfo(applicantWithImage);
                    }else{
                        Toast.makeText(getContext(),"Invalid Otp!",Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
        birth_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //using DatePickerFragment set dob
                showDatePicker();
            }
        });
    }
    boolean checkData(){

        if(Validation.isEmpty(fname)){
            fname.setError("Required");
            return false;
        }
        if(Validation.isEmpty(lname)){
            lname.setError("Required");
            return false;
        }
        if(Validation.isEmpty(cnic)){
            cnic.setError("Required");
            return false;
        }
        if(Validation.isEmpty(birth_date)){
            birth_date.setError("Required");
            return false;
        }
        if(Validation.isEmpty(father_name)){
            father_name.setError("Required");
            return false;
        }
        if(Validation.isEmail(email)){
            email.setError("Wrong Email");
            return false;
        }
        if(Validation.isEmpty(password)){
            password.setError("Required");
            return false;
        }
        if(!retype_password.getText().toString().equalsIgnoreCase(password.getText().toString())){
            retype_password.setError("Password not match!");
            return false;
        }
        return true;
    }

    public static PersonalF newInstance() {
        return new PersonalF();
    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            SM = (SendMessage) getActivity();
        } catch (ClassCastException e) {
            throw new ClassCastException("Error in retrieving data. Please try again");
        }
    }
    DatePickerDialog.OnDateSetListener ondate = new DatePickerDialog.OnDateSetListener() {

        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {

            birth_date.setText(String.valueOf(dayOfMonth) + "-" + String.valueOf(monthOfYear+1)
                    + "-" + String.valueOf(year));
        }
    };
    private void showDatePicker() {
        DatePickerFragment date = new DatePickerFragment();
        /**
         * Set Up Current Date Into dialog
         */
        Calendar calender = Calendar.getInstance();
        Bundle args = new Bundle();
        args.putInt("year", calender.get(Calendar.YEAR));
        args.putInt("month", calender.get(Calendar.MONTH));
        args.putInt("day", calender.get(Calendar.DAY_OF_MONTH));
        date.setArguments(args);
        /**
         * Set Call back to capture selected date
         */
        date.setCallBack(ondate);
        date.show(getFragmentManager(), "Date Picker");
    }

    public void getReferences(){
        fname = getView().findViewById(R.id.first_name);
        lname = getView().findViewById(R.id.last_name);
        cnic = getView().findViewById(R.id.cnic);
        birth_date = getView().findViewById(R.id.birth_date);
        father_name = getView().findViewById(R.id.father_name);
        email = getView().findViewById(R.id.email);
        password = getView().findViewById(R.id.password);
        retype_password = getView().findViewById(R.id.password_retype);
        province = getView().findViewById(R.id.province_spinner_id);
        district = getView().findViewById(R.id.district_spinner_id);
        sendBtn = getView().findViewById(R.id.sendBtn);
        sendOtp = getView().findViewById(R.id.sendOtp);
        otp = getView().findViewById(R.id.otp);

    }
    public static String random() {
        Random generator = new Random();
        StringBuilder randomStringBuilder = new StringBuilder();
        int randomLength = generator.nextInt(8);
        char tempChar;
        for (int i = 0; i < randomLength; i++){
            tempChar = (char) (generator.nextInt(96) + 32);
            randomStringBuilder.append(tempChar);
        }
        return randomStringBuilder.toString();
    }

}
