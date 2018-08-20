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
 * Created by Aviator on 11/28/2017.
 */

public class ElectionRecAdapter extends RecyclerView.Adapter<ElectionRecAdapter.ElectionRecViewHolder>implements Disselector{

    private MyDb myDb;
    private Stack<Candidate> candidateStack;
    private Context context;
    private ElectionRecViewHolder electionRecViewHolder=null;
    private String POSITION;

    public ElectionRecAdapter(Stack<Candidate> candidateStack, Context context) {
        this.candidateStack = candidateStack;
        this.context = context;
        myDb=new MyDb(this.context);
    }

    @Override
    public ElectionRecViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View retView= LayoutInflater.from(parent.getContext()).inflate(R.layout.electionchildmodel,parent,false);

        return new ElectionRecViewHolder(retView);
    }

//    public void AddAll(Stack<Candidate> aa){
//        int x=candidateStack.size();
//        candidateStack.addAll(aa);
//        notifyItemRangeChanged(x,aa.size());
//    }
    @Override
    public void onBindViewHolder(final ElectionRecViewHolder holder, int position) {
        final Candidate candidate=candidateStack.get(position);
        Glide.with(context).load(Uri.parse(Constants.MEMBER_PIC_URL+candidate.getPHOTO())).into(holder.circleImageView);

        holder.tName.setText(candidate.getNAME());
        holder.tId.setText(candidate.getID());
        holder.tPos.setText(candidate.getPOSITION());
        holder.tMail.setText(candidate.getEMAIL());

        holder.circleImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(getElectionRecViewHolder()!=null){
                    disSelectCheckBox(getElectionRecViewHolder(),getPOSITION());
                }

                setElectionRecViewHolder(holder);
                Update(candidate.getPOSITION(),candidate.getPositionId(),myDb,candidate);
                setPOSITION(candidate.getPOSITION());

                if(holder.checkBox.isChecked()){
                    holder.checkBox.setChecked(false);
                   // TastyToast.makeText(context,candidate.getNAME(),TastyToast.LENGTH_LONG,TastyToast.SUCCESS);
                }else{
                    holder.checkBox.setChecked(true);
                   // TastyToast.makeText(context,candidate.getEMAIL(),TastyToast.LENGTH_LONG,TastyToast.SUCCESS);
                }
            }
        });
    }



    @Override
    public int getItemCount() {
        return candidateStack.size();
    }

    @Override
    public void disSelectCheckBox(ElectionRecViewHolder recViewHolder,String position) {
        if(recViewHolder!=null && position.equalsIgnoreCase(getPOSITION())) {
            recViewHolder.checkBox.setChecked(false);
        }
    }

    @Override
    public void Update(String position,String pid, MyDb myDb, Candidate candidate) {
        if(myDb!=null && pid!=null && candidate!=null){
          // if(position.equalsIgnoreCase(getPOSITION())) {
               myDb.UpdateData(pid, candidate);
          // }
        }else{
            TastyToast.makeText(context,"Null parameters",TastyToast.LENGTH_LONG,TastyToast.ERROR);
        }
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


    private ElectionRecViewHolder getElectionRecViewHolder() {
        return electionRecViewHolder;
    }

    private void setElectionRecViewHolder(ElectionRecViewHolder electionRecViewHolder) {
        this.electionRecViewHolder = electionRecViewHolder;
    }

    private String getPOSITION() {
        return POSITION;
    }

    private void setPOSITION(String POSITION) {
        this.POSITION = POSITION;
    }
}
