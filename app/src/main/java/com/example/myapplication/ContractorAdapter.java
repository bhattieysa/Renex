package com.example.myapplication;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

public class ContractorAdapter extends RecyclerView.Adapter<ContractorAdapter.myViewHolder> {
    private List<ContractorProjectModel> list;
    Context ctx;



    public ContractorAdapter(MyProjectsContractor ctx, List<ContractorProjectModel> list) {
        this.ctx = ctx;
        this.list = list;
    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card,parent,false);
        myViewHolder viewHolder = new myViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull myViewHolder holder, int position) {

        ContractorProjectModel model=list.get(position);
        holder.jobtitle.setText(model.getJobtitle());
        holder.jobdesc.setText(model.getJobdesc());
        holder.jobloc.setText(model.getJobloc());
        holder.jobtime.setText(model.getJobtime());
        holder.jobbudget.setText(model.getJobbudget());
        holder.ImageURL=model.getImageUrl();
        holder.ImageURL2=model.getImageurl2();
        holder.ImageURL3=model.getImageurl3();
        holder.jobid.setText(model.getJobid());
        // holder.ImageURL= imageUrl+holder.ImageURL;
        Object context;
        Glide.with(ctx)
                .load(holder.ImageURL)

                .into(holder.image);




        holder.viewdet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ctx,ViewDetailsContractors.class);
                holder.getAdapterPosition();
                String jobtitle =  holder.jobtitle.getText().toString();
                String jobdesc = holder.jobdesc.getText().toString();
                String jobloc = holder.jobloc.getText().toString();
                String jobtime = holder.jobtime.getText().toString();
                String jobbudget = holder.jobbudget.getText().toString();
                String jobid = holder.jobid.getText().toString();




                intent.putExtra("JobTitle",jobtitle );
                intent.putExtra("JobDesc", jobdesc);
                intent.putExtra("JobLoc", jobloc);
                intent.putExtra("JobTime", jobtime);
                intent.putExtra("JobBudget", jobbudget);
                intent.putExtra("imageurl", holder.ImageURL);
                intent.putExtra("imageurl2", holder.ImageURL2);
                intent.putExtra("imageurl3", holder.ImageURL3);
                intent.putExtra("Jobid", jobid);
                ctx.startActivity(intent);

            }
        });




    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class myViewHolder extends RecyclerView.ViewHolder{
        TextView jobtitle,jobdesc,jobloc,jobtime,jobbudget,viewdet,jobid;
        ImageView image,image2,image3;
        String ImageURL,ImageURL2,ImageURL3;

        public myViewHolder(@NonNull View itemView) {
            super(itemView);
            jobtitle=itemView.findViewById(R.id.textView5);
            jobdesc=itemView.findViewById(R.id.tvdesc);
            jobloc=itemView.findViewById(R.id.loc);
            jobtime=itemView.findViewById(R.id.time);
            jobbudget=itemView.findViewById(R.id.budget);
            image=itemView.findViewById(R.id.appCompatImageView);
            image2=itemView.findViewById(R.id.appCompatImageView2);
            image3=itemView.findViewById(R.id.appCompatImageView3);
            viewdet = itemView.findViewById(R.id.btndetails);
            jobid = itemView.findViewById(R.id.jobid);




        }
    }






}

