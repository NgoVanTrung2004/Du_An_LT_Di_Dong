package com.nvtrung.luyendia12;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.nvtrung.luyendia12.Database;
import com.nvtrung.luyendia12.HistoryAdapter;
import com.nvtrung.luyendia12.R;
import com.nvtrung.luyendia12.model.History;

import java.util.List;

public class HistoryActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    HistoryAdapter adapter;
    List<History> historyList;
    Database db;
    Button buttonBackToMain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        // Ánh xạ các view
        recyclerView = findViewById(R.id.recyclerViewHistory);
        buttonBackToMain = findViewById(R.id.buttonBackToMain);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        db = new Database(this);
        historyList = db.getHistoryList();

        adapter = new HistoryAdapter(historyList);
        recyclerView.setAdapter(adapter);

        // Xử lý sự kiện nút "Quay Lại"
        buttonBackToMain.setOnClickListener(v -> {
            // Quay lại MainActivity
            Intent intent = new Intent(HistoryActivity.this, MainActivity.class);
            // Nếu bạn muốn xóa HistoryActivity khỏi stack, dùng finish()
            startActivity(intent);
            finish();
        });
    }
}
