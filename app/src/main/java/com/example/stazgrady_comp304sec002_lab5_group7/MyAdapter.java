package com.example.stazgrady_comp304sec002_lab5_group7;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    Context context;
    ArrayList<Patient> patients;

    public MyAdapter(Context context, ArrayList<Patient> patients) {
        this.context = context;
        this.patients = patients;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(context).inflate(R.layout.patient_info, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Patient patient = patients.get(position);
        holder.nameInfo.setText(patient.getName());
        holder.ageInfo.setText(String.valueOf(patient.getAge()));
        holder.diseaseInfo.setText(patient.getDisease());
        holder.billInfo.setText(String.valueOf(patient.getBill()));
    }

    @Override
    public int getItemCount() {
        return patients.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView nameInfo;
        TextView ageInfo;
        TextView diseaseInfo;
        TextView billInfo;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            nameInfo = itemView.findViewById(R.id.nameInfo);
            ageInfo = itemView.findViewById(R.id.ageInfo);
            diseaseInfo = itemView.findViewById(R.id.diseaseInfo);
            billInfo = itemView.findViewById(R.id.billInfo);
        }
    }
}
