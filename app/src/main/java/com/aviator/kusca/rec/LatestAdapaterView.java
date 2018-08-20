package com.aviator.kusca.rec;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.Uri;
import android.os.Build;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.aviator.kusca.R;
import com.aviator.kusca.helpers.KuscaGalleryHelper;
import com.aviator.kusca.models.LatestModel;
import com.aviator.kusca.net.Constants;
import com.bumptech.glide.Glide;
import com.vstechlab.easyfonts.EasyFonts;

import java.util.Stack;

/**
 * Created by Aviator on 11/27/2017.Tra
 */


public class LatestAdapaterView extends RecyclerView.Adapter{
    Context context;
    private static int VIEWHEADER=1222;
    private static int VIEWBODY=1223;
    private static int VIEWGALLERY=1224;
    private static int GALLERYHEADER=1225;
    private Stack<LatestModel> latestModels;

    public LatestAdapaterView(Context context, Stack<LatestModel> latestModels) {
        this.context = context;
        this.latestModels = latestModels;
    }

    @Override
    public int getItemViewType(int position) {
        if(position==getItemCount()-3){
            return VIEWHEADER;
        }

        if(position==(getItemCount()-2)){
            return GALLERYHEADER;
        }

        if(position==(getItemCount()-1)){
            return VIEWGALLERY;
        }
        return VIEWBODY;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(viewType==GALLERYHEADER){

            View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.latest_header,parent,false);
            return new HeaderGallery(view);
        }

        if(viewType==VIEWHEADER){

            View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.latest_header,parent,false);
            return new HeaderViewHolder(view);
        }

        if(viewType==VIEWGALLERY){

            View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.latest_gallery_view,parent,false);
            return new GalleryViewHolder(view);
        }

        if(viewType==VIEWBODY){
            View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.latest_model,parent,false);
            return new LatestViewHolder(view);
        }else{
            return null;
        }
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {

        if(holder!=null){

            if(holder instanceof HeaderViewHolder){

                ((HeaderViewHolder) holder).textHeader.setText("Upcoming Events");

            }
            if(holder instanceof HeaderGallery){

                ((HeaderGallery) holder).textHeader.setText("Latest Gallery");
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    ((HeaderGallery) holder).textHeader.setTextColor(context.getResources().getColor(R.color.colorHoloOrange,null));
                    return;
                }
                ((HeaderGallery) holder).textHeader.setTextColor(context.getResources().getColor(R.color.colorHoloOrange));

            }
            if(holder instanceof LatestViewHolder){
                LatestModel latestModel=latestModels.get(position);
                ((LatestViewHolder) holder).theader.setTypeface(EasyFonts.robotoRegular(context));
                ((LatestViewHolder) holder).theader.setText(latestModel.getTitle());
                Glide.with(context).load(Uri.parse(Constants.OTHER_PIC_URL+"latestpics/"+latestModel.getPic())).into(((LatestViewHolder) holder).imageView);
                //((LatestViewHolder) holder).imageView.setImageResource(imgsRes[(position%2)]);
                ((LatestViewHolder) holder).textBody.setText(latestModel.getBody());

                ((LatestViewHolder) holder).imageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                });

                ((LatestViewHolder) holder).textBody.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                    }
                });

                ((LatestViewHolder) holder).tmore.setOnClickListener((new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                    }
                }));

            }

            if(holder instanceof GalleryViewHolder){

                new KuscaGalleryHelper(((GalleryViewHolder) holder).superRecyclerView,context).Volley_LoadData();
            }

        }

    }

    @Override
    public int getItemCount() {
        return (latestModels.size())+3;
    }



    public class HeaderViewHolder extends RecyclerView.ViewHolder {
        TextView textHeader;
        HeaderViewHolder(View itemView) {
            super(itemView);
            textHeader=itemView.findViewById(R.id.newsHeader);
            textHeader.setTypeface(EasyFonts.caviarDreamsBold(context));
        }
    }

    public class HeaderGallery extends RecyclerView.ViewHolder {
        TextView textHeader;
        HeaderGallery(View itemView) {
            super(itemView);
            textHeader=itemView.findViewById(R.id.newsHeader);
            textHeader.setTypeface(EasyFonts.caviarDreamsBold(context));
        }
    }

    public class GalleryViewHolder extends RecyclerView.ViewHolder {
        RecyclerView superRecyclerView;
        GalleryViewHolder(View itemView) {
            super(itemView);
            superRecyclerView= itemView.findViewById(R.id.superRec);
            superRecyclerView.setLayoutManager(new LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false));

        }
    }

}
