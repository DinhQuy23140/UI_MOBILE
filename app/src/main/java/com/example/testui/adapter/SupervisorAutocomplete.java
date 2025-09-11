package com.example.testui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Filter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.testui.R;
import com.example.testui.model.Supervisor;

import java.util.ArrayList;
import java.util.List;

public class SupervisorAutocomplete extends ArrayAdapter<Supervisor> {
    private final List<Supervisor> allItems; // danh sách gốc
    private final List<Supervisor> items;    // danh sách hiển thị

    public SupervisorAutocomplete(@NonNull Context context, @NonNull List<Supervisor> objects) {
        super(context, 0, new ArrayList<>(objects));
        this.allItems = new ArrayList<>(objects);
        this.items = new ArrayList<>(objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_gvhd, parent, false);
        }

        ImageView imgAvatar = convertView.findViewById(R.id.imgAvatar);
        TextView tvGiangVienName = convertView.findViewById(R.id.tvGiangVienName);
        TextView tvHocVi = convertView.findViewById(R.id.tvHocVi);
        TextView tvDonVi = convertView.findViewById(R.id.tvDonVi);

        Supervisor supervisor = getItem(position);
        if (supervisor != null) {
            tvGiangVienName.setText(supervisor.getTeacher().getUser().getFullname());
            tvHocVi.setText(supervisor.getTeacher().getDegree());
        }

        return convertView;
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Nullable
    @Override
    public Supervisor getItem(int position) {
        return items.get(position);
    }

    @NonNull
    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults results = new FilterResults();
                List<Supervisor> suggestions = new ArrayList<>();

                if (constraint == null || constraint.length() == 0) {
                    // hiển thị tất cả khi chưa nhập gì
                    suggestions.addAll(allItems);
                } else {
                    String searchStr = constraint.toString().toLowerCase().trim();
                    for (Supervisor sup : allItems) {
                        if (sup.getTeacher().getUser().getFullname().toLowerCase().contains(searchStr)) {
                            suggestions.add(sup);
                        }
                    }
                }

                results.values = suggestions;
                results.count = suggestions.size();
                return results;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                items.clear();
                if (results != null && results.count > 0) {
                    items.addAll((List<Supervisor>) results.values);
                }
                notifyDataSetChanged();
            }

            @Override
            public CharSequence convertResultToString(Object resultValue) {
                return ((Supervisor) resultValue).getTeacher().getUser().getFullname();
            }
        };
    }
}
