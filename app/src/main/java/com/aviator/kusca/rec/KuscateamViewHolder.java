package com.aviator.kusca.rec;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.aviator.kusca.R;
import com.vstechlab.easyfonts.EasyFonts;

import de.hdodenhof.circleimageview.CircleImageView;
import info.hoang8f.widget.FButton;

/**
 * Created by Aviator on 11/27/2017.
 */
@SuppressWarnings("ALL")
public class KuscateamViewHolder extends RecyclerView.ViewHolder {
    TextView awesomeTextViewPos,awesomeTextViewName,awesomeTextViewInfo;
    CircleImageView bootstrapCircleThumbnail;
    FButton bootstrapButton;
    public KuscateamViewHolder(View itemView) {
        super(itemView);
        awesomeTextViewPos=itemView.findViewById(R.id.bPosition);
        awesomeTextViewName=itemView.findViewById(R.id.bName);
        awesomeTextViewInfo=itemView.findViewById(R.id.bInfo);

        bootstrapCircleThumbnail=itemView.findViewById(R.id.bPhoto);
        bootstrapButton=itemView.findViewById(R.id.bProfile);

    }
}
