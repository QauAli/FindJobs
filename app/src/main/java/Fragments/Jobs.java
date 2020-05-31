package Fragments;


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

import Adapters.AdvertisementAdapter;
import com.example.ammara.FindJobs.R;

import java.util.List;

import RestfullServices.FindJobService;
import RestfullServices.Client;
import ModelClasses.Advertisement;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Jobs extends Fragment {

    private Spinner province, district;

    private RecyclerView rv;
    private AdvertisementAdapter adapter;
    private FindJobService service = Client.getClient().create(FindJobService.class);


    public Jobs() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_jobs, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
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
                    case "GilgitBaltistan":
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

        district.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Call<List<Advertisement>> call = service.getAllAds(province.getSelectedItem().toString(),
                        district.getSelectedItem().toString());
                call.enqueue(new Callback<List<Advertisement>>() {
                    @Override
                    public void onResponse(Call<List<Advertisement>> call, Response<List<Advertisement>> response) {
                        if (response.isSuccessful()) {
                            List<Advertisement> advertisements = response.body();
//                            for (int i = 0; i < advertisements.size(); i++) {
//                                Toast.makeText(getContext(), advertisements.get(0).getTitle(), Toast.LENGTH_SHORT).show();
//                            }
                            adapter = new AdvertisementAdapter(advertisements, getContext(),1);
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

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void getReferences() {
        rv = getView().findViewById(R.id.rv);
        province = getView().findViewById(R.id.province);
        district = getView().findViewById(R.id.district);
    }
}
