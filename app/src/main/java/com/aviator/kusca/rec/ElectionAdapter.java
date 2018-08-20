package com.aviator.kusca.rec;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;

import com.aviator.kusca.R;
import com.aviator.kusca.models.Candidate;
import com.aviator.kusca.models.ElectionModel;
import com.aviator.kusca.net.Constants;
import com.bumptech.glide.Glide;
import com.sdsmdg.tastytoast.TastyToast;
import com.vstechlab.easyfonts.EasyFonts;

import java.util.ArrayList;
import java.util.Stack;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Aviator on 11/27/2017.
 */

public class ElectionAdapter extends BaseExpandableListAdapter{


    private ArrayList<ElectionModel> electionModelArrayList;
    private Context context;

    public ElectionAdapter(ArrayList<ElectionModel> electionModelArrayList, Context context) {
        this.electionModelArrayList = electionModelArrayList;
        this.context = context;
    }

    @Override
    public int getGroupCount() {
        return electionModelArrayList.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return (electionModelArrayList.get(groupPosition)).getCandidateStack().size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return electionModelArrayList.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return ((electionModelArrayList.get(groupPosition)).getCandidateStack()).get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        View myView= LayoutInflater.from(parent.getContext()).inflate(R.layout.electionheader,parent,false);
        TextView tx=myView.findViewById(R.id.posHeader);
        tx.setTypeface(EasyFonts.freedom(context));
        tx.setText(electionModelArrayList.get(groupPosition).getPosition());
        return myView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        Stack<Candidate> candidates=electionModelArrayList.get(groupPosition).getCandidateStack();

        final Candidate candidate=candidates.get(childPosition);
        final View retView= LayoutInflater.from(parent.getContext()).inflate(R.layout.electionchildmodel,parent,false);
        CircleImageView imageView=retView.findViewById(R.id.pic);
        Glide.with(context).load(Uri.parse(Constants.MEMBER_PIC_URL+candidate.getPHOTO())).into(imageView);

        ((TextView)retView.findViewById(R.id.tName)).setText(candidate.getNAME());
        ((TextView)retView.findViewById(R.id.tName)).setTypeface(EasyFonts.walkwayBold(context));
        ((TextView)retView.findViewById(R.id.tId)).setText(candidate.getID());
        ((TextView)retView.findViewById(R.id.tPos)).setText(candidate.getPOSITION());
        ((TextView)retView.findViewById(R.id.tEmail)).setText(candidate.getEMAIL());

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(((CheckBox)retView.findViewById(R.id.checkbox)).isChecked()){
                    ((CheckBox) retView.findViewById(R.id.checkbox)).setChecked(false);
                    //TastyToast.makeText(context,candidate.getNAME(),TastyToast.LENGTH_LONG,TastyToast.SUCCESS);
                }else{
                    ((CheckBox) retView.findViewById(R.id.checkbox)).setChecked(true);
                   // TastyToast.makeText(context,candidate.getEMAIL(),TastyToast.LENGTH_LONG,TastyToast.SUCCESS);
                }
            }
        });

        return retView;

    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}
