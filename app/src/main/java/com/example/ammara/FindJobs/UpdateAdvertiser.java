package com.example.ammara.FindJobs;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.myhexaville.smartimagepicker.ImagePicker;

import java.io.File;

import ModelClasses.Advertiser;
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

public class UpdateAdvertiser extends AppCompatActivity {
    private ImagePicker imagePicker;
    private ImageView imageView;
    private Button btn;
    private EditText mobile, address, company, password;
    private FindJobService service = Client.getClient().create(FindJobService.class);
    private Advertiser advertiser;
    private int advertiserId;

    private void getReferences() {
        imageView = findViewById(R.id.profile_image);
        btn = findViewById(R.id.btn);
        mobile = findViewById(R.id.mobile);
        address = findViewById(R.id.address);
        company = findViewById(R.id.company);
        password = findViewById(R.id.password);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_advertiser);
        getReferences();
        SharedPreferences prefs = getSharedPreferences(Constants.file, MODE_PRIVATE);
        String mobilenum = prefs.getString("mobile", "No");
        String comp = prefs.getString("company", "No");
        String addres = prefs.getString("address", "No");
        String imageName = prefs.getString("imageName", "No");
        String pass = prefs.getString("AdvertiserPassword", "No");
        advertiserId = prefs.getInt("AdvertiserId", 0);

        mobile.setText(mobilenum);
        company.setText(comp);
        address.setText(addres);
        password.setText(pass);
        ImageLibrary.LoadImage(imageView, imageName, UpdateAdvertiser.this);


        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                if (imagePicker != null) {
//                    File profile_image = imagePicker.getImageFile();
                if (Validation.isEmpty(mobile)) {
                    mobile.setError("Required");
                } else if (Validation.isEmpty(address)) {
                    address.setError("Required");
                } else if (Validation.isEmpty(company)) {
                    company.setError("Required");
                }
                advertiser = new Advertiser(
                        advertiserId,
                        "",
                        "",
                        "",
                        "",
                        password.getText().toString(),
                        "",
                        "",
                        address.getText().toString(),
                        "",
                        company.getText().toString(),
                        mobile.getText().toString()
                );
                Call<Advertiser> call = service.updateAdvertiser(advertiser);
                call.enqueue(new Callback<Advertiser>() {
                    @Override
                    public void onResponse(Call<Advertiser> call, Response<Advertiser> response) {
                        if (response.isSuccessful()) {
                            if (imagePicker != null) {
                                File profile_image = imagePicker.getImageFile();
                                RequestBody imagee = RequestBody.create(MediaType.parse("image/*"), profile_image);
                                RequestBody idd = RequestBody.create(MediaType.parse("text/plain"), "" + advertiserId);
                                Call<UserImage> call2 = service.createAdvertiserImage(imagee, idd);
                                call2.enqueue(new Callback<UserImage>() {
                                    @Override
                                    public void onResponse(Call<UserImage> call, Response<UserImage> response) {
                                        if (response.isSuccessful()) {
                                            Toast.makeText(UpdateAdvertiser.this, "Updated!", Toast.LENGTH_SHORT).show();
                                            Intent myIntent = new Intent(UpdateAdvertiser.this, AdvertiserCycle.class);
                                            myIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                            UpdateAdvertiser.this.startActivity(myIntent);
                                        } else {
                                            Toast.makeText(UpdateAdvertiser.this, response.code() + " ", Toast.LENGTH_SHORT).show();
                                        }
                                    }

                                    @Override
                                    public void onFailure(Call<UserImage> call, Throwable t) {

                                    }
                                });
                            }
                            else {
                                Toast.makeText(UpdateAdvertiser.this, "Updated!", Toast.LENGTH_SHORT).show();
                                Intent myIntent = new Intent(UpdateAdvertiser.this, AdvertiserCycle.class);
                                myIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                UpdateAdvertiser.this.startActivity(myIntent);
                            }
                            //Toast.makeText(AdvertiserRegistration.this,response.body().getId()+"",Toast.LENGTH_SHORT).show();
                        }

                    }

                    @Override
                    public void onFailure(Call<Advertiser> call, Throwable t) {
                        Toast.makeText(UpdateAdvertiser.this, "Error", Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });


        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(AdvertiserRegistration.this,"clicked",Toast.LENGTH_SHORT).show();
                imagePicker = new ImagePicker(UpdateAdvertiser.this,
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
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        imagePicker.handleActivityResult(resultCode, requestCode, data);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        imagePicker.handlePermission(requestCode, grantResults);
    }
}
