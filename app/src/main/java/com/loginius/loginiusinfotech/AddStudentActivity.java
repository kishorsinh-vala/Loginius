package com.loginius.loginiusinfotech;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.LinearInterpolator;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Spinner;
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
import java.util.HashMap;
import java.util.Map;

import static com.loginius.loginiusinfotech.R.drawable.ic_email;
import static com.loginius.loginiusinfotech.R.drawable.ic_email_err;
import static com.loginius.loginiusinfotech.R.drawable.round_border;
import static com.loginius.loginiusinfotech.R.drawable.round_border_err;

public class AddStudentActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private ArrayList<String> courseName,courseFee;
    Spinner spinner;
    EditText txt_fnm,txt_lnm,txt_mob,txt_fee;
    Button add_stud;
    RelativeLayout relLay;
    LazyLoader lazyLoader;
    ConstraintLayout cns;
    CheckBox chkFee,chkCrs;
    String crsPaid,crsComp;
    String spnVal;
//    AdapterClass1 adapterClass;
    private  static  final String url="https://maharshiinstitute.com/loginius/getCourse.php";
        private  static  final String URL_ADDSTUD="https://maharshiinstitute.com/loginius/addStud.php";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_student);
        spinner=findViewById(R.id.stud_course);
            spinner.setOnItemSelectedListener(this);
        txt_fnm=findViewById(R.id.stud_fnm);
        txt_lnm=findViewById(R.id.stud_lnm);
        txt_mob=findViewById(R.id.stud_mobile);
        txt_fee=findViewById(R.id.stud_fee);
        cns = findViewById(R.id.main_cns);
        relLay = findViewById(R.id.main_rel);
        lazyLoader = findViewById(R.id.my_progress);
        add_stud=findViewById(R.id.stud_add);
        chkCrs=findViewById(R.id.chk_CRS);
        chkFee=findViewById(R.id.chk_fee);
        courseName=new ArrayList<>();
        courseFee=new ArrayList<>();
        courseName.clear();
        courseName.add("SELECT COURSE");
        courseFee.add("");
        relLay.setVisibility(View.INVISIBLE);
        crsPaid="NO";
        crsComp="NO";
        loadSpinnerData();


        /* CHECKBOX CHECK*/
        //FEE pAID
        chkFee.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(buttonView.isChecked())
                {
                    crsPaid="YES";
                }
                else if(!buttonView.isChecked())
                {
                    crsPaid="NO";
                }
            }
        });
        //COURSE COMPELETE
        chkCrs.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(buttonView.isChecked())
                {
                    crsComp="YES";
                }
                else if(!buttonView.isChecked())
                {
                    crsComp="NO";
                }
            }
        });

        /*BUTTON CLICK*/
        add_stud.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                visibleLoader();
                if(txt_fnm.getText().toString().isEmpty() || txt_lnm.getText().toString().isEmpty() || txt_mob.getText().toString().isEmpty() || txt_fee.getText().toString().isEmpty()|| spnVal.equals("SELECT COURSE"))
                {
                    invisibleLoader();
                    if(txt_fnm.getText().toString().isEmpty())
                    {
                        txt_fnm.setCompoundDrawablesWithIntrinsicBounds(ic_email_err,
                                0,
                                0,
                                0);
                        txt_fnm.setBackgroundResource(round_border_err);
                        txt_fnm.setError("Please Enter First Name.");
                    }
                    if(txt_lnm.getText().toString().isEmpty())
                    {
                        txt_lnm.setCompoundDrawablesWithIntrinsicBounds(ic_email_err,
                                0,
                                0,
                                0);
                        txt_lnm.setBackgroundResource(round_border_err);
                        txt_lnm.setError("Please Enter Last Name.");
                    }
                    if(txt_mob.getText().toString().isEmpty() || txt_mob.getText().length()<=10)
                    {
                        if(txt_mob.getText().toString().isEmpty()) {
                            txt_mob.setCompoundDrawablesWithIntrinsicBounds(ic_email_err,
                                    0,
                                    0,
                                    0);
                            txt_mob.setBackgroundResource(round_border_err);
                            txt_mob.setError("Please Enter Mobile Number.");
                        }
                        else
                        {
                            txt_mob.setCompoundDrawablesWithIntrinsicBounds(ic_email_err,
                                    0,
                                    0,
                                    0);
                            txt_mob.setBackgroundResource(round_border_err);
                            txt_mob.setError("Please Enter Valid Mobile Number.");
                        }
                    }
                    if(txt_fee.getText().toString().isEmpty())
                    {
                        txt_fee.setCompoundDrawablesWithIntrinsicBounds(ic_email_err,
                                0,
                                0,
                                0);
                        txt_fee.setBackgroundResource(round_border_err);
                        txt_fee.setError("Please Enter Fee.");
                    }
                    if(spnVal.equals("SELECT COURSE"))
                    {
//                        spinner.setPopupBackgroundResource(round_border_err);
                        ((TextView)spinner.getSelectedView()).setCompoundDrawablesWithIntrinsicBounds(ic_email_err,
                                0,
                                0,
                                0);
                       spinner.setBackgroundResource(round_border_err);
                        ((TextView)spinner.getSelectedView()).setError("Please Select Valide Course.");
                    }
                }
                else
                {
                    visibleLoader();
                    /*INSERT STUDENT*/
                    StringRequest request = new StringRequest(Request.Method.POST, URL_ADDSTUD, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            if (response.contains("Successfull Registered")) {
//                                Log.d("url",response.toString());
                                invisibleLoader();
                                Toast.makeText(AddStudentActivity.this, "Student Added...", Toast.LENGTH_LONG).show();
                                finish();
                            }
                            else if(response.contains("User Exist")){
                                Toast.makeText(AddStudentActivity.this, "Student Already Exist!!!", Toast.LENGTH_SHORT).show();
                            }
                            else {
                                invisibleLoader();
                                Toast.makeText(AddStudentActivity.this, "Sorry Some Thing Wrong!!!", Toast.LENGTH_SHORT).show();
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
                            params.put("firstName", txt_fnm.getText().toString());
                            params.put("lastName", txt_lnm.getText().toString());
                            params.put("mobile", txt_mob.getText().toString());
                            params.put("courseName", spnVal);
                            params.put("courseFee", txt_fee.getText().toString());
                            params.put("paid", crsPaid);
                            params.put("courseCmp",crsComp );
                            return params;
                        }
                    };
                    Volley.newRequestQueue(AddStudentActivity.this).add(request);

                }
            }
        });
        //OVER CLICK
        /*TXT CHANGE*/

        //FNM
        txt_fnm.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                txt_fnm.setBackgroundResource(R.drawable.round_border);
                txt_fnm.setCompoundDrawablesWithIntrinsicBounds(ic_email,
                        0,
                        0,
                        0);
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        //LNM
        txt_lnm.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                txt_lnm.setBackgroundResource(R.drawable.round_border);
                txt_lnm.setCompoundDrawablesWithIntrinsicBounds(ic_email,
                        0,
                        0,
                        0);
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        //Mob
        txt_mob.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                txt_mob.setBackgroundResource(R.drawable.round_border);
                txt_mob.setCompoundDrawablesWithIntrinsicBounds(ic_email,
                        0,
                        0,
                        0);
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        //FEE
        txt_fee.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                txt_fee.setBackgroundResource(R.drawable.round_border);
                txt_fee.setCompoundDrawablesWithIntrinsicBounds(ic_email,
                        0,
                        0,
                        0);
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        /*TXT CHANGE OVER*/
    }
    public void visibleLoader()
    {
        LazyLoader loader = new LazyLoader(AddStudentActivity.this, 30, 20,
                ContextCompat.getColor(AddStudentActivity.this, R.color.loader_selected),
                ContextCompat.getColor(AddStudentActivity.this, R.color.loader_selected),
                ContextCompat.getColor(AddStudentActivity.this, R.color.loader_selected));
        loader.setAnimDuration(500);
        loader.setFirstDelayDuration(100);
        loader.setSecondDelayDuration(200);
        loader.setInterpolator(new LinearInterpolator());
        lazyLoader.addView(loader);
        relLay.setVisibility(View.VISIBLE);


        AlphaAnimation alpha = new AlphaAnimation(0.2F, 0.2F);
        alpha.setDuration(0);
        alpha.setFillAfter(true);
        cns.startAnimation(alpha);
        txt_fnm.setEnabled(false);
        txt_lnm.setEnabled(false);
        txt_mob.setEnabled(false);
        txt_fee.setEnabled(false);
        add_stud.setEnabled(false);
    }

    public void invisibleLoader()
    {
        LazyLoader loader = new LazyLoader(AddStudentActivity.this, 30, 20,
                ContextCompat.getColor(AddStudentActivity.this, R.color.loader_selected),
                ContextCompat.getColor(AddStudentActivity.this, R.color.loader_selected),
                ContextCompat.getColor(AddStudentActivity.this, R.color.loader_selected));
        loader.setAnimDuration(500);
        loader.setFirstDelayDuration(100);
        loader.setSecondDelayDuration(200);
        loader.setInterpolator(new LinearInterpolator());
        lazyLoader.addView(loader);
        cns.startAnimation(new AlphaAnimation(0, 0));
        relLay.setVisibility(View.INVISIBLE);

        txt_fnm.setEnabled(true);
        txt_lnm.setEnabled(true);
        txt_mob.setEnabled(true);
        txt_fee.setEnabled(true);
        add_stud.setEnabled(true);
    }
    private void loadSpinnerData() {

        final RequestQueue requestQueue= Volley.newRequestQueue(this);
        JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
//                Log.d("data", response.toString());
                try {
                    JSONObject jsonObject=new JSONObject(String.valueOf(response));
                    JSONArray data=jsonObject.getJSONArray("data");
                    Log.d("data",jsonObject.toString());

                    for(int i=0;i<data.length();i++)
                    {
                        JSONObject myObj=data.getJSONObject(i);
                        String cnm=myObj.getString("courseName");
                        courseName.add(cnm);
                        String fee=myObj.getString("courseFee");
                        courseFee.add(fee);
                    }
                    ArrayAdapter adapter=new ArrayAdapter(AddStudentActivity.this,android.R.layout.simple_list_item_1,courseName);
                    spinner.setAdapter(adapter);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("data", error.toString());
            }
        });


        requestQueue.add(jsonObjectRequest);

    }
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        ((TextView) view).setPadding(0, 0, 0, 0);
        ((TextView)view) .setCompoundDrawablesWithIntrinsicBounds(AppCompatResources.getDrawable(AddStudentActivity.this, ic_email), null, null, null);
        ((TextView) view).setCompoundDrawablePadding(20);// to set
        spinner.setBackgroundResource(round_border);
        spnVal=parent.getItemAtPosition(position).toString();
//        Log.d("Fee",courseFee.get(position));
        txt_fee.setText(courseFee.get(position));
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}