package com.example.testui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.testui.R;
import com.example.testui.model.Supervisor;

import java.util.ArrayList;
import java.util.List;

public class SupervisorAutoAdapter extends ArrayAdapter<Supervisor> {
    private final List<Supervisor> originalList;  // danh sách gốc để filter

    public SupervisorAutoAdapter(@NonNull Context context, @NonNull List<Supervisor> list) {
        super(context, 0, new ArrayList<>(list)); // Adapter quản lý list hiển thị
        originalList = new ArrayList<>(list);
    }

    @NonNull
    @Override
    public Filter getFilter() {
        return supervisorFilter;
    }

    private final Filter supervisorFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<Supervisor> suggestions = new ArrayList<>();

            if (constraint == null || constraint.length() == 0) {
                suggestions.addAll(originalList);
            } else {
                String pattern = constraint.toString().toLowerCase().trim();
                for (Supervisor s : originalList) {
                    if (s.getTeacher() != null &&
                            s.getTeacher().getUser() != null &&
                            s.getTeacher().getUser().getFullname() != null &&
                            s.getTeacher().getUser().getFullname().toLowerCase().contains(pattern)) {
                        suggestions.add(s);
                    }
                }
            }

            FilterResults results = new FilterResults();
            results.values = suggestions;
            results.count = suggestions.size();
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            clear();
            if (results != null && results.values != null) {
                // Cập nhật dữ liệu hiển thị trực tiếp trong ArrayAdapter
                addAll((List<Supervisor>) results.values);
            } else {
                addAll(originalList);
            }
            notifyDataSetChanged();
        }

        @Override
        public CharSequence convertResultToString(Object resultValue) {
            if (resultValue instanceof Supervisor) {
                Supervisor s = (Supervisor) resultValue;
                if (s.getTeacher() != null && s.getTeacher().getUser() != null) {
                    return s.getTeacher().getUser().getFullname();
                }
            }
            return "";
        }
    };

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            view = LayoutInflater.from(getContext()).inflate(R.layout.item_complete_textview, parent, false);
        }

        Supervisor supervisor = getItem(position);

        TextView tvName = view.findViewById(R.id.tvGiangVienName);
        ImageView imgAvatar = view.findViewById(R.id.imgAvatar);

        if (supervisor != null && supervisor.getTeacher() != null && supervisor.getTeacher().getUser() != null) {
            tvName.setText(supervisor.getTeacher().getUser().getFullname());
        } else {
            tvName.setText("");
        }

        return view;
    }
}
