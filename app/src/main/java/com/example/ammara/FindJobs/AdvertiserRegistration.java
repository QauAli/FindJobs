package com.example.ammara.FindJobs;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.congfandi.simpledatepicker.Picker;
import com.myhexaville.smartimagepicker.ImagePicker;

import java.io.File;

import Api.DataService;
import Api.RetrofitClientInstance;
import Models.Advertiser;
import Models.UserImage;
import Models.Validation;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdvertiserRegistration extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {
    private ImagePicker imagePicker;
    private ImageView imageView;
    private Spinner province, district, gender;
    private Button btn;
    private EditText name, cnic, birth_date, mobile, address, company, email, password, retype_password;

    private void getReferences() {
        imageView = findViewById(R.id.profile_image);
        btn = findViewById(R.id.btn);
        province = findViewById(R.id.province_spinner_id);
        district = findViewById(R.id.district_spinner_id);
        gender = findViewById(R.id.gender);
        name = findViewById(R.id.name);
        email = findViewById(R.id.email);
        cnic = findViewById(R.id.cnic);
        birth_date = findViewById(R.id.birth_date);
        mobile = findViewById(R.id.mobile);
        address = findViewById(R.id.address);
        company = findViewById(R.id.company);
        password = findViewById(R.id.password);
        retype_password = findViewById(R.id.password_retype);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_advertiser_registration);

        getReferences();

        final ArrayAdapter<CharSequence> Kpk = ArrayAdapter.createFromResource(AdvertiserRegistration.this,
                R.array.Kpk, android.R.layout.simple_spinner_item);
        final ArrayAdapter<CharSequence> Punjab = ArrayAdapter.createFromResource(AdvertiserRegistration.this,
                R.array.Punjab, android.R.layout.simple_spinner_item);
        final ArrayAdapter<CharSequence> Sindh = ArrayAdapter.createFromResource(AdvertiserRegistration.this,
                R.array.Sindh, android.R.layout.simple_spinner_item);
        final ArrayAdapter<CharSequence> Balochistan = ArrayAdapter.createFromResource(AdvertiserRegistration.this,
                R.array.Balochistan, android.R.layout.simple_spinner_item);
        final ArrayAdapter<CharSequence> Islamabad = ArrayAdapter.createFromResource(AdvertiserRegistration.this,
                R.array.Islamabad, android.R.layout.simple_spinner_item);
        final ArrayAdapter<CharSequence> Gilgit = ArrayAdapter.createFromResource(AdvertiserRegistration.this,
                R.array.Gilgit, android.R.layout.simple_spinner_item);
        final ArrayAdapter<CharSequence> Kashmir = ArrayAdapter.createFromResource(AdvertiserRegistration.this,
                R.array.Kashmir, android.R.layout.simple_spinner_item);

        birth_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Picker().show(getSupportFragmentManager(), null);
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
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (imagePicker != null) {
                    File profile_image = imagePicker.getImageFile();
                    if (Validation.isEmpty(name)) {
                        name.setError("Required");
                    } else if (Validation.isEmpty(cnic)) {
                        cnic.setError("Required");
                    } else if (Validation.isEmpty(birth_date)) {
                        birth_date.setError("Required");
                    } else if (Validation.isEmpty(email) || Validation.isEmail(email)) {
                        email.setError("Wrong Email");
                    } else if (Validation.isEmpty(password)) {
                        password.setError("Required");
                    } else if (Validation.isEmpty(retype_password)) {
                        retype_password.setError("Required");
                    } else if (Validation.isEmpty(mobile)) {
                        mobile.setError("Required");
                    } else if (Validation.isEmpty(address)) {
                        address.setError("Required");
                    } else if (Validation.isEmpty(company)) {
                        company.setError("Required");
                    }else if (!password.getText().toString().equalsIgnoreCase(retype_password.getText().toString())) {
                        retype_password.setError("password not match");
                    } else if (password.getText().toString().equalsIgnoreCase(retype_password.getText().toString())) {
                        DataService service = RetrofitClientInstance.getRetrofitInstance().create(DataService.class);
                        Call<Advertiser> call = service.createAdvertiser(new Advertiser(
                                0,
                                name.getText().toString(),
                                cnic.getText().toString(),
                                birth_date.getText().toString(),
                                email.getText().toString(),
                                password.getText().toString(),
                                province.getSelectedItem().toString(),
                                district.getSelectedItem().toString(),
                                address.getText().toString(),
                                gender.getSelectedItem().toString(),
                                company.getText().toString(),
                                mobile.getText().toString()
                        ));
                        call.enqueue(new Callback<Advertiser>() {
                            @Override
                            public void onResponse(Call<Advertiser> call, Response<Advertiser> response) {
                                if (response.isSuccessful()) {
                                    int id = response.body().getId();
                                    RequestBody imagee = RequestBody.create(MediaType.parse("image/*"), profile_image);
                                    RequestBody idd = RequestBody.create(MediaType.parse("text/plain"), "" + id);
                                    Call<UserImage> call2 = service.createAdvertiserImage(imagee, idd);
                                    call2.enqueue(new Callback<UserImage>() {
                                        @Override
                                        public void onResponse(Call<UserImage> call, Response<UserImage> response) {
                                            if (response.isSuccessful()) {
                                                Toast.makeText(AdvertiserRegistration.this, "Registration Complete", Toast.LENGTH_SHORT).show();
                                                Intent myIntent = new Intent(AdvertiserRegistration.this, Login.class);
                                                myIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                                AdvertiserRegistration.this.startActivity(myIntent);
                                            } else {
                                                Toast.makeText(AdvertiserRegistration.this, response.code() + " ", Toast.LENGTH_SHORT).show();
                                            }
                                        }

                                        @Override
                                        public void onFailure(Call<UserImage> call, Throwable t) {

                                        }
                                    });
                                    //Toast.makeText(AdvertiserRegistration.this,response.body().getId()+"",Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onFailure(Call<Advertiser> call, Throwable t) {
                                Toast.makeText(AdvertiserRegistration.this, "Error", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }

                }
            }
        });


        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(AdvertiserRegistration.this,"clicked",Toast.LENGTH_SHORT).show();
                imagePicker = new ImagePicker(AdvertiserRegistration.this,
                        null,
                        imageUri -> {/*on image picked */
                            imageView.setImageURI(imageUri);
                        })
                        .setWithImageCrop(
                                1,/*aspect ratio x*/
                                1 /*aspect ratio y*/);
                imagePicker.choosePicture(true);
            }
        });
    }

    @Override
    public void onDateSet(DatePicker datePicker, int year, int month, int day) {

        String selected_year = Integer.toString(year);
        String selected_month = Integer.toString(month + 1);
        String selected_day = Integer.toString(day);

        String birthDate = selected_day + '-' + selected_month + '-' + selected_year;

        birth_date.setText(birthDate);


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
