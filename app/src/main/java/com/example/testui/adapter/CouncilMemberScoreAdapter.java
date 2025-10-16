package com.example.testui.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.testui.R;
import com.example.testui.interfaces.OnClickItem;
import com.example.testui.model.CouncilProjectDefence;
import com.example.testui.model.CouncilsMember;
import com.example.testui.model.Status;
import com.example.testui.untilities.formatter.CouncilMemberFormatter;
import com.example.testui.untilities.formatter.DateFormatter;

import java.util.List;

public class CouncilMemberScoreAdapter extends RecyclerView.Adapter<CouncilMemberScoreAdapter.CouncilMemberScoreViewHolder> {

    Context context;
    List<CouncilProjectDefence> listCouncilProjectDefence;
    OnClickItem onClickItem;

    public CouncilMemberScoreAdapter(Context context, List<CouncilProjectDefence> listCouncilProjectDefence, OnClickItem onClickItem) {
        this.context = context;
        this.listCouncilProjectDefence = listCouncilProjectDefence;
        this.onClickItem = onClickItem;
    }

    public void updateData(List<CouncilProjectDefence> listCouncilProjectDefence) {
        this.listCouncilProjectDefence = listCouncilProjectDefence;
        notifyDataSetChanged();
    }

    public CouncilProjectDefence getData(int position) {
        return listCouncilProjectDefence.get(position);
    }

    @NonNull
    @Override
    public CouncilMemberScoreViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_council_member_score, parent, false);
        return new CouncilMemberScoreViewHolder(view);
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    @Override
    public void onBindViewHolder(@NonNull CouncilMemberScoreViewHolder holder, int position) {
        CouncilProjectDefence councilProjectDefence = listCouncilProjectDefence.get(position);
        CouncilsMember councilsMember = councilProjectDefence.getCouncil_member();
        String name = councilsMember.getSupervisor().getTeacher().getUser().getFullname();
        String role = councilsMember.getRole();
        String degree = councilsMember.getSupervisor().getTeacher().getDegree();
        String score = councilProjectDefence.getScore();
        String comment = councilProjectDefence.getComments();
        String time = councilProjectDefence.getUpdated_at();
        holder.tvName.setText(name);

        Status statusRole = CouncilMemberFormatter.formatRole(role);
        holder.tvRole.setText(statusRole.getStrStatus());
        holder.tvRole.setBackground(context.getDrawable(statusRole.getBackgroundColor()));

        Status statusDegree = CouncilMemberFormatter.formatDegree(degree);
        holder.tvDegree.setText(statusDegree.getStrStatus());
        holder.tvDegree.setBackground(context.getDrawable(statusDegree.getBackgroundColor()));

        Status statusScore = CouncilMemberFormatter.formatScore(score);
        holder.tvScore.setText(score);

        holder.tvComment.setText(comment);
        holder.tvStatus.setText(statusScore.getStrStatus());
        holder.tvStatus.setBackground(context.getDrawable(statusScore.getBackgroundColor()));
        holder.tvTime.setText(DateFormatter.formatDate(time));
    }

    @Override
    public int getItemCount() {
        return listCouncilProjectDefence.size();
    }

    public class CouncilMemberScoreViewHolder extends RecyclerView.ViewHolder {
        TextView tvName, tvRole, tvDegree, tvScore, tvComment, tvStatus, tvTime;
        public CouncilMemberScoreViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvThanhVienName);
            tvRole = itemView.findViewById(R.id.tvVaiTro);
            tvDegree = itemView.findViewById(R.id.tvHocVi);
            tvScore = itemView.findViewById(R.id.tvDiemSo);
            tvComment = itemView.findViewById(R.id.tvNhanXet);
            tvStatus = itemView.findViewById(R.id.tvTrangThai);
            tvTime = itemView.findViewById(R.id.tvThoiGian);
        }
    }
}
