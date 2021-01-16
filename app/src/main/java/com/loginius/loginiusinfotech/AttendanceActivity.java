package com.loginius.loginiusinfotech;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.agrawalsuneet.dotsloader.loaders.LazyLoader;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AttendanceActivity extends AppCompatActivity {
    private static final String url = "https://maharshiinstitute.com/loginius/getUserAll.php";
    private static final String url1 = "https://maharshiinstitute.com/loginius/AddAtd.php";
    List<AttendanceModel> mList;
    AttendanceAdapter adapterClass;
    RecyclerView recyclerView;
    RelativeLayout relLay;
    LazyLoader lazyLoader;
    LinearLayout cns;
    int datax=0,count=0;
    Button add_atd;
    TextView txtnm, txtp, txta, txtl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendance);
        mList = new ArrayList<>();

        recyclerView = findViewById(R.id.recyclerview);
        add_atd = findViewById(R.id.add_atd);
        txtnm = findViewById(R.id.attd_unm);
        txtp = findViewById(R.id.attd_p);
        txtl = findViewById(R.id.attd_l);
        txta = findViewById(R.id.attd_a);
        cns = findViewById(R.id.attd_Lnr);
        relLay = findViewById(R.id.main_rel);
        relLay.setVisibility(View.INVISIBLE);
        lazyLoader = findViewById(R.id.my_progress);
        visibleLoader();
        final RequestQueue requestQueue = Volley.newRequestQueue(this);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONObject jsonObject = new JSONObject(String.valueOf(response));
                    JSONArray data = jsonObject.getJSONArray("data");
                    for (int i = 0; i < data.length(); i++) {
                        JSONObject myObj = data.getJSONObject(i);
                        //create istance of class to add value in layout
                        AttendanceModel modalClass = new AttendanceModel(myObj.getString("firstname"), myObj.getString("lastname"));
                        //add data in model list
                        mList.add(modalClass);
                    }
                } catch (JSONException e) {
                    Log.d("error", response.toString());
                    e.printStackTrace();
                }
                adapterClass = new AttendanceAdapter(AttendanceActivity.this, mList);
                recyclerView.setLayoutManager(new LinearLayoutManager(AttendanceActivity.this));
                recyclerView.setAdapter(adapterClass);
                invisibleLoader();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("data", error.toString());
            }
        });
        requestQueue.add(jsonObjectRequest);

        /* BUTTON CLICK */
        add_atd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                count = recyclerView.getAdapter().getItemCount();
//                Toast.makeText(AttendanceActivity.this, ""+count, Toast.LENGTH_SHORT).show();
                for (int i = 0; i < count; i++) {
                    LinearLayout itemLayout = (LinearLayout) recyclerView.getChildAt(i);
                    RadioGroup rg = itemLayout.findViewById(R.id.rd_grp);
                    TextView nm = itemLayout.findViewById(R.id.unm);
                    String username = nm.getText().toString();
                    RadioButton radioButton;
                    int selectedId = rg.getCheckedRadioButtonId();
                    radioButton = itemLayout.findViewById(selectedId);
                    String Attd = radioButton.getText().toString();
//                    Toast.makeText(AttendanceActivity.this, radioButton.getText(), Toast.LENGTH_SHORT).show();
//                    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    Date date = new Date();
                    String day = (String) DateFormat.format("dd", date); // 20
                    String monthNumber = (String) DateFormat.format("MM", date); // 06
//                    Toast.makeText(AttendanceActivity.this, " "+day+" "+monthNumber, Toast.LENGTH_SHORT).show();
//                    Log.d("name",username+" "+Attd+" "+day+" "+monthNumber);
                    nm.setVisibility(View.INVISIBLE);

                    itemLayout.findViewById(R.id.attd_p1).setVisibility(View.INVISIBLE);
                    itemLayout.findViewById(R.id.attd_a1).setVisibility(View.INVISIBLE);
                    itemLayout.findViewById(R.id.attd_l1).setVisibility(View.INVISIBLE);
                    SharedPreferences preferences = getSharedPreferences("Login", MODE_PRIVATE);
                    String admin = preferences.getString("email", "");
                    AddAttendance(username, day, monthNumber, Attd, admin);
                }
            }
        });
    }
    private void AddAttendance(final String username, final String day, final String monthNumber, final String attd, final String admin) {
        visibleLoader();
        StringRequest request = new StringRequest(Request.Method.POST, url1, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response.contains("1")) {
                    invisibleLoader();
                    Toast.makeText(AttendanceActivity.this, "Attendance Add.", Toast.LENGTH_SHORT).show();
                    finish();
                } else if (response.contains("2")) {
                    invisibleLoader();
                    datax++;
                    if(datax>0 && count<=datax)
                    {
                        Toast.makeText(AttendanceActivity.this, "Already taken", Toast.LENGTH_SHORT).show();
                    }
                    finish();
                } else {
                    invisibleLoader();
                    Toast.makeText(AttendanceActivity.this, "Sorry Something Wrong.", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("username", username);
                if(day.equals("01")||day.equals("02")||day.equals("03")||day.equals("04")||day.equals("05")
                        ||day.equals("06")||day.equals("07")||day.equals("08")||day.equals("09"))
                {
                    params.put("day", day.replace("0",""));
                }
                else
                {
                    params.put("day", day);
                }
                if(monthNumber.equals("01")||monthNumber.equals("02")||monthNumber.equals("03")|| monthNumber.equals("04")
                        ||monthNumber.equals("05") ||monthNumber.equals("06")
                        ||monthNumber.equals("07")||monthNumber.equals("08")||monthNumber.equals("09"))
                {
                    params.put("month", monthNumber.replace("0",""));
                }
                else
                {
                    params.put("month", monthNumber);
                }
                params.put("Attd", attd);
                params.put("admin", admin);
                return params;
            }
        };
        Volley.newRequestQueue(AttendanceActivity.this).add(request);

    }

    private void invisibleLoader() {
        cns.startAnimation(new AlphaAnimation(0, 0));
        relLay.setVisibility(View.INVISIBLE);
        txtnm.setEnabled(true);
        txtp.setEnabled(true);
        txtl.setEnabled(true);
        txta.setEnabled(true);
        add_atd.setEnabled(true);

    }

    private void visibleLoader() {
        relLay.setVisibility(View.VISIBLE);
        LazyLoader loader = new LazyLoader(AttendanceActivity.this, 30, 20,
                ContextCompat.getColor(AttendanceActivity.this, R.color.loader_selected),
                ContextCompat.getColor(AttendanceActivity.this, R.color.loader_selected),
                ContextCompat.getColor(AttendanceActivity.this, R.color.loader_selected));
        loader.setAnimDuration(500);
        loader.setFirstDelayDuration(100);
        loader.setSecondDelayDuration(200);
        loader.setInterpolator(new LinearInterpolator());
        lazyLoader.addView(loader);

        AlphaAnimation alpha = new AlphaAnimation(0.2F, 0.2F);
        alpha.setDuration(0);
        alpha.setFillAfter(true);
        cns.startAnimation(alpha);
        txtnm.setEnabled(false);
        txtp.setEnabled(false);
        txtl.setEnabled(false);
        txta.setEnabled(false);
        add_atd.setEnabled(false);
    }
}