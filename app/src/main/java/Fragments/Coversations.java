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

import RestfullServices.FindJobService;
import Adapters.ConsAdapter;

import com.example.ammara.FindJobs.Constants;
import com.example.ammara.FindJobs.R;

import java.util.List;

import RestfullServices.Client;
import ModelClasses.Conversation;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;

public class Coversations extends Fragment {

    private ConsAdapter adapter;
    private Conversation con;
    private RecyclerView rv;
    private FindJobService service = Client.getClient().create(FindJobService.class);
    public Coversations() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_coversations_applicant, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();
        refreshCons();

    }

    private void refreshCons() {
        SharedPreferences prefs = getActivity().getSharedPreferences(Constants.file, MODE_PRIVATE);
        int ApplicantId = prefs.getInt("ApplicantId", 0);
        int AdvertiserId = prefs.getInt("AdvertiserId", 0);
        if(ApplicantId == 0){
            // Replace getterId with current userId
            Call<List<Conversation>> call = service.getAdvertiserCons(AdvertiserId);
            call.enqueue(new Callback<List<Conversation>>() {
                @Override
                public void onResponse(Call<List<Conversation>> call, Response<List<Conversation>> response) {
                    if (response.isSuccessful()) {
                        List<Conversation> conversations = response.body();
                        adapter = new ConsAdapter(conversations, getContext());
                        rv.setAdapter(adapter);
                        rv.setLayoutManager(new LinearLayoutManager(getContext()));
                    } else {
                        Toast.makeText(getContext(), response.code() + "", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<List<Conversation>> call, Throwable t) {

                }
            });
        }else if(AdvertiserId == 0){
            // Replace getterId with current userId
            Call<List<Conversation>> call = service.getApplicantCons(ApplicantId);
            call.enqueue(new Callback<List<Conversation>>() {
                @Override
                public void onResponse(Call<List<Conversation>> call, Response<List<Conversation>> response) {
                    if (response.isSuccessful()) {
                        List<Conversation> conversations = response.body();
                        adapter = new ConsAdapter(conversations, getContext());
                        rv.setAdapter(adapter);
                        rv.setLayoutManager(new LinearLayoutManager(getContext()));
                    } else {
                        Toast.makeText(getContext(), response.code() + "", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<List<Conversation>> call, Throwable t) {

                }
            });
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        rv = view.findViewById(R.id.rv);
    }
}
