package Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ammara.FindJobs.R;

import java.util.List;

import ModelClasses.Conversation;
import ModelClasses.Education;
import ModelClasses.Skill;
import RestfullServices.Client;
import RestfullServices.FindJobService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SkillsAdapter
        extends RecyclerView.Adapter<SkillsAdapter.RvHolder> {
    private final List<Skill> skills;
    private LayoutInflater layoutInflater;
    private Context context;

    public SkillsAdapter(List<Skill> skills, Context context) {
        this.context = context;
        this.skills = skills;
        layoutInflater = LayoutInflater.from(context);
    }
    @NonNull
    @Override
    public SkillsAdapter.RvHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = layoutInflater.inflate(R.layout.skill_view, viewGroup, false);
        return new RvHolder(itemView, this);
    }

    @Override
    public void onBindViewHolder(@NonNull SkillsAdapter.RvHolder rvHolder, final int i) {
        //String s = educations.get(i)._id;
        rvHolder.name.setText(skills.get(i).getName());
        rvHolder.id = skills.get(i).getId();
    }

    @Override
    public int getItemCount() {
        return skills.size();
    }

    public class RvHolder
            extends RecyclerView.ViewHolder{
        final SkillsAdapter adapter;
        public final TextView name;
        public final Button delete;
        int id;

        public RvHolder(@NonNull View itemView, final SkillsAdapter adapter) {
            super(itemView);
            this.adapter = adapter;
            name = itemView.findViewById(R.id.name);
            delete = itemView.findViewById(R.id.delete);

            delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    FindJobService service = Client.getClient().create(FindJobService.class);
                    Call<Skill> call = service.deleteSkill(id);
                    call.enqueue(new Callback<Skill>() {
                        @Override
                        public void onResponse(Call<Skill> call, Response<Skill> response) {
                            if(response.isSuccessful()){
                                Toast.makeText(adapter.context, "Deleted!", Toast.LENGTH_SHORT).show();
                                adapter.skills.remove(getLayoutPosition());
                                adapter.notifyDataSetChanged();
                            }
                        }

                        @Override
                        public void onFailure(Call<Skill> call, Throwable t) {

                        }
                    });
                }
            });


        }
    }
}
