package com.loginius.loginiusinfotech;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PwdAdapterClass extends RecyclerView.Adapter<PwdAdapterClass.MyViewHolder> {
    List<PasswordModelClass> list;
    Context context;

    public PwdAdapterClass(List<PasswordModelClass> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.reset_pwd_file, parent, false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {
        holder.unm.setText(list.get(position).getUserNm());
        holder.reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String unm = list.get(position).getUserNm();

                updPwd(unm);
            }

            private void updPwd(final String unm) {
                StringRequest request = new StringRequest(Request.Method.POST, "https://maharshiinstitute.com/loginius/getUser.php", new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(context, "Password Reset of " + list.get(position).getUserNm(), Toast.LENGTH_SHORT).show();
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }) {
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> params = new HashMap<>();
                        params.put("username", unm);
                        params.put("password", "resetAdpt");
                        return params;
                    }
                };
                Volley.newRequestQueue(context).add(request);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView unm;
        Button reset;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            unm = itemView.findViewById(R.id.rest_user);

            reset = itemView.findViewById(R.id.btn_reset);
//            id=itemView.findViewById(R.id.)
        }
    }
}
