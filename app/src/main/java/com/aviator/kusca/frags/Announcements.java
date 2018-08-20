package com.aviator.kusca.frags;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.aviator.kusca.R;
import com.vstechlab.easyfonts.EasyFonts;

/**
 * A simple {@link Fragment} subclass.
 */
public class Announcements extends Fragment {


    public Announcements() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View evt=inflater.inflate(R.layout.fragment_announcements, container, false);
        ((TextView)evt.findViewById(R.id.holder)).setTypeface(EasyFonts.caviarDreamsBold(getContext()));
        return  evt;
    }


    public String toString(){
        return "Announcements";
    }
}
