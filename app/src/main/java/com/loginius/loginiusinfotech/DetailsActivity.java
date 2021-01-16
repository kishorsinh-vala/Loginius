package com.loginius.loginiusinfotech;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.LinearInterpolator;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.agrawalsuneet.dotsloader.loaders.LazyLoader;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.navigation.NavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;

public class DetailsActivity extends AppCompatActivity {

    private  static  final String url="https://maharshiinstitute.com/loginius/countCourse.php";
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mToggle;
    RecyclerView recyclerView;
    List<ModalClass> mList;
    AdapterClass adapterClass;
    RelativeLayout relLay;
    LazyLoader lazyLoader;
    ConstraintLayout cns;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        cns = findViewById(R.id.main_cns);
        relLay = findViewById(R.id.main_rel);
        lazyLoader = findViewById(R.id.my_progress);
        recyclerView=findViewById(R.id.recyclerviewid);
        LazyLoader loader = new LazyLoader(DetailsActivity.this, 30, 20,
                ContextCompat.getColor(DetailsActivity.this, R.color.loader_selected),
                ContextCompat.getColor(DetailsActivity.this, R.color.loader_selected),
                ContextCompat.getColor(DetailsActivity.this, R.color.loader_selected));
        loader.setAnimDuration(500);
        loader.setFirstDelayDuration(100);
        loader.setSecondDelayDuration(200);
        loader.setInterpolator(new LinearInterpolator());
        lazyLoader.addView(loader);
        AlphaAnimation alpha = new AlphaAnimation(0.2F, 0.2F);
        alpha.setDuration(0);
        alpha.setFillAfter(true);
        cns.startAnimation(alpha);


        mList=new ArrayList<>();
        //SET NAVIGATION DRAWER
        mDrawerLayout=findViewById(R.id.det_drawer);
        mToggle=new ActionBarDrawerToggle(DetailsActivity.this,mDrawerLayout,R.string.open,R.string.close);
        mDrawerLayout.addDrawerListener(mToggle);
        mToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //SET uSER EMAIL
        NavigationView navigationView = (NavigationView) findViewById(R.id.det_nav);
        View hView =  navigationView.getHeaderView(0);

        TextView nav_user = hView.findViewById(R.id.textEmail);
        //GET EMAIL
        SharedPreferences preferences=getSharedPreferences("Login",MODE_PRIVATE);
        String chk=preferences.getString("email","");
        nav_user.setText("Welcome : "+chk);

        final RequestQueue requestQueue= Volley.newRequestQueue(this);
        JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
//                Log.d("data", response.toString());
                try {
                    JSONObject jsonObject=new JSONObject(String.valueOf(response));
                    JSONArray data=jsonObject.getJSONArray("data");
                    for (int i=0;i<data.length();i++)
                    {
                        JSONObject myObj=data.getJSONObject(i);
                        //create istance of class to add value in layout
                        ModalClass modalClass=new ModalClass(myObj.getString("course"),myObj.getString("count"));
                        //add data in model list
                        mList.add(modalClass);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                adapterClass=new AdapterClass(mList,DetailsActivity.this);
                recyclerView.setLayoutManager(new LinearLayoutManager(DetailsActivity.this));
                recyclerView.setAdapter(adapterClass);
                relLay.setVisibility(View.INVISIBLE);
                cns.startAnimation(new AlphaAnimation(0, 0));
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("data", error.toString());
            }
        });


        requestQueue.add(jsonObjectRequest);
    }

    //DRAWABLE OPTION ITEM SELECT


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(mToggle.onOptionsItemSelected(item))
        {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    public void addAtd(MenuItem item)
    {
        startActivity(new Intent(DetailsActivity.this,AttendanceActivity.class));
    }
    public void resetPassword(MenuItem item)
    {
        startActivity(new Intent(DetailsActivity.this,ResetPassword.class));
    }
    public void addCourse(MenuItem item)
    {

        startActivity(new Intent(DetailsActivity.this,AddCourseActivity.class));
    }

    public void addStudent(MenuItem item)
    {
        startActivity(new Intent(DetailsActivity.this,AddStudentActivity.class));
    }

    public void Logout(MenuItem item) {
        finish();
        startActivity(new Intent(DetailsActivity.this,MainActivity.class));
    }

    public void addProject(MenuItem item) {
        startActivity(new Intent(DetailsActivity.this,AddProjectActivity.class));
    }

    public void viewReport(MenuItem item) {
        startActivity(new Intent(DetailsActivity.this,ReportActivity.class));
    }
}