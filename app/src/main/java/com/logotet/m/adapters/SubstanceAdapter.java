package com.logotet.m.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.logotet.m.R;
import com.logotet.m.data.entities.Substance;
import com.logotet.m.utils.Utils;

import java.util.List;

public class SubstanceAdapter extends RecyclerView.Adapter<SubstanceAdapter.SubstanceHolder> {

    private List<Substance> substanceList;
    private SubstanceHolder.OnActionListener listener;

    public SubstanceAdapter(List<Substance> list, SubstanceHolder.OnActionListener listener) {
        substanceList = list;
        this.listener = listener;
    }

    public void updateData(List<Substance> substances) {
        substanceList = substances;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public SubstanceHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.substance_view, parent, false);
        return new SubstanceHolder(view, listener);
    }

    @Override
    public void onBindViewHolder(@NonNull SubstanceHolder holder, int position) {
        Substance substance;
        substance = substanceList.get(position);
        holder.txtSubstName.setText(substance.getName());
        holder.txtCategory.setText(substance.getCategory());
        holder.txtIntakePeriods.setText(Utils.getCategory(substance.getIntakeDays()));
        holder.setSubstance(substance);
    }

    @Override
    public int getItemCount() {
        return substanceList.size();
    }

    public static class SubstanceHolder extends RecyclerView.ViewHolder {

        private TextView txtSubstName;
        private TextView txtOpen;
        private TextView txtCategory;
        private TextView txtIntakePeriods;
        private ImageView imgEdit;
        private Substance substance;

        public SubstanceHolder(@NonNull final View itemView, OnActionListener listener) {
            super(itemView);
            txtSubstName = itemView.findViewById(R.id.txtName);
            txtCategory = itemView.findViewById(R.id.txt_category);
            txtIntakePeriods = itemView.findViewById(R.id.txt_intake_period);
            txtOpen = itemView.findViewById(R.id.txt_details);
            imgEdit = itemView.findViewById(R.id.img_delete);
            itemView.setOnClickListener(v -> toggleVisibility());
            txtOpen.setOnClickListener(v -> listener.onOpenClicked(substance));
            imgEdit.setOnClickListener(v -> listener.onDeleteClicked(substance));
        }

        private void toggleVisibility() {
            if (txtOpen.getVisibility() == View.GONE && imgEdit.getVisibility() == View.GONE) {
                txtOpen.setVisibility(View.VISIBLE);
                imgEdit.setVisibility(View.VISIBLE);
            } else {
                txtOpen.setVisibility(View.GONE);
                imgEdit.setVisibility(View.GONE);
            }
        }

        public void setSubstance(Substance substance) {
            this.substance = substance;
        }

        public interface OnActionListener {
            void onDeleteClicked(Substance substance);
            void onOpenClicked(Substance substance);
        }

    }
}
