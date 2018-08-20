package com.aviator.kusca.rec;

import android.support.v7.widget.RecyclerView;

import com.aviator.kusca.db.MyDb;
import com.aviator.kusca.models.Candidate;

/**
 * Created by Aviator on 11/28/2017.
 */

public interface Disselector {
     void disSelectCheckBox(ElectionRecAdapter.ElectionRecViewHolder recViewHolder,String position);  //position
     void Update(String position,String pid,MyDb myDb, Candidate candidate);
}
