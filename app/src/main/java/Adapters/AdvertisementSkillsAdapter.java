package Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.ammara.FindJobs.R;

import java.util.List;

public class AdvertisementSkillsAdapter
        extends RecyclerView.Adapter<AdvertisementSkillsAdapter.RvHolder> {
    private final List<String> skills;
    private LayoutInflater layoutInflater;
    private Context context;

    public AdvertisementSkillsAdapter(List<String> skills, Context context) {
        this.context = context;
        this.skills = skills;
        layoutInflater = LayoutInflater.from(context);
    }
    @NonNull
    @Override
    public AdvertisementSkillsAdapter.RvHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = layoutInflater.inflate(R.layout.one_skill, viewGroup, false);
        return new RvHolder(itemView, this);
    }

    @Override
    public void onBindViewHolder(@NonNull AdvertisementSkillsAdapter.RvHolder rvHolder, final int i) {
        //String s = skills.get(i)._id;
        rvHolder.name.setText("+"+ skills.get(i));

    }

    @Override
    public int getItemCount() {
        return skills.size();
    }

    public class RvHolder
            extends RecyclerView.ViewHolder{
        final AdvertisementSkillsAdapter adapter;
        public final TextView name;

        public RvHolder(@NonNull View itemView, final AdvertisementSkillsAdapter adapter) {
            super(itemView);
            this.adapter = adapter;
            name = itemView.findViewById(R.id.name);


        }
    }
}
