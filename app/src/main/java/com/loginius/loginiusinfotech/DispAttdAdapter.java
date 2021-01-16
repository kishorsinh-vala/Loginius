package com.loginius.loginiusinfotech;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class DispAttdAdapter extends RecyclerView.Adapter<DispAttdAdapter.ViewHolder> {
    Context context;
    List<DispAttdModel> list;

    public DispAttdAdapter(Context context, List<DispAttdModel> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(context);
        View view=inflater.inflate(R.layout.attendance_disp_modal_file,parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.nm.setText(list.get(position).getUnm());
        holder.rec.setText(list.get(position).getAttd());

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView nm,rec;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            nm=itemView.findViewById(R.id.txt_attd_disp_unm);
            rec=itemView.findViewById(R.id.txt_attd_disp_rec);
        }
    }
}
