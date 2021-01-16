package com.loginius.loginiusinfotech;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.LinearInterpolator;
import android.widget.RelativeLayout;

import com.agrawalsuneet.dotsloader.loaders.LazyLoader;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ResetPassword extends AppCompatActivity {
    RecyclerView recyclerView;
    List<PasswordModelClass> mList;
    PwdAdapterClass adapterClass;
    RelativeLayout relLay;
    LazyLoader lazyLoader;
    ConstraintLayout cns;
    private  static  final String url="https://maharshiinstitute.com/loginius/getProduct.php";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);
        recyclerView=findViewById(R.id.recyclerviewid);
        cns = findViewById(R.id.main_cns);
        relLay = findViewById(R.id.main_rel);
        lazyLoader = findViewById(R.id.my_progress);
        mList=new ArrayList<>();
        visible();
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
                        PasswordModelClass modalClass=new PasswordModelClass(myObj.getString("username"));
                        //add data in model list
                        mList.add(modalClass);
                    }
                    invisible();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                adapterClass=new PwdAdapterClass(mList,ResetPassword.this);
                recyclerView.setLayoutManager(new LinearLayoutManager(ResetPassword.this));
                recyclerView.setAdapter(adapterClass);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("data", error.toString());
            }
        });


        requestQueue.add(jsonObjectRequest);

    }

    private void invisible() {
        cns.startAnimation(new AlphaAnimation(0, 0));
        relLay.setVisibility(View.INVISIBLE);

    }

    private void visible() {
        LazyLoader loader = new LazyLoader(ResetPassword.this, 30, 20,
                ContextCompat.getColor(ResetPassword.this, R.color.loader_selected),
                ContextCompat.getColor(ResetPassword.this, R.color.loader_selected),
                ContextCompat.getColor(ResetPassword.this, R.color.loader_selected));
        loader.setAnimDuration(500);
        loader.setFirstDelayDuration(100);
        loader.setSecondDelayDuration(200);
        loader.setInterpolator(new LinearInterpolator());
        lazyLoader.addView(loader);
        AlphaAnimation alpha = new AlphaAnimation(0.2F, 0.2F);
        alpha.setDuration(0);
        alpha.setFillAfter(true);
        cns.startAnimation(alpha);
    }

}