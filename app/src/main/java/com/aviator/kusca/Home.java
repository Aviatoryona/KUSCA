package com.aviator.kusca;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.aviator.kusca.volley.VolleySingleton;

public class Home extends AppCompatActivity {
    TextView textView;
    View bottomSheet;
    BottomSheetBehavior bottomSheetBehavior;
    CollapsingToolbarLayout collapsingToolbarLayout;

   // private FloatingNavigationView floatingNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        collapsingToolbarLayout= findViewById(R.id.collapseLayout);
        collapsingToolbarLayout.setTitleEnabled(false);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
//        toolbar.setNavigationIcon(R.drawable.ic_menu_black_24dp);
//        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                if(bottomSheetBehavior.getState()==BottomSheetBehavior.STATE_EXPANDED){
//                    bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
//                    return;
//                }
//                bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
//
//            }
//        });


        Init();
    }

    void Init(){

        textView= findViewById(R.id.fullscreen_content);
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Volley_LoadData();
            }
        });


        bottomSheet=findViewById(R.id.design_bottom_sheet);
        bottomSheetBehavior=BottomSheetBehavior.from(bottomSheet);
        bottomSheetBehavior.setHideable(true);
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
        bottomSheetBehavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {

            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {

            }
        });

    }


    void Volley_LoadData(){

        RequestQueue requestQueue= VolleySingleton.getInstance(Home.this);
        StringRequest stringRequest=new StringRequest(Request.Method.POST,"http://192.168.42.177:9190/kusca-realajax/test.php", new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                if(s!=null){
                    textView.setText(s);
                }
                Toast.makeText(Home.this, s, Toast.LENGTH_SHORT).show();
            }
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                textView.setText(volleyError.getMessage());
                Toast.makeText(Home.this,volleyError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        requestQueue.add(stringRequest);
        requestQueue.start();

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }
        if (id == R.id.action_share) {
            if(bottomSheetBehavior.getState()==BottomSheetBehavior.STATE_EXPANDED){
                bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
                return true;
            }
            bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {

    }


}
