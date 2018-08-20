package com.aviator.kusca.rec;

import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.aviator.kusca.R;
import com.aviator.kusca.models.EventsModel;
import com.aviator.kusca.net.Constants;
import com.bumptech.glide.Glide;
import com.lid.lib.LabelTextView;
import com.vstechlab.easyfonts.EasyFonts;

import java.util.Stack;

/**
 * Created by Aviator on 12/8/2017.Tra
 */

public class EventsAdapter extends BaseExpandableListAdapter {
    Context context;
    private Stack<EventsModel> eventsModels;

    public EventsAdapter(Context context, Stack<EventsModel> eventsModels) {
        this.context = context;
        this.eventsModels = eventsModels;
    }

    @Override
    public int getGroupCount() {
        return eventsModels.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return 1;
    }

    @Override
    public Object getGroup(int groupPosition) {
        return eventsModels.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return eventsModels.get(groupPosition);
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
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.latest_header,parent,false);
        TextView textView=view.findViewById(R.id.newsHeader);
        textView.setTypeface(EasyFonts.caviarDreamsBold(context));
        textView.setTextColor(Color.BLACK);
        textView.setMaxLines(1);
        textView.setText(eventsModels.get(groupPosition).getTitle());
//        Drawable drawable=context.getResources().getDrawable(R.drawable.ic_navigate_next_black_24dp);
//        Rect rect=new Rect(20,10,20,10);
//        drawable.setBounds(rect);
//        textView.setCompoundDrawables(null,null,drawable,null);
        return view;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
       View view1=LayoutInflater.from(parent.getContext()).inflate(R.layout.events_model,parent,false);
       TextView tBody,tDH,tDP;
        ImageView imageView;
        LabelTextView labelTextView;

        labelTextView=view1.findViewById(R.id.text);
        imageView=view1.findViewById(R.id.imageView);
        tBody=view1.findViewById(R.id.txtBody);
        tDH=view1.findViewById(R.id.txtHp);
        tDP=view1.findViewById(R.id.txtDt);

        EventsModel model=eventsModels.get(groupPosition);

        labelTextView.setText(model.getTitle());
        Glide.with(context).load(Uri.parse(Constants.OTHER_PIC_URL+"eventpics/"+model.getPic())).into(imageView);
        tBody.setText(model.getStory());
        tDH.setText(model.getDh());
        tDP.setText("posted\n"+model.getDp());

        return view1;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }




}
