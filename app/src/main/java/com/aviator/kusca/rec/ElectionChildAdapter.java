package com.aviator.kusca.rec;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import com.aviator.kusca.R;
import com.aviator.kusca.models.Candidate;
import com.aviator.kusca.net.Constants;
import com.bumptech.glide.Glide;
import com.sdsmdg.tastytoast.TastyToast;

import java.util.Stack;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Aviator on 11/27/2017.
 */

class ElectionChildAdapter extends BaseAdapter{
    private Stack<Candidate> candidateStack;
    private Context context;

    ElectionChildAdapter(Stack<Candidate> candidateStack, Context context) {
        this.candidateStack = candidateStack;
        this.context = context;
    }

    @Override
    public int getCount() {
        return candidateStack.size();
    }

    @Override
    public Object getItem(int position) {
        return candidateStack.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {


        final Candidate candidate=candidateStack.get(position);
        final View retView= LayoutInflater.from(parent.getContext()).inflate(R.layout.electionchildmodel,parent,false);
        CircleImageView imageView=retView.findViewById(R.id.pic);
        Glide.with(context).load(Uri.parse(Constants.MEMBER_PIC_URL+candidate.getPHOTO())).into(imageView);

        ((TextView)retView.findViewById(R.id.tName)).setText(candidate.getNAME());
        ((TextView)retView.findViewById(R.id.tId)).setText(candidate.getID());
        ((TextView)retView.findViewById(R.id.tPos)).setText(candidate.getPOSITION());
        ((TextView)retView.findViewById(R.id.tEmail)).setText(candidate.getEMAIL());

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 if(((CheckBox)retView.findViewById(R.id.checkbox)).isChecked()){
                     retView.findViewById(R.id.checkbox).setSelected(false);
                     TastyToast.makeText(context,candidate.getNAME(),TastyToast.LENGTH_LONG,TastyToast.SUCCESS);
                 }else{
                     retView.findViewById(R.id.checkbox).setSelected(true);
                     TastyToast.makeText(context,candidate.getEMAIL(),TastyToast.LENGTH_LONG,TastyToast.SUCCESS);
                 }
            }
        });

        return retView;
    }

}
