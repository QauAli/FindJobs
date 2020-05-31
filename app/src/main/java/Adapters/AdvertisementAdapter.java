package Adapters;

import android.content.Context;
import android.content.Intent;
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

import com.example.ammara.FindJobs.Constants;
import com.example.ammara.FindJobs.Messages;
import com.example.ammara.FindJobs.ProfileView;
import com.example.ammara.FindJobs.R;

import java.util.List;

import ModelClasses.Conversation;
import RestfullServices.Client;
import RestfullServices.FindJobService;
import ModelClasses.Advertisement;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;

public class AdvertisementAdapter
        extends RecyclerView.Adapter<AdvertisementAdapter.AdvertisementRvHolder> {
    private final List<Advertisement> advertisements;
    //private String program;
    private LayoutInflater layoutInflater;
    private Context context;
    private int wishlistButtonVisibility;

    public AdvertisementAdapter(List<Advertisement> advertisements, Context context, int wishlistButtonVisibility) {
        this.context = context;
        this.advertisements = advertisements;
        this.wishlistButtonVisibility = wishlistButtonVisibility;
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
        AdvertisementSkillsAdapter childAdapter = new AdvertisementSkillsAdapter(advertisements.get(i).getSkills(),this.context);
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
        final AdvertisementAdapter adapter;
        public final TextView title,province,district,education,last_date;
        public final Button applybtn;
        public final Button addToWishlist;
        private Button msgbtn;
        public final RecyclerView childRv;
        public int adId = 0;

        public AdvertisementRvHolder(@NonNull View itemView, final AdvertisementAdapter adapter) {
            super(itemView);
            this.adapter = adapter;
            childRv = itemView.findViewById(R.id.rv);
            title = itemView.findViewById(R.id.title);
            province = itemView.findViewById(R.id.province);
            district = itemView.findViewById(R.id.district);
            education = itemView.findViewById(R.id.education);
            last_date = itemView.findViewById(R.id.last_date);
            applybtn = itemView.findViewById(R.id.apply);
            addToWishlist = itemView.findViewById(R.id.wishlist);
            msgbtn = itemView.findViewById(R.id.msg);

            if(wishlistButtonVisibility == 0){
                addToWishlist.setVisibility(View.GONE);
            }

            addToWishlist.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    SharedPreferences prefs = context.getSharedPreferences(Constants.file, MODE_PRIVATE);
                    int applicantId = prefs.getInt("ApplicantId", 0);
                    FindJobService service = Client.getClient().create(FindJobService.class);
                    Call<String> call = service.addToWishlist(applicantId,adId,"");
                    call.enqueue(new Callback<String>() {
                        @Override
                        public void onResponse(Call<String> call, Response<String> response) {
                            if(Integer.parseInt(response.body())==201){
                                Toast.makeText(adapter.context,"Added to wishlist!",Toast.LENGTH_SHORT).show();
                            }
                            if(Integer.parseInt(response.body())==200){
                                Toast.makeText(adapter.context,"Already in wishlist!",Toast.LENGTH_SHORT).show();
                            }

                        }

                        @Override
                        public void onFailure(Call<String> call, Throwable t) {

                        }
                    });
                }
            });
            msgbtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    SharedPreferences prefs = context.getSharedPreferences(Constants.file, MODE_PRIVATE);
                    int applicantId = prefs.getInt("ApplicantId", 0);
                    FindJobService service = Client.getClient().create(FindJobService.class);
                    Call<Conversation> call = service.createJobCon(applicantId,
                            adId,
                            new Conversation());
                    call.enqueue(new Callback<Conversation>() {
                        @Override
                        public void onResponse(Call<Conversation> call, Response<Conversation> response) {
                            if(response.isSuccessful()){
                                Intent myIntent = new Intent(context, Messages.class);
                                myIntent.putExtra("conId",response.body().getId());
                                context.startActivity(myIntent);
                            }else if(response.code() == 200){
                                Toast.makeText(context, "200", Toast.LENGTH_SHORT).show();
//                                Intent myIntent = new Intent(context, Messages.class);
//                                myIntent.putExtra("conId",response.body().getId());
//                                context.startActivity(myIntent);
                            }
                        }

                        @Override
                        public void onFailure(Call<Conversation> call, Throwable t) {

                        }
                    });
                }
            });

            applybtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    SharedPreferences prefs = context.getSharedPreferences(Constants.file, MODE_PRIVATE);
                    int applicantId = prefs.getInt("ApplicantId", 0);
                    FindJobService service = Client.getClient().create(FindJobService.class);
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
