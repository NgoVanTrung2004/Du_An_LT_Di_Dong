package com.nvtrung.luyendia12;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import com.nvtrung.luyendia12.model.Category;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private TextView textViewHighScore;
    private Spinner spinnerCategory;
    private Button buttonStartQuestion;
    private Button btnHistory;  // Thêm khai báo nút History
    private int highscore;

    private ActivityResultLauncher<Intent> questionActivityLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        AnhXa();
        loadCategories();
        loadHighScore();

        // Đăng ký launcher nhận kết quả từ QuestionActivity
        questionActivityLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK) {
                        Intent data = result.getData();
                        if (data != null) {
                            int score = data.getIntExtra("score", 0);
                            if (score > highscore) {
                                updateHighScore(score);
                            }
                        }
                    }
                }
        );

        buttonStartQuestion.setOnClickListener(view -> startQuestion());

        // Thêm xử lý nút lịch sử làm bài trong onCreate()
        btnHistory.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, HistoryActivity.class);
            startActivity(intent);
        });
    }

    private void startQuestion() {
        Category category = (Category) spinnerCategory.getSelectedItem();
        int categoryID = category.getId();
        String categoryName = category.getName();

        Intent intent = new Intent(MainActivity.this, QuestionActivity.class);
        intent.putExtra("idcategories", categoryID);
        intent.putExtra("categoriesname", categoryName);

        // Mở QuestionActivity với launcher thay vì startActivityForResult
        questionActivityLauncher.launch(intent);
    }

    private void AnhXa() {
        textViewHighScore = findViewById(R.id.textView_high_score);
        buttonStartQuestion = findViewById(R.id.button_start_question);
        spinnerCategory = findViewById(R.id.spinner_category);
        btnHistory = findViewById(R.id.btnHistory);  // Ánh xạ nút History
    }

    private void loadCategories() {
        Database database = new Database(this);
        List<Category> categories = database.getDataCategories();
        ArrayAdapter<Category> categoryArrayAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, categories);
        categoryArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCategory.setAdapter(categoryArrayAdapter);
    }

    private void loadHighScore() {
        SharedPreferences preferences = getSharedPreferences("share", MODE_PRIVATE);
        highscore = preferences.getInt("highscore", 0);
        textViewHighScore.setText("Điểm Cao: " + highscore);
    }

    private void updateHighScore(int score) {
        highscore = score;
        textViewHighScore.setText("Điểm Cao: " + highscore);
        SharedPreferences preferences = getSharedPreferences("share", MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt("highscore", highscore);
        editor.apply();
    }
}
