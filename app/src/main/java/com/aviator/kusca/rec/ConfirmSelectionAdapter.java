package com.aviator.kusca.rec;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.aviator.kusca.R;
import com.aviator.kusca.db.MyDb;
import com.aviator.kusca.models.Candidate;
import com.aviator.kusca.net.Constants;
import com.bumptech.glide.Glide;
import com.sdsmdg.tastytoast.TastyToast;

import java.util.Stack;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Aviator on 11/29/2017.
 */

public class ConfirmSelectionAdapter extends RecyclerView.Adapter<ElectionRecAdapter.ElectionRecViewHolder>{

    private Stack<Candidate> candidateStack;
    private Context context;

    public ConfirmSelectionAdapter(Stack<Candidate> candidateStack, Context context) {
        this.candidateStack = candidateStack;
        this.context = context;
    }

    @Override
    public ElectionRecAdapter.ElectionRecViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View retView= LayoutInflater.from(parent.getContext()).inflate(R.layout.electionchildmodel,parent,false);

        return new ElectionRecAdapter.ElectionRecViewHolder(retView);
    }

    @Override
    public void onBindViewHolder(final ElectionRecAdapter.ElectionRecViewHolder holder, int position) {
        final Candidate candidate=candidateStack.get(position);
        Glide.with(context).load(Uri.parse(Constants.MEMBER_PIC_URL+candidate.getPHOTO())).into(holder.circleImageView);

        holder.tName.setText(candidate.getNAME());
        holder.tId.setText(candidate.getID());
        holder.tPos.setText(candidate.getPOSITION());
        holder.tMail.setText(candidate.getEMAIL());
        holder.checkBox.setVisibility(View.GONE);
    }



    @Override
    public int getItemCount() {
        return candidateStack.size();
    }



    static class ElectionRecViewHolder extends RecyclerView.ViewHolder{
        TextView tName,tPos,tId,tMail;
        CheckBox checkBox;
        CircleImageView circleImageView;
        ElectionRecViewHolder(View itemView) {
            super(itemView);
            tName=itemView.findViewById(R.id.tName);
            tPos=itemView.findViewById(R.id.tPos);
            tId=itemView.findViewById(R.id.tId);
            tMail=itemView.findViewById(R.id.tEmail);
            checkBox=itemView.findViewById(R.id.checkbox);
            circleImageView=itemView.findViewById(R.id.pic);
        }
    }

}