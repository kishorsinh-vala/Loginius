package com.loginius.loginiusinfotech;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ProjectAdapterClass extends RecyclerView.Adapter<ProjectAdapterClass.MyViewHolder> {
    List<ProjectModelClass> list;
    Context context;

    public ProjectAdapterClass(List<ProjectModelClass> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.project_display, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.pnm.setText(list.get(position).getPnm());
        holder.amt.setText(list.get(position).getAmount());
        holder.st.setText(list.get(position).getStart());
        holder.edt.setText(list.get(position).getDue());
        holder.dev.setText(list.get(position).getDev());
        holder.ref.setText(list.get(position).getRef());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView pnm,amt,st,edt,ref,dev;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            pnm=itemView.findViewById(R.id.prj_disp_nm);
            amt=itemView.findViewById(R.id.prj_disp_prc);
            st=itemView.findViewById(R.id.prj_disp_st);
            edt=itemView.findViewById(R.id.prj_disp_ed);
            ref=itemView.findViewById(R.id.prj_disp_ref);
            dev=itemView.findViewById(R.id.prj_disp_dev);
        }
    }
}
