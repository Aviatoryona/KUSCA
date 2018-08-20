package com.aviator.kusca.frags;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.aviator.kusca.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class Latest extends Fragment {

    LinearLayout linearLayout;

    public Latest() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View V=inflater.inflate(R.layout.fragment_latest, container, false);
        Init(V);
        return V;
    }


    void Init(View view){
        linearLayout=view.findViewById(R.id.list_container);
        dealListView();
    }


    private void dealListView() {
        LayoutInflater layoutInflater = LayoutInflater.from(getContext());

        for (int i = 0; i < 20; i++) {
            View childView = layoutInflater.inflate(R.layout.latest_model, null);
            linearLayout.addView(childView);
            ImageView headView = childView.findViewById(R.id.pic);
            headView.setImageResource(R.mipmap.hero_image_studio);
            TextView news=childView.findViewById(R.id.news);
            news.setText(R.string.large_text);
//            if (i < headStrs.length) {
//                headView.setImageResource(imageIds[i % imageIds.length]);
//                ViewCompat.setTransitionName(headView, headStrs[i]);
//            }
        }
    }
}
