package com.loginius.loginiusinfotech;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;

import android.app.DatePickerDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.agrawalsuneet.dotsloader.loaders.LazyLoader;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class AddProjectActivity extends AppCompatActivity {
    DatePickerDialog datepicker;
    String chk;
    EditText prtNm, amount, ref,dev;
    TextView stDate, dueDate;
    Button btn_clk;
    RelativeLayout relLay;
    LazyLoader lazyLoader;
    ConstraintLayout cns;
    private static final String url = "https://maharshiinstitute.com/loginius/AddProject.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_project);
        SharedPreferences preferences = getSharedPreferences("Login", MODE_PRIVATE);
        chk = preferences.getString("email", "");
        prtNm = findViewById(R.id.prj_prtNm);
        amount = findViewById(R.id.prj_amount);
        ref = findViewById(R.id.prj_ref);
        dev = findViewById(R.id.prj_dev);
        stDate = findViewById(R.id.prj_st_date);
        dueDate = findViewById(R.id.prj_due_date);
        cns = findViewById(R.id.main_cns);
        relLay = findViewById(R.id.main_rel);
        lazyLoader = findViewById(R.id.my_progress);
        btn_clk=findViewById(R.id.prj_add);
        relLay.setVisibility(View.INVISIBLE);
        /*Start Date Set Start*/
        stDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar cldr = Calendar.getInstance();
                int day = cldr.get(Calendar.DAY_OF_MONTH);
                int month = cldr.get(Calendar.MONTH);
                int year = cldr.get(Calendar.YEAR);
                datepicker = new DatePickerDialog(AddProjectActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                stDate.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
                            }
                        }, year, month, day);
                datepicker.show();
            }
        });
        /*Start date Set Over*/
        /*Due Date Set Start*/
        dueDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar cldr = Calendar.getInstance();
                int day = cldr.get(Calendar.DAY_OF_MONTH);
                int month = cldr.get(Calendar.MONTH);
                int year = cldr.get(Calendar.YEAR);
                datepicker = new DatePickerDialog(AddProjectActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                dueDate.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
                            }
                        }, year, month, day);
                datepicker.show();
            }
        });
        /*Due Date Set Over*/
        /*Add Project Button Start*/
        btn_clk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                relLay.setVisibility(View.VISIBLE);
                LazyLoader loader = new LazyLoader(AddProjectActivity.this, 30, 20,
                        ContextCompat.getColor(AddProjectActivity.this, R.color.loader_selected),
                        ContextCompat.getColor(AddProjectActivity.this, R.color.loader_selected),
                        ContextCompat.getColor(AddProjectActivity.this, R.color.loader_selected));
                loader.setAnimDuration(500);
                loader.setFirstDelayDuration(100);
                loader.setSecondDelayDuration(200);
                loader.setInterpolator(new LinearInterpolator());

                lazyLoader.addView(loader);
                final String pnm,prc,refr,sd,dd,usr;
                pnm=prtNm.getText().toString();
                prc=amount.getText().toString();
                refr=ref.getText().toString();
                sd=stDate.getText().toString();
                dd=dueDate.getText().toString();
                usr=dev.getText().toString();
                if(pnm.isEmpty() || prc.isEmpty() || usr.isEmpty() || sd.isEmpty() || dd.isEmpty())
                {
                    relLay.setVisibility(View.INVISIBLE);
                    if (pnm.isEmpty() || pnm.equals("")) {
                        prtNm.setError("Please Enter Party Name");
                    }
                    if (prc.isEmpty() || prc.equals("")) {
                        amount.setError("Please Enter Amount");
                    }
                    if (usr.isEmpty() || usr.equals("")) {
                        dev.setError("Please Enter Developer Name");
                    }
                    if (sd.isEmpty() || sd.equals("")) {
                        stDate.setError("Please Select Start Date");
                    }
                    if (dd.isEmpty() || dd.equals("")) {
                        dueDate.setError("Please Select Due Dat");
                    }
                }
                else
                {

                    if (refr.isEmpty() || refr.equals("")) {
                        ref.setText("Self");
                    }
                    AlphaAnimation alpha = new AlphaAnimation(0.2F, 0.2F);
                    alpha.setDuration(0);
                    alpha.setFillAfter(true);
                    cns.startAnimation(alpha);
                    btn_clk.setEnabled(false);
                    prtNm.setEnabled(false);
                    amount.setEnabled(false);
                    ref.setEnabled(false);
                    dev.setEnabled(false);
                    stDate.setEnabled(false);
                    dueDate.setEnabled(false);
                    StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {

                            if (response.contains("1")) {
                                Toast.makeText(AddProjectActivity.this, "Project Added...", Toast.LENGTH_SHORT).show();
                                finish();
                            }
                            else if(response.contains("User Exist")){
                                Toast.makeText(AddProjectActivity.this, "Project Already Exist!", Toast.LENGTH_SHORT).show();
                            }
                            else {

                                Toast.makeText(AddProjectActivity.this, "Sorry Something Wrong.", Toast.LENGTH_SHORT).show();
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
                            params.put("insert",chk );
                            params.put("prty",pnm );
                            params.put("price",prc );
                            params.put("ref",ref.getText().toString() );
                            params.put("dev",usr );
                            params.put("sdate",sd );
                            params.put("edate",dd);
                            return params;
                        }
                    };
                    Volley.newRequestQueue(AddProjectActivity.this).add(request);
                }

            }
        });
        /*Add Project Button Over*/
    }
}