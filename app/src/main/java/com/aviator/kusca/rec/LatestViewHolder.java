package com.aviator.kusca.rec;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.aviator.kusca.R;

/**  tra
 * Created by Aviator on 11/27/2017.
 */

class LatestViewHolder extends RecyclerView.ViewHolder{
    ImageView imageView;
    TextView textBody,tmore,theader;
    LatestViewHolder(View itemView) {
        super(itemView);
        imageView=itemView.findViewById(R.id.pic);
        textBody=itemView.findViewById(R.id.news);
        tmore=itemView.findViewById(R.id.rmore);
        theader=itemView.findViewById(R.id.newsHeader);
    }


}
