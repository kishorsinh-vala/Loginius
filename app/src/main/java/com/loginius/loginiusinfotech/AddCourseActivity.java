package com.loginius.loginiusinfotech;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;

import android.os.Bundle;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.agrawalsuneet.dotsloader.loaders.LazyLoader;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

import static com.loginius.loginiusinfotech.R.drawable.ic_email_err;
import static com.loginius.loginiusinfotech.R.drawable.round_border_err;

public class AddCourseActivity extends AppCompatActivity {

    EditText txt_course,txt_courseTime,txt_courseFee;
    Button btn_addCourse;
    RelativeLayout relLay;
    LazyLoader lazyLoader;
    ConstraintLayout cns;
    private static final String url = "https://maharshiinstitute.com/loginius/addCourse.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_course);
        txt_course=findViewById(R.id.course_nm);
        txt_courseFee=findViewById(R.id.course_fee);
        txt_courseTime=findViewById(R.id.course_time);
        cns = findViewById(R.id.main_cns);
        lazyLoader = findViewById(R.id.my_progress);
        btn_addCourse=findViewById(R.id.course_add);
        relLay = findViewById(R.id.main_rel);
        relLay.setVisibility(View.INVISIBLE);

        //BUTTON CLICK
        btn_addCourse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(txt_course.getText().toString().isEmpty() || txt_courseTime.getText().toString().isEmpty() || txt_courseFee.getText().toString().isEmpty())
                {
                    if(txt_course.getText().toString().isEmpty())
                    {
                        txt_course.setCompoundDrawablesWithIntrinsicBounds(ic_email_err,
                                0,
                                0,
                                0);
                        txt_course.setBackgroundResource(round_border_err);
                        txt_course.setError("Please Enter Course Name.");
                    }
                    if(txt_courseTime.getText().toString().isEmpty())
                    {
                        txt_courseTime.setCompoundDrawablesWithIntrinsicBounds(ic_email_err,
                                0,
                                0,
                                0);
                        txt_courseTime.setBackgroundResource(round_border_err);
                        txt_courseTime.setError("Please Enter Course Duration.");
                    }
                    if(txt_courseFee.getText().toString().isEmpty())
                    {
                        txt_courseFee.setCompoundDrawablesWithIntrinsicBounds(ic_email_err,
                                0,
                                0,
                                0);
                        txt_courseFee.setBackgroundResource(round_border_err);
                        txt_courseFee.setError("Please Enter Course Fees.");
                    }
                }
                else
                {
                    visibleLoader();
                    StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            if (response.contains("1")) {
                                Toast.makeText(AddCourseActivity.this, "Course Added.", Toast.LENGTH_LONG).show();
                                finish();
                            }
                            else if(response.contains("User Exist")){
                                invisibleLoader();
                                Toast.makeText(AddCourseActivity.this, "Course Already Exist!", Toast.LENGTH_SHORT).show();
                            }
                            else {
                                invisibleLoader();
                                Toast.makeText(AddCourseActivity.this, "Sorry Something Wrong.", Toast.LENGTH_SHORT).show();

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
                            params.put("course", txt_course.getText().toString());
                            params.put("duration", txt_courseTime.getText().toString());
                            params.put("fee", txt_courseFee.getText().toString());
                            return params;
                        }
                    };
                    Volley.newRequestQueue(AddCourseActivity.this).add(request);
                }

            }
        });
        //OVER CLICK
        //TXT CHANGE



    }

    public void visibleLoader()
    {
        relLay.setVisibility(View.VISIBLE);
        LazyLoader loader = new LazyLoader(AddCourseActivity.this, 30, 20,
                ContextCompat.getColor(AddCourseActivity.this, R.color.loader_selected),
                ContextCompat.getColor(AddCourseActivity.this, R.color.loader_selected),
                ContextCompat.getColor(AddCourseActivity.this, R.color.loader_selected));
        loader.setAnimDuration(500);
        loader.setFirstDelayDuration(100);
        loader.setSecondDelayDuration(200);
        loader.setInterpolator(new LinearInterpolator());
        lazyLoader.addView(loader);

        AlphaAnimation alpha = new AlphaAnimation(0.2F, 0.2F);
        alpha.setDuration(0);
        alpha.setFillAfter(true);
        cns.startAnimation(alpha);
        txt_course.setEnabled(false);
        txt_courseTime.setEnabled(false);
        txt_courseFee.setEnabled(false);
        btn_addCourse.setEnabled(false);
    }

    public void invisibleLoader()
    {
        cns.startAnimation(new AlphaAnimation(0, 0));
        relLay.setVisibility(View.INVISIBLE);
        txt_course.setEnabled(true);
        txt_courseTime.setEnabled(true);
        txt_courseFee.setEnabled(true);
        btn_addCourse.setEnabled(true);
    }
}