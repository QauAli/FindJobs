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
import android.widget.Toast;

import com.example.ammara.FindJobs.Constants;
import com.example.ammara.FindJobs.R;

import java.util.List;

import Adapters.AdvertisementAdapter;
import ModelClasses.Advertisement;
import RestfullServices.Client;
import RestfullServices.FindJobService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;

public class Wishlist extends Fragment {
    private RecyclerView rv;
    private AdvertisementAdapter adapter;
    private FindJobService service = Client.getClient().create(FindJobService.class);


    public Wishlist() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_wishlist, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        rv = getView().findViewById(R.id.rv);
        SharedPreferences prefs = getContext().getSharedPreferences(Constants.file, MODE_PRIVATE);
        int applicantId = prefs.getInt("ApplicantId", 0);
        Call<List<Advertisement>> call = service.getAds(applicantId,0);
        call.enqueue(new Callback<List<Advertisement>>() {
            @Override
            public void onResponse(Call<List<Advertisement>> call, Response<List<Advertisement>> response) {
                if (response.isSuccessful()) {
                    List<Advertisement> advertisements = response.body();
//                            for (int i = 0; i < advertisements.size(); i++) {
//                                Toast.makeText(getContext(), advertisements.get(0).getTitle(), Toast.LENGTH_SHORT).show();
//                            }
                    adapter = new AdvertisementAdapter(advertisements, getContext(),0);
                    rv.setAdapter(adapter);
                    rv.setLayoutManager(new LinearLayoutManager(getContext()));
                } else {
                    Toast.makeText(getContext(), response.code() + "", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Advertisement>> call, Throwable t) {

            }
        });


    }
}
