package com.example.ammara.FindJobs;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import Models.Education;

public class ProfileEducationRvAdapter
        extends RecyclerView.Adapter<ProfileEducationRvAdapter.RvHolder> {
    private final List<Education> educations;
    private LayoutInflater layoutInflater;
    private Context context;

    public ProfileEducationRvAdapter(List<Education> educationss, Context context) {
        this.context = context;
        this.educations = educationss;
        layoutInflater = LayoutInflater.from(context);
    }
    @NonNull
    @Override
    public ProfileEducationRvAdapter.RvHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = layoutInflater.inflate(R.layout.one_education, viewGroup, false);
        return new RvHolder(itemView, this);
    }

    @Override
    public void onBindViewHolder(@NonNull ProfileEducationRvAdapter.RvHolder rvHolder, final int i) {
        //String s = educations.get(i)._id;
        rvHolder.title.setText(educations.get(i).getTitle() );
        rvHolder.grade.setText( educations.get(i).getGrade());
        rvHolder.year.setText( educations.get(i).getYear());
    }

    @Override
    public int getItemCount() {
        return educations.size();
    }

    public class RvHolder
            extends RecyclerView.ViewHolder{
        final ProfileEducationRvAdapter adapter;
        public final TextView title;
        public final TextView grade;
        public final TextView year;

        public RvHolder(@NonNull View itemView, final ProfileEducationRvAdapter adapter) {
            super(itemView);
            this.adapter = adapter;
            title = itemView.findViewById(R.id.title);
            grade = itemView.findViewById(R.id.grade);
            year = itemView.findViewById(R.id.year);


        }
    }
}
