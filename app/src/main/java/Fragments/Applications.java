package Fragments;


import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.ammara.FindJobs.R;

import java.util.List;

import Api.DataService;
import Api.RetrofitClientInstance;
import Models.Applicant;
import RVAdapters.ProfileRvAdapter;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;

/**
 * A simple {@link Fragment} subclass.
 */
public class Applications extends Fragment {
    private Spinner ads;
    private RecyclerView rv;
    private ProfileRvAdapter adapter;

    public Applications() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_applications, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        SharedPreferences prefs = getActivity().getSharedPreferences("credentials", MODE_PRIVATE);
        int advertiserId = prefs.getInt("AdvertiserId", 0);
        ads = view.findViewById(R.id.ad);
        rv = getView().findViewById(R.id.rv);
        DataService service = RetrofitClientInstance.getRetrofitInstance().create(DataService.class);
        Call<List<String>> call = service.getAdvertiserAds(advertiserId);
        call.enqueue(new Callback<List<String>>() {
            @Override
            public void onResponse(Call<List<String>> call, Response<List<String>> response) {
                if(response.isSuccessful()){
                    ArrayAdapter<String> adapter =
                            new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_dropdown_item, response.body());
                    ads.setAdapter(adapter);
                }
            }

            @Override
            public void onFailure(Call<List<String>> call, Throwable t) {

            }
        });

        ads.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selestedAd = ads.getSelectedItem().toString();
                DataService service = RetrofitClientInstance.getRetrofitInstance().create(DataService.class);
                Call<List<Applicant>> call = service.getApplications(advertiserId,selestedAd);
                call.enqueue(new Callback<List<Applicant>>() {
                    @Override
                    public void onResponse(Call<List<Applicant>> call, Response<List<Applicant>> response) {
                        if (response.isSuccessful()) {
                            List<Applicant> profiles = response.body();
                            adapter = new ProfileRvAdapter(profiles, getContext(), advertiserId);
                            rv.setAdapter(adapter);
                            rv.setLayoutManager(new LinearLayoutManager(getContext()));
                        } else {
                            Toast.makeText(getContext(), response.code() + "", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<List<Applicant>> call, Throwable t) {

                    }
                });

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }
}
