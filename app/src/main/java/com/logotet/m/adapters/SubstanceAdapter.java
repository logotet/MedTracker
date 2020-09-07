package com.logotet.m.adapters;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.logotet.m.R;
import com.logotet.m.SubstanceDetailsFragment;
import com.logotet.m.models.Substance;

import java.util.List;

public class SubstanceAdapter extends RecyclerView.Adapter<SubstanceAdapter.SubstanceHolder> {

    List<Substance> substanceList;

    public SubstanceAdapter(List<Substance> list) {
        substanceList = list;
    }


    @NonNull
    @Override
    public SubstanceHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.substance_view, parent, false);
        return new SubstanceHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SubstanceHolder holder, int position) {
        Substance substance;
        substance =  substanceList.get(position);
        holder.txtSubstName.setText(substance.getName());
    }

    @Override
    public int getItemCount() {
        return substanceList.size();
    }

    public class SubstanceHolder extends RecyclerView.ViewHolder {

        TextView txtSubstName;

        public SubstanceHolder(@NonNull final View itemView) {
            super(itemView);
            txtSubstName = itemView.findViewById(R.id.txtName);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(itemView.getContext(), SubstanceDetailsFragment.class);
                    }
            });

        }
    }
}
