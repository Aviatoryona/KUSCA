package com.aviator.kusca.frags;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.aviator.kusca.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class Galleriy extends Fragment {


    public Galleriy() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_galleriy, container, false);
    }

    public String toString(){
        return "Galleriy";
    }
}
