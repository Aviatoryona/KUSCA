package com.aviator.kusca.rec;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.aviator.kusca.R;
import com.aviator.kusca.models.GalleryModel;
import com.aviator.kusca.net.Constants;
import com.bumptech.glide.Glide;
import com.sdsmdg.tastytoast.TastyToast;

import java.util.Stack;

/**
 * Created by Aviator on 11/28/2017.
 */
@SuppressWarnings("ALL")
public class KuscaGalleryAdapter extends RecyclerView.Adapter<KuscaGalleryAdapter.MyViewHolder>{

    Stack<GalleryModel> stringStack;
    Context context;

    public KuscaGalleryAdapter(Stack<GalleryModel> stringStack, Context context) {
        this.stringStack = stringStack;
        this.context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View myV=LayoutInflater.from(parent.getContext()).inflate(R.layout.gallery_model,null,false);
        return new MyViewHolder(myV);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        GalleryModel galleryModel=stringStack.get(position);
        Glide.with(context).load(Uri.parse(Constants.OTHER_PIC_URL+"gallerypics/"+galleryModel.getPicName())).into(holder.imageView);

        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TastyToast.makeText(context,"liked",TastyToast.LENGTH_LONG,TastyToast.DEFAULT);
            }
        });

    }

    @Override
    public int getItemCount() {
        return stringStack.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{
      AppCompatImageView imageView;
        MyViewHolder(View itemView) {
            super(itemView);
            imageView=itemView.findViewById(R.id.imageView);
        }
    }

}
