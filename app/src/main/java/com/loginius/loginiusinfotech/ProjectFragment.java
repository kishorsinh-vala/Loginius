package com.loginius.loginiusinfotech;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.LinearInterpolator;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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

public class ProjectFragment extends Fragment {
    RecyclerView recyclerView;
    Context context;
    List<ProjectModelClass> mList;
    ProjectAdapterClass adapterClass;
    ImageButton prj;
    RelativeLayout relLay;
    LazyLoader lazyLoader;
    ConstraintLayout cns;

    public ProjectFragment(Context context) {
        this.context=context;
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mList = new ArrayList<>();


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_project, container, false);
        try {
            recyclerView = view.findViewById(R.id.rv_tst);
            cns = view.findViewById(R.id.main_cns);
            relLay = view.findViewById(R.id.main_rel);
            lazyLoader = view.findViewById(R.id.my_progress);
            prj=view.findViewById(R.id.img_btn_prj);
            LazyLoader loader = new LazyLoader(context, 30, 20,
                    ContextCompat.getColor(context, R.color.loader_selected),
                    ContextCompat.getColor(context, R.color.loader_selected),
                    ContextCompat.getColor(context, R.color.loader_selected));
            loader.setAnimDuration(500);
            loader.setFirstDelayDuration(100);
            loader.setSecondDelayDuration(200);
            loader.setInterpolator(new LinearInterpolator());
            AlphaAnimation alpha = new AlphaAnimation(0.2F, 0.2F);
            alpha.setDuration(0);
            alpha.setFillAfter(true);
            cns.startAnimation(alpha);
            prj.setEnabled(false);
            getData();

        } catch (Exception e) {
            Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
        return view;
    }

    private void getData() {
        final String url = "https://maharshiinstitute.com/loginius/getProject.php";

        final RequestQueue requestQueue = Volley.newRequestQueue(context);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
//                Log.d("data", response.toString());
                try {
                    JSONObject jsonObject = new JSONObject(String.valueOf(response));
                    JSONArray data = jsonObject.getJSONArray("data");
                    for (int i = 0; i < data.length(); i++) {
                        JSONObject myObj = data.getJSONObject(i);
                        //create istance of class to add value in layout
                        ProjectModelClass modalClass=new ProjectModelClass(myObj.getString("pnm"),myObj.getString("ref"),myObj.getString("start"),myObj.getString("due"),myObj.getString("amount"),myObj.getString("dev"));
                        //add data in model list
                        mList.add(modalClass);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                adapterClass = new ProjectAdapterClass(mList, context);
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
                recyclerView.setAdapter(adapterClass);
                relLay.setVisibility(View.INVISIBLE);
                cns.startAnimation(new AlphaAnimation(0, 0));
                prj.setEnabled(true);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("data", error.toString());
            }
        });
        requestQueue.add(jsonObjectRequest);
    }
}
