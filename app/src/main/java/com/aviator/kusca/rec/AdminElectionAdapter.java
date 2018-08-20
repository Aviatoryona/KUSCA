package com.aviator.kusca.rec;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.aviator.kusca.R;
import com.aviator.kusca.models.Candidate;
import com.aviator.kusca.net.Constants;
import com.bumptech.glide.Glide;
import com.sdsmdg.tastytoast.TastyToast;
import com.vstechlab.easyfonts.EasyFonts;

import java.util.Stack;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Aviator on 12/3/2017. Tra
 */
@SuppressWarnings("ALL")
public class AdminElectionAdapter extends RecyclerView.Adapter{

    private final int HEADER=2315,BODY=2316;
    private Stack<Candidate> candidateStack;
    private Context context;

    public AdminElectionAdapter(Stack<Candidate> candidateStack, Context context) {
        this.candidateStack = candidateStack;
        this.context = context;
    }

    @Override
    public int getItemViewType(int position) {
        if(position==0 || position==getItemCount()-1){
            return HEADER;
        }
        return BODY;
    }

    private void RemoveItem(int position){
        try {
            if(candidateStack.size()==1){
                candidateStack.remove(0);
            }else {
                candidateStack.remove(position);
            }
            notifyItemRemoved(position);
        } catch (Exception e) {
         TastyToast.makeText(context,e.getMessage(), TastyToast.LENGTH_LONG, TastyToast.DEFAULT);
         }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        if(viewType==HEADER){
            View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.latest_header,parent,false);
            return new HeaderViewHolder(view);
        }

        final View retView= LayoutInflater.from(parent.getContext()).inflate(R.layout.admin_election_model,parent,false);
        return new ElectionRecViewHolder(retView);
    }
    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder,final int position) {

        if (holder instanceof ElectionRecViewHolder){
            final Candidate candidate = candidateStack.get(position-1);
            Glide.with(context).load(Uri.parse(Constants.MEMBER_PIC_URL + candidate.getPHOTO())).into(((ElectionRecViewHolder) holder).circleImageView);

            ((ElectionRecViewHolder) holder).tName.setText(candidate.getNAME());
            ((ElectionRecViewHolder) holder).tId.setText(candidate.getID());
            ((ElectionRecViewHolder) holder).tPos.setText(candidate.getPOSITION());
            ((ElectionRecViewHolder) holder).tMail.setText(candidate.getEMAIL());
            ((ElectionRecViewHolder) holder).btnConfirm.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    MyConfirm_CL myConfirm_cl=new MyConfirm_CL(context);
                    myConfirm_cl.setEmail(candidate.getEMAIL());
                    myConfirm_cl.setPosition(candidate.getPOSITION());
                    myConfirm_cl.Send();
                    RemoveItem(position);
                }
            });
        }
        if(holder instanceof  HeaderViewHolder){

            ((HeaderViewHolder) holder).textHeader.setTextColor(context.getResources().getColor(R.color.fbutton_color_asbestos));

            if(position==0){
                ((HeaderViewHolder) holder).textHeader.setText("Candidature Requests");
                return;
            }
            ((HeaderViewHolder) holder).textHeader.setTextSize(14);
            ((HeaderViewHolder) holder).textHeader.setText("load more");
            ((HeaderViewHolder) holder).textHeader.setGravity(Gravity.CENTER);

        }
    }



    @Override
    public int getItemCount() {
        return (candidateStack.size())+2;
    }



    public class HeaderViewHolder extends RecyclerView.ViewHolder {
        TextView textHeader;
        HeaderViewHolder(View itemView) {
            super(itemView);
            textHeader=itemView.findViewById(R.id.newsHeader);
            textHeader.setTypeface(EasyFonts.caviarDreamsBold(context));
        }
    }

    static class ElectionRecViewHolder extends RecyclerView.ViewHolder{
        TextView tName,tPos,tId,tMail;
        Button btnConfirm;
        CircleImageView circleImageView;
        ElectionRecViewHolder(View itemView) {
            super(itemView);
            tName=itemView.findViewById(R.id.tName);
            tPos=itemView.findViewById(R.id.tPos);
            tId=itemView.findViewById(R.id.tId);
            tMail=itemView.findViewById(R.id.tEmail);
            btnConfirm=itemView.findViewById(R.id.btnConfirm);
            circleImageView=itemView.findViewById(R.id.pic);
        }

    }


    public class MyConfirm_CL extends AdminConfirm{

        MyConfirm_CL(Context context) {
            super(context);
        }

        @Override
        public void Send() {
            super.Send();
        }

        @Override
        public void setEmail(String email) {
            super.setEmail(email);
        }

        @Override
        public void setPosition(String position) {
            super.setPosition(position);
        }
    }

}