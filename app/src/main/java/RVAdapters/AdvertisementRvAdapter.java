package RVAdapters;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ammara.FindJobs.AdvertisementSkillsRvAdapter;
import com.example.ammara.FindJobs.R;

import java.util.List;

import Api.DataService;
import Api.RetrofitClientInstance;
import Models.Advertisement;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;

public class AdvertisementRvAdapter
        extends RecyclerView.Adapter<AdvertisementRvAdapter.AdvertisementRvHolder> {
    private final List<Advertisement> advertisements;
    //private String program;
    private LayoutInflater layoutInflater;
    private Context context;

    public AdvertisementRvAdapter(List<Advertisement> advertisements, Context context) {
        this.context = context;
        this.advertisements = advertisements;
        //this.program = program;
        layoutInflater = LayoutInflater.from(context);
    }
    @NonNull
    @Override
    public AdvertisementRvHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = layoutInflater.inflate(R.layout.one_advertisement, viewGroup, false);
        return new AdvertisementRvHolder(itemView, this);
    }
    
    @Override
    public void onBindViewHolder(@NonNull AdvertisementRvHolder rvHolder, final int i) {
        //rvHolder.id.setText(Integer.toString(advertisements.get(i).getId()));
        rvHolder.adId = advertisements.get(i).getId();
        rvHolder.title.setText(advertisements.get(i).getTitle());
        rvHolder.province.setText(advertisements.get(i).getProvince());
        rvHolder.district.setText(advertisements.get(i).getDistrict());
        rvHolder.education.setText(advertisements.get(i).getEducation());
        AdvertisementSkillsRvAdapter childAdapter = new AdvertisementSkillsRvAdapter(advertisements.get(i).getSkills(),this.context);
        rvHolder.childRv.setAdapter(childAdapter);
        rvHolder.childRv.setLayoutManager(new LinearLayoutManager(this.context));
        rvHolder.last_date.setText(advertisements.get(i).getLast_date());

    }

    @Override
    public int getItemCount() {
        return advertisements.size();
    }

    public class AdvertisementRvHolder
            extends RecyclerView.ViewHolder{
        final AdvertisementRvAdapter adapter;
        public final TextView title,province,district,education,last_date;
        public final Button applybtn;
        public final RecyclerView childRv;
        public int adId = 0;

        public AdvertisementRvHolder(@NonNull View itemView, final AdvertisementRvAdapter adapter) {
            super(itemView);
            this.adapter = adapter;
            childRv = itemView.findViewById(R.id.rv);
            title = itemView.findViewById(R.id.title);
            province = itemView.findViewById(R.id.province);
            district = itemView.findViewById(R.id.district);
            education = itemView.findViewById(R.id.education);
            last_date = itemView.findViewById(R.id.last_date);
            applybtn = itemView.findViewById(R.id.apply);

            applybtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    SharedPreferences prefs = context.getSharedPreferences("credentials", MODE_PRIVATE);
                    int applicantId = prefs.getInt("ApplicantId", 0);
                    DataService service = RetrofitClientInstance.getRetrofitInstance().create(DataService.class);
                    Call<String> call = service.sendApplication(adId,applicantId);
                    call.enqueue(new Callback<String>() {
                        @Override
                        public void onResponse(Call<String> call, Response<String> response) {
                            if(response.isSuccessful()){
                                Toast.makeText(adapter.context, response.body(), Toast.LENGTH_SHORT).show();
                            }
                        }
                        @Override
                        public void onFailure(Call<String> call, Throwable t) {

                        }
                    });
                }
            });
        }
    }
}
