package Fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.ammara.FindJobs.R;

import ModelClasses.ApplicantWithImage;
import ModelClasses.Validation;

public class ContactF extends Fragment {
    private Button sendBtn;
    private EditText permanentAddress,postelAddress,mobile,phone;
    private SendContact SM;
    public ContactF() {
        // Required empty public constructor
    }
    public interface SendContact {
        void sendContactInfo(ApplicantWithImage applicantWithImage);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_contact, container, false);
    }

    public static ContactF newInstance() {
        return new ContactF();
    }
    boolean checkData(){
        if(Validation.isEmpty(postelAddress)){
            postelAddress.setError("Required");
            return false;
        }
        if(Validation.isEmpty(permanentAddress)){
            permanentAddress.setError("Required");
            return false;
        }
        if(Validation.isEmpty(mobile)){
            mobile.setError("Required");
            return false;
        }
        if(Validation.isEmpty(phone)){
            phone.setError("Required");
            return false;
        }
        return true;
    }


    public void getReferences(){
        permanentAddress = getView().findViewById(R.id.permanent_address);
        postelAddress = getView().findViewById(R.id.postal_address);
        mobile = getView().findViewById(R.id.mobile);
        phone = getView().findViewById(R.id.phone);
        sendBtn = getView().findViewById(R.id.sendBtn);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getReferences();

        sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkData()) {
                    ApplicantWithImage applicantWithImage = new ApplicantWithImage();
                    applicantWithImage.setPostal_address(postelAddress.getText().toString());
                    applicantWithImage.setPermanent_address(permanentAddress.getText().toString());
                    applicantWithImage.setPhone(phone.getText().toString());
                    applicantWithImage.setMobile(mobile.getText().toString());
                    SM.sendContactInfo(applicantWithImage);
                }
            }
        });
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            SM = (SendContact) getActivity();
        } catch (ClassCastException e) {
            throw new ClassCastException("Error in retrieving data. Please try again");
        }
    }


}
