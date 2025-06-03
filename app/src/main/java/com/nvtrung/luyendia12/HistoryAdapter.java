package com.nvtrung.luyendia12;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.nvtrung.luyendia12.model.History;

import java.util.List;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.ViewHolder> {
    private final List<History> historyList;

    public HistoryAdapter(List<History> historyList) {
        this.historyList = historyList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Sử dụng layout hệ thống 2 dòng text
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_history, parent, false);
        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        History h = historyList.get(position);

        holder.textViewDate.setText("Ngày: " + h.getDate());
        holder.textViewCategory.setText("Chủ đề: " + h.getCategoryName());
        holder.textViewScore.setText("Điểm: " + h.getScore() + "% - Đúng: " +
                h.getCorrectAnswers() + "/" + h.getTotalQuestions());
    }



    @Override
    public int getItemCount() {
        return historyList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView textViewDate, textViewCategory, textViewScore;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewDate = itemView.findViewById(R.id.textViewDate);
            textViewCategory = itemView.findViewById(R.id.textViewCategory);
            textViewScore = itemView.findViewById(R.id.textViewScore);
        }
    }

}
