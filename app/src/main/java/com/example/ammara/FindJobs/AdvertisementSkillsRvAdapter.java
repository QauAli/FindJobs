package com.example.ammara.FindJobs;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class AdvertisementSkillsRvAdapter
        extends RecyclerView.Adapter<AdvertisementSkillsRvAdapter.RvHolder> {
    private final List<String> skills;
    private LayoutInflater layoutInflater;
    private Context context;

    public AdvertisementSkillsRvAdapter(List<String> skills, Context context) {
        this.context = context;
        this.skills = skills;
        layoutInflater = LayoutInflater.from(context);
    }
    @NonNull
    @Override
    public AdvertisementSkillsRvAdapter.RvHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = layoutInflater.inflate(R.layout.one_skill, viewGroup, false);
        return new RvHolder(itemView, this);
    }

    @Override
    public void onBindViewHolder(@NonNull AdvertisementSkillsRvAdapter.RvHolder rvHolder, final int i) {
        //String s = skills.get(i)._id;
        rvHolder.name.setText("+"+ skills.get(i));

    }

    @Override
    public int getItemCount() {
        return skills.size();
    }

    public class RvHolder
            extends RecyclerView.ViewHolder{
        final AdvertisementSkillsRvAdapter adapter;
        public final TextView name;

        public RvHolder(@NonNull View itemView, final AdvertisementSkillsRvAdapter adapter) {
            super(itemView);
            this.adapter = adapter;
            name = itemView.findViewById(R.id.name);


        }
    }
}
