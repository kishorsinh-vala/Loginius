package com.loginius.loginiusinfotech;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class AdapterClass extends RecyclerView.Adapter<AdapterClass.MyViewHolder> {
    List<ModalClass> list;
    Context context;

    public AdapterClass(List<ModalClass> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(context);
        View view=inflater.inflate(R.layout.layout_modal_file,parent,false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.nm.setText(list.get(position).getUesrname());
        holder.pwd.setText(list.get(position).getPassword());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView nm,pwd;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            nm=itemView.findViewById(R.id.lin_lay_unm);
            pwd=itemView.findViewById(R.id.lin_lay_pwd);
        }
    }
}
