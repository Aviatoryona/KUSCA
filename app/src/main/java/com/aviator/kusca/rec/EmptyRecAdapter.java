package com.aviator.kusca.rec;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.aviator.kusca.KuscaAnnouncements;
import com.aviator.kusca.R;
import com.vstechlab.easyfonts.EasyFonts;

/**
 * Created by Aviator on 12/4/2017.
 */

public class EmptyRecAdapter extends RecyclerView.Adapter<EmptyRecAdapter.EmptyRecView> {
Context context;
String message;

    public EmptyRecAdapter(Context context, String message) {
        this.context = context;
        this.message = message;
    }

    @Override
    public EmptyRecView onCreateViewHolder(ViewGroup parent, int viewType) {
        View m= LayoutInflater.from(parent.getContext()).inflate(R.layout.emptyview,null,false);
        return new EmptyRecView(m);
    }

    @Override
    public void onBindViewHolder(EmptyRecView holder, int position) {
       holder.textView.setText(message);
    }

    @Override
    public int getItemCount() {
        return 1;
    }


    class EmptyRecView extends RecyclerView.ViewHolder{
TextView textView;
        public EmptyRecView(View itemView) {
            super(itemView);
            textView=itemView.findViewById(R.id.textView);
            textView.setTypeface(EasyFonts.caviarDreamsBold(context));

        }
    }
}
