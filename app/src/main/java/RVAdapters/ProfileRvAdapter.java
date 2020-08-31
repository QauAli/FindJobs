package RVAdapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ammara.FindJobs.AdvertisementSkillsRvAdapter;
import com.example.ammara.FindJobs.MessagesActivity;
import com.example.ammara.FindJobs.ProfileEducationRvAdapter;
import com.example.ammara.FindJobs.R;

import java.util.List;

import Api.DataService;
import Api.ImageLoader;
import Api.RetrofitClientInstance;
import Models.Applicant;
import Models.Conversation;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileRvAdapter
        extends RecyclerView.Adapter<ProfileRvAdapter.ProfileRvHolder> {
    private final List<Applicant> profiles;
    //private String program;
    private LayoutInflater layoutInflater;
    private Context context;
    int advertiserId;

    public ProfileRvAdapter(List<Applicant> profiles, Context context, int advertiserId) {
        this.context = context;
        this.profiles = profiles;
        this.advertiserId = advertiserId;
        //this.program = program;
        layoutInflater = LayoutInflater.from(context);
    }
    @NonNull
    @Override
    public ProfileRvHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = layoutInflater.inflate(R.layout.one_applicant, viewGroup, false);
        return new ProfileRvHolder(itemView, this);
    }
    
    @Override
    public void onBindViewHolder(@NonNull ProfileRvHolder rvHolder, final int i) {
        //rvHolder.id.setText(Integer.toString(advertisements.get(i).getId()));
//        rvHolder.Challan_id = advertisements.get(i).getId();

        ImageLoader.LoadImage(rvHolder.profileImage,profiles.get(i).getImage_path(),context);
        rvHolder.name.setText(profiles.get(i).getFirst_name()+profiles.get(i).getLast_name());
        rvHolder.cnic.setText(profiles.get(i).getCnic());
        rvHolder.birth_date.setText(profiles.get(i).getBirth_date());
        rvHolder.father_name.setText(profiles.get(i).getFather_name());
        rvHolder.email.setText(profiles.get(i).getEmail());
        rvHolder.province.setText((profiles.get(i).getProvince()));
        rvHolder.district.setText((profiles.get(i).getDistrict()));
        rvHolder.postal_address.setText((profiles.get(i).getPostal_address()));
        rvHolder.permanent_address.setText((profiles.get(i).getPermanent_address()));
        rvHolder.mobile.setText((profiles.get(i).getMobile()));
        rvHolder.phone.setText((profiles.get(i).getPhone()));
        rvHolder.job_status.setText((profiles.get(i).getJob_status()));
        rvHolder.selectedApplicantId = profiles.get(i).getId();
        //rvHolder.education.setText(advertisements.get(i).getEducation());
        AdvertisementSkillsRvAdapter childAdapter = new AdvertisementSkillsRvAdapter(profiles.get(i).getSkills(),this.context);
        rvHolder.childRv.setAdapter(childAdapter);
        rvHolder.childRv.setLayoutManager(new LinearLayoutManager(this.context));
        //rvHolder.last_date.setText(advertisements.get(i).getLast_date());
        ProfileEducationRvAdapter childEducationAdapter = new ProfileEducationRvAdapter(profiles.get(i).getEducations(),this.context);
        rvHolder.childRvEducation.setAdapter(childEducationAdapter);
        rvHolder.childRvEducation.setLayoutManager(new LinearLayoutManager(this.context));

    }

    @Override
    public int getItemCount() {
        return profiles.size();
    }

    public class ProfileRvHolder
            extends RecyclerView.ViewHolder{
        final ProfileRvAdapter adapter;
        public final TextView name,cnic,birth_date,father_name,
                province,district,postal_address,permanent_address,
                mobile,phone,job_status, email;
        public final Button msgbtn;
        public final ImageView profileImage;
        public final RecyclerView childRv;
        public final RecyclerView childRvEducation;
        public int selectedApplicantId = 0;

        public ProfileRvHolder(@NonNull View itemView, final ProfileRvAdapter adapter) {
            super(itemView);
            this.adapter = adapter;
            childRv = itemView.findViewById(R.id.rv);
            childRvEducation = itemView.findViewById(R.id.rv_education);
            name = itemView.findViewById(R.id.name);
            cnic = itemView.findViewById(R.id.cnic);
            email = itemView.findViewById(R.id.email);
            birth_date = itemView.findViewById(R.id.dob);
            father_name = itemView.findViewById(R.id.father_name);
            province = itemView.findViewById(R.id.province);
            district = itemView.findViewById(R.id.district);
            postal_address = itemView.findViewById(R.id.postal_address);
            permanent_address = itemView.findViewById(R.id.permanent);
            mobile = itemView.findViewById(R.id.mobile);
            phone = itemView.findViewById(R.id.phone);
            job_status = itemView.findViewById(R.id.job_status);
            msgbtn = itemView.findViewById(R.id.msg);
            profileImage = itemView.findViewById(R.id.imageView);

            msgbtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DataService service = RetrofitClientInstance.getRetrofitInstance().create(DataService.class);
                    Call<Conversation> call = service.createCon(selectedApplicantId,
                            advertiserId,
                            new Conversation());
                    call.enqueue(new Callback<Conversation>() {
                        @Override
                        public void onResponse(Call<Conversation> call, Response<Conversation> response) {
                            if(response.isSuccessful()){
                                Intent myIntent = new Intent(context, MessagesActivity.class);
                                myIntent.putExtra("conId",response.body().getId());
                                context.startActivity(myIntent);
                            }else if(response.code() == 200){
                                Toast.makeText(adapter.context, "200", Toast.LENGTH_SHORT).show();
//                                Intent myIntent = new Intent(context, MessagesActivity.class);
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

//            verifybtn.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    DataService service = RetrofitClientInstance.getRetrofitInstance().create(DataService.class);
//                    Call<Challan> call = service.VerifyChallan(Challan_id);
//                    call.enqueue(new Callback<Challan>() {
//                        @Override
//                        public void onResponse(Call<Challan> call, Response<Challan> response) {
//                            //generateDataList(response.body());
//                            if(response.isSuccessful()) {
//                                Toast.makeText(adapter.context, "Verified", Toast.LENGTH_SHORT).show();
//                                adapter.advertisements.remove(getLayoutPosition());
//                                adapter.notifyDataSetChanged();
//                            }
//                        }
//
//                        @Override
//                        public void onFailure(Call<Challan> call, Throwable t) {
//                            Toast.makeText(adapter.context, "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
//                        }
//                    });
//                }
//            });
//
//            deletebtn.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    DataService service = RetrofitClientInstance.getRetrofitInstance().create(DataService.class);
//                    Call<Challan> call = service.DeleteChallan(Challan_id);
//                    call.enqueue(new Callback<Challan>() {
//                        @Override
//                        public void onResponse(Call<Challan> call, Response<Challan> response) {
//                            //generateDataList(response.body());
//                            if(response.isSuccessful()) {
//                                Toast.makeText(adapter.context, "deleted Successfully", Toast.LENGTH_SHORT).show();
//                                adapter.advertisements.remove(getLayoutPosition());
//                                adapter.notifyDataSetChanged();
//                            }
//                        }
//
//                        @Override
//                        public void onFailure(Call<Challan> call, Throwable t) {
//                            Toast.makeText(adapter.context, "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
//                        }
//                    });
//                }
//            });
        }
    }
}
