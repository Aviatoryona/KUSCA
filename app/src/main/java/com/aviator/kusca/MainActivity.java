package com.aviator.kusca;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ShareCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.aviator.kusca.helpers.MainActivityHelper;
import com.aviator.kusca.models.LatestModel;
import com.aviator.kusca.net.Constants;
import com.aviator.kusca.prefs.MyPreferences;
import com.aviator.kusca.rec.EmptyRecAdapter;
import com.aviator.kusca.rec.LatestAdapaterView;
import com.aviator.kusca.volley.VolleySingleton;
import com.bumptech.glide.Glide;
import com.jpardogo.android.googleprogressbar.library.GoogleProgressBar;
import com.sdsmdg.tastytoast.TastyToast;
import com.vstechlab.easyfonts.EasyFonts;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

import de.hdodenhof.circleimageview.CircleImageView;

@SuppressWarnings("deprecation")
public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private BottomSheetBehavior bottomSheetBehavior;
    private RecyclerView recyclerView;
    private CircleImageView circleImageView;


    private GoogleProgressBar googleProgressBar;
    //empty adp
    CardView cardView;
    TextView textView;
    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

       Init();

    }


    void Init(){

        CollapsingToolbarLayout collapsingToolbarLayout = findViewById(R.id.collapseLayout);
        collapsingToolbarLayout.setTitleEnabled(false);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Volley_LoadData();
            }
        });


        googleProgressBar= findViewById(R.id.google_progress);
        cardView=findViewById(R.id.cardEmpty);
        textView=findViewById(R.id.textView);
        imageView=findViewById(R.id.reloadEmpty);
        textView.setTypeface(EasyFonts.caviarDreamsBold(MainActivity.this));
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Volley_LoadData();
            }
        });


        View bottomSheet = findViewById(R.id.design_bottom_sheet);
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

        recyclerView= findViewById(R.id.recyclerView);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));

        View headerView=navigationView.getHeaderView(0);
        TextView textView=headerView.findViewById(R.id.kuscaTag);
        TextView textView2=headerView.findViewById(R.id.kuscaUserName);
        textView.setTypeface(EasyFonts.caviarDreamsBold(this));
        textView2.setTypeface(EasyFonts.caviarDreamsBold(this));
        circleImageView=headerView.findViewById(R.id.image_profile);
        Glide.with(this).load(Uri.parse(Constants.MEMBER_PIC_URL+new MyPreferences(MainActivity.this).getKucsa_PHOTOURI())).into(circleImageView);




    }

    @Override
    protected void onStart() {
        super.onStart();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                //googleProgressBar.setVisibility(View.GONE);
                Volley_LoadData();
               // recyclerView.setAdapter(new LatestAdapaterView(MainActivity.this));
            }
        },1500);
    }

    void Volley_LoadData(){
         googleProgressBar.setVisibility(View.VISIBLE);
         cardView.setVisibility(View.GONE);
        final RequestQueue requestQueue= VolleySingleton.getInstance(MainActivity.this);
        StringRequest stringRequest=new StringRequest(Request.Method.POST,Constants.API_URL, new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                requestQueue.stop();
                googleProgressBar.setVisibility(View.GONE);
                    try {
                        JSONArray jsonArray=new JSONArray(s);
                        Stack<LatestModel> latestModels=new Stack<>();
                        for (int i = 0; i < jsonArray.length(); i++) {

                            JSONObject jsonObject=jsonArray.getJSONObject(i);
                            LatestModel latestModel=new LatestModel();
                            latestModel.setId(jsonObject.getString("ID"));
                            latestModel.setBody(jsonObject.getString("lt_body"));
                            latestModel.setPic(jsonObject.getString("lt_pic"));
                            latestModel.setTitle(jsonObject.getString("lt_title"));
                            latestModel.setDate(jsonObject.getString("lt_date"));

                            latestModels.add(latestModel);

                        }

                        recyclerView.setAdapter(new LatestAdapaterView(MainActivity.this,latestModels));

                    } catch (JSONException e) {
                        try {
                            JSONObject jsonObject=new JSONObject(s);
                           // recyclerView.setAdapter(new EmptyRecAdapter(MainActivity.this,jsonObject.getString("message")));

                            cardView.setVisibility(View.VISIBLE);
                            textView.setText(jsonObject.getString("message"));
                        } catch (JSONException e1) {
                            //googleProgressBar.setVisibility(View.VISIBLE);
                           // recyclerView.setAdapter(new EmptyRecAdapter(MainActivity.this,"Error encountered"));
                            cardView.setVisibility(View.VISIBLE);
                            textView.setText("Error encountered");
                        }
                    }
            }
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                googleProgressBar.setVisibility(View.GONE);
                requestQueue.stop();
                cardView.setVisibility(View.VISIBLE);
                textView.setText("Connection Error");
            //  TastyToast.makeText(MainActivity.this,"Connection Error",TastyToast.LENGTH_LONG,TastyToast.ERROR);
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params=new HashMap<>();
                params.put("action","getlatest");
                return params;
            }
        };

        requestQueue.add(stringRequest);
        requestQueue.start();

    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else if(bottomSheetBehavior.getState()==BottomSheetBehavior.STATE_EXPANDED){
            bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
        }else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch(id){
            case R.id.action_share:{
                if(bottomSheetBehavior.getState()==BottomSheetBehavior.STATE_EXPANDED){
                    bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
                    return true;
                }
                bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                return true;
            }

            case R.id.action_settings:{
                DatePickerDialog datePickerDialog;
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                    datePickerDialog = new DatePickerDialog(MainActivity.this);
                    datePickerDialog.setTitle("Select Date");
                    datePickerDialog.setCancelable(true);
                    datePickerDialog.setOnDateSetListener(new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

                            String date=year+"/"+month+"/"+dayOfMonth;
                            TastyToast.makeText(MainActivity.this,date,TastyToast.LENGTH_LONG,TastyToast.DEFAULT);

                        }
                    });
                    datePickerDialog.show();
                }

                break;
            }

            case R.id.action_report:{
                TastyToast.makeText(MainActivity.this,"Report Error",TastyToast.LENGTH_SHORT,TastyToast.ERROR);
                return true;
            }
            case R.id.action_more:{
                MainActivityHelper mainActivityHelper=new MainActivityHelper(MainActivity.this);
                mainActivityHelper.ChkLognUserAdmin("check","");

              //  TastyToast.makeText(MainActivity.this,"You MUST have Admin previledges",TastyToast.LENGTH_SHORT,TastyToast.ERROR);
                return true;
            }
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        switch (id){

            case R.id.nav_team:{
                startActivity(new Intent(MainActivity.this,KuscaTeam.class));
                return true;
            }
            case R.id.nav_events:{
                startActivity(new Intent(MainActivity.this,KuscaEvents.class));
                return true;
            }

            case R.id.nav_gallery:{
                startActivity(new Intent(MainActivity.this,KuscaGallery.class));
                return true;
            }

            case R.id.nav_announcements:{
                startActivity(new Intent(MainActivity.this,KuscaAnnouncements.class));
                return true;
            }

            case R.id.nav_contact:{

               Intent intent=new Intent();
                intent.setAction(Intent.ACTION_SEND);
                intent.setData(Uri.parse("email"));
                String[] to={"noreply@kusca.ac.ke"};
                intent.putExtra(Intent.EXTRA_EMAIL,to);
                intent.putExtra(Intent.EXTRA_SUBJECT,"title here...");
                intent.putExtra(Intent.EXTRA_TEXT,"your message...");
                intent.setType("message/rfc822");

                startActivity(Intent.createChooser(intent,"Launch Email"));

                return true;
            }

            case R.id.nav_login:{

                if(new MyPreferences(MainActivity.this).Reset()){
                    finish();
                    startActivity(new Intent(MainActivity.this,FullscreenActivity.class));
                }

                return true;
            }
            case R.id.nav_exit:{
                finish();
                return true;
            }

            case R.id.nav_socail:{

                return true;
            }

        }


        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

}
