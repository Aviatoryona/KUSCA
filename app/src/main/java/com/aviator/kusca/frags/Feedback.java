package com.aviator.kusca.frags;


import android.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.aviator.kusca.R;
import com.vstechlab.easyfonts.EasyFonts;


public class Feedback extends DialogFragment {

    TextView textView;
    public Feedback() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View myV=inflater.inflate(R.layout.fragment_feedback, container, false);
       textView=myV.findViewById(R.id.feedback);

        //textView.setTypeface(EasyFonts.caviarDreamsBold(getContext()));

        return myV;
    }

    public void SetText(String text){

    }

}
