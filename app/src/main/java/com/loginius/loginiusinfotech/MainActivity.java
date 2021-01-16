package com.loginius.loginiusinfotech;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.KeyguardManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.hardware.fingerprint.FingerprintManager;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
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

import java.util.HashMap;
import java.util.Map;

import static com.loginius.loginiusinfotech.R.drawable.ic_email;
import static com.loginius.loginiusinfotech.R.drawable.ic_email_err;
import static com.loginius.loginiusinfotech.R.drawable.ic_lock;
import static com.loginius.loginiusinfotech.R.drawable.ic_lock_err;
import static com.loginius.loginiusinfotech.R.drawable.round_border_err;
public class MainActivity extends AppCompatActivity {

    EditText txt_email, txt_pwd;
    Button btn_clk;
    RelativeLayout relLay;
    LazyLoader lazyLoader;
    ConstraintLayout cns;
    TextView msg;

    private FingerprintManager fingerprintManager;
    private KeyguardManager keyguardManager;
    private static final String url = "https://maharshiinstitute.com/loginius/getUser.php";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        cns = findViewById(R.id.main_cns);
        relLay = findViewById(R.id.main_rel);
        lazyLoader = findViewById(R.id.my_progress);
        txt_email = findViewById(R.id.login_email);
        txt_pwd = findViewById(R.id.login_pwd);
        btn_clk = findViewById(R.id.login_btn);
        relLay.setVisibility(View.INVISIBLE);
        msg = findViewById(R.id.txt_msg);

        SharedPreferences preferences=getSharedPreferences("Login",MODE_PRIVATE);
        String chk=preferences.getString("email","");
        String pwd=preferences.getString("password","");
        if(!chk.isEmpty() && !pwd.isEmpty())
        {
            msg.setVisibility(View.VISIBLE);
            BIOMATRIC_DATA();
        }
        /*BIO MATRIC START*/

        /*BIO MATRIC OVER*/
        //TXT CHANGE PWD
        txt_pwd.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                txt_pwd.setBackgroundResource(R.drawable.round_border);
                txt_pwd.setCompoundDrawablesWithIntrinsicBounds(ic_lock,
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
        txt_email.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                txt_email.setBackgroundResource(R.drawable.round_border);
                txt_email.setCompoundDrawablesWithIntrinsicBounds(ic_email,
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
        btn_clk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                relLay.setVisibility(View.VISIBLE);
                LazyLoader loader = new LazyLoader(MainActivity.this, 30, 20, ContextCompat.getColor(MainActivity.this, R.color.loader_selected),
                        ContextCompat.getColor(MainActivity.this, R.color.loader_selected),
                        ContextCompat.getColor(MainActivity.this, R.color.loader_selected));
                loader.setAnimDuration(500);
                loader.setFirstDelayDuration(100);
                loader.setSecondDelayDuration(200);
                loader.setInterpolator(new LinearInterpolator());

                lazyLoader.addView(loader);
                if (txt_email.getText().toString().isEmpty() || txt_pwd.getText().toString().isEmpty()) {
                    relLay.setVisibility(View.INVISIBLE);
                    if (txt_email.getText().toString().isEmpty() && txt_pwd.getText().toString().isEmpty()) {

                        txt_email.setBackgroundResource(round_border_err);
                        txt_email.setCompoundDrawablesWithIntrinsicBounds(ic_email_err,
                                0,
                                0,
                                0);
                        txt_email.setError("Enter Your Username Or Email.");
                        txt_pwd.setBackgroundResource(round_border_err);
                        txt_pwd.setCompoundDrawablesWithIntrinsicBounds(ic_lock_err,
                                0,
                                0,
                                0);
                        txt_pwd.setError("Enter Your Password.");
                    } else if (txt_email.getText().toString().isEmpty()) {
//                        Toast.makeText(MainActivity.this, "Please Enter Email...", Toast.LENGTH_SHORT).show();
                        txt_email.setCompoundDrawablesWithIntrinsicBounds(ic_email_err,
                                0,
                                0,
                                0);
                        txt_email.setBackgroundResource(round_border_err);
                        txt_email.setError("Enter Your Username Or Email.");
                    } else if (txt_pwd.getText().toString().isEmpty()) {
//                            Toast.makeText(MainActivity.this, "Please Enter Password...", Toast.LENGTH_SHORT).show()
                        txt_pwd.setBackgroundResource(round_border_err);
                        txt_pwd.setCompoundDrawablesWithIntrinsicBounds(ic_lock_err,
                                0,
                                0,
                                0);
                        txt_email.setBackgroundResource(round_border_err);
                        txt_pwd.setError("Enter Your Password.");
                    }

                } else {
                    AlphaAnimation alpha = new AlphaAnimation(0.2F, 0.2F);
                    alpha.setDuration(0);
                    alpha.setFillAfter(true);
                    cns.startAnimation(alpha);
                    txt_email.setEnabled(false);
                    txt_pwd.setEnabled(false);
                    btn_clk.setEnabled(false);
//                    Toast.makeText(MainActivity.this, "DONE", Toast.LENGTH_SHORT).show();
                    StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {

                            if (response.contains("1")) {
//                                Toast.makeText(MainActivity.this, "Done", Toast.LENGTH_SHORT).show();
                                SharedPreferences preferences =getApplicationContext().getSharedPreferences("Login", MODE_PRIVATE);
                                SharedPreferences.Editor editor = preferences.edit();
                                editor.putString("email", txt_email.getText().toString());
                                editor.putString("password", txt_pwd.getText().toString());
                                editor.commit();
                                finish();
                                startActivity(new Intent(getApplicationContext(), DetailsActivity.class));
                            }
                            else {
                                relLay.setVisibility(View.INVISIBLE);
                                cns.startAnimation(new AlphaAnimation(0, 0));
                                txt_email.setEnabled(true);
                                txt_pwd.setEnabled(true);
                                btn_clk.setEnabled(true);
                                Toast.makeText(MainActivity.this, "Invalid Username And Password...",Toast.LENGTH_SHORT).show();
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
                            params.put("username",txt_email.getText().toString() );
                            params.put("password", txt_pwd.getText().toString());
                            return params;
                        }
                    };
                    Volley.newRequestQueue(MainActivity.this).add(request);
                }
            }
        });

    }

    private void BIOMATRIC_DATA() {
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.M)
        {

            fingerprintManager= (FingerprintManager) getSystemService(FINGERPRINT_SERVICE);
            keyguardManager= (KeyguardManager) getSystemService(KEYGUARD_SERVICE);
            if(!fingerprintManager.isHardwareDetected())
            {
               msg.setVisibility(View.INVISIBLE);

            }
            else if(ContextCompat.checkSelfPermission(this, Manifest.permission.USE_FINGERPRINT)!= PackageManager.PERMISSION_GRANTED){

                msg.setVisibility(View.INVISIBLE);

            }
            else if(!keyguardManager.isKeyguardSecure())
            {
                msg.setVisibility(View.VISIBLE);
                msg.setText("Add lock To Your Phone in Settings");
            }
            else if(!fingerprintManager.hasEnrolledFingerprints())
            {
                msg.setVisibility(View.VISIBLE);
                msg.setText("You should add atleast 1 Fingerprint to use this Feature");
            }
            else{
                FingerprintHandler handler=new FingerprintHandler(this);
                handler.startAuth(fingerprintManager,null);
            }
        }

    }
}