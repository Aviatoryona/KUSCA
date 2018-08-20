package com.aviator.kusca.rec;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.aviator.kusca.R;
import com.aviator.kusca.models.Kuscateam_model;
import com.aviator.kusca.net.Constants;
import com.bumptech.glide.Glide;
import com.sdsmdg.tastytoast.TastyToast;
import com.vstechlab.easyfonts.EasyFonts;

import java.util.ArrayList;

/**
 * Created by Aviator on 11/27/2017.
 */

public class KuscateamAdapterHolder extends RecyclerView.Adapter<KuscateamViewHolder> {
    private ArrayList<Kuscateam_model> kuscateam_models;
    private Context context;

    public KuscateamAdapterHolder(ArrayList<Kuscateam_model> kuscateam_models, Context context) {
        this.kuscateam_models = kuscateam_models;
        this.context = context;
    }

    @Override
    public KuscateamViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View myV=LayoutInflater.from(parent.getContext()).inflate(R.layout.kusca_team_model,parent,false);
        return new KuscateamViewHolder(myV);
    }

    @Override
    public void onBindViewHolder(KuscateamViewHolder holder, int position) {

       // if(holder!=null){
           final Kuscateam_model kuscateam_model=kuscateam_models.get(position);
            holder.awesomeTextViewPos.setText(kuscateam_model.getPosition());
            holder.awesomeTextViewPos.setTypeface(EasyFonts.ostrichBold(context));
            holder.awesomeTextViewName.setText(kuscateam_model.getName());
            holder.awesomeTextViewInfo.setText(kuscateam_model.getInfo());

            Glide.with(context).load(Uri.parse(Constants.MEMBER_PIC_URL+kuscateam_model.getPhoto())).into(holder.bootstrapCircleThumbnail);



        holder.bootstrapCircleThumbnail.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    TastyToast.makeText(context,kuscateam_model.getEmail(),TastyToast.LENGTH_LONG,TastyToast.SUCCESS);
                }
            });
       // }

    }

    @Override
    public int getItemCount() {
        return kuscateam_models.size();
    }
}
