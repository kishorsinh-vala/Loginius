package com.loginius.loginiusinfotech;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.hardware.fingerprint.FingerprintManager;
import android.os.Build;
import android.os.CancellationSignal;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.core.content.ContextCompat;

@RequiresApi(api = Build.VERSION_CODES.M)
public class FingerprintHandler extends FingerprintManager .AuthenticationCallback{
    private  Context context;
    public FingerprintHandler(Context context) {
        this.context=context;
    }

    public void startAuth(FingerprintManager fingerprintManager,FingerprintManager.CryptoObject cryptoObject)
    {
        CancellationSignal cancellationSignal=new CancellationSignal();
        fingerprintManager.authenticate(cryptoObject,cancellationSignal,0,this,null);
    }

    @Override
    public void onAuthenticationError(int errorCode, CharSequence errString) {
        super.onAuthenticationError(errorCode, errString);
        this.update("There was an Auth Error"+errString,false);
    }

    @Override
    public void onAuthenticationFailed() {
        super.onAuthenticationFailed();
        this.update("Auth Failed",false);

    }

    @Override
    public void onAuthenticationHelp(int helpCode, CharSequence helpString) {
        super.onAuthenticationHelp(helpCode, helpString);
        this.update("Error: "+helpString,false);
    }

    @Override
    public void onAuthenticationSucceeded(FingerprintManager.AuthenticationResult result) {
        super.onAuthenticationSucceeded(result);
        this.update("You can Now Access the APP",true);

    }

    private void update(String s, boolean b) {
        TextView textView1 =((Activity) context).findViewById(R.id.txt_msg);
        textView1.setText(s);
        if(b==false)
        {
            textView1.setTextColor(ContextCompat.getColor(context,R.color.colorError));
        }
        else
        {
            textView1.setVisibility(View.VISIBLE);
            textView1.setTextColor(ContextCompat.getColor(context,R.color.colorPrimary));
            ((Activity) context).finish();
            context.startActivity(new Intent(context,DetailsActivity.class));
        }
    }
}
