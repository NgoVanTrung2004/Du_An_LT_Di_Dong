package com.nvtrung.luyendia12;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.nvtrung.luyendia12.model.Question;
import com.nvtrung.luyendia12.model.History; // ✅ Thêm import History

import java.text.SimpleDateFormat;            // ✅ Thêm để lấy thời gian
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Locale;

public class QuestionActivity extends AppCompatActivity {

    private TextView textViewQuestion;
    private TextView textViewScore;
    private TextView getTextViewQuestionCount;
    private TextView textViewCategory;
    private TextView textViewCountDown;

    private RadioGroup rbGroup;
    private RadioButton rb1, rb2, rb3, rb4;
    private Button buttonConfirmNext;

    private CountDownTimer countDownTimer;
    private ArrayList<Question> questionList;
    private long timeLeftInMillis;
    private int questionCounter;
    private int questionSize;

    private int Score;
    private boolean answered;
    private int checkedCount = 0;
    private int wrongCount = 0;

    private Question currentQuestion;

    private int categoryID; // ✅ Dùng để lưu lịch sử làm bài

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);

        anhxa(); // Ánh xạ view

        this.getOnBackPressedDispatcher().addCallback(this, new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                finishQuestion(); // Xử lý khi nhấn nút Back
            }
        });

        // Nhận dữ liệu chủ đề
        Intent intent = getIntent();
        categoryID = intent.getIntExtra("idcategories", 0); // ✅ Gán biến toàn cục
        String categoryName = intent.getStringExtra("categoriesname");

        textViewCategory.setText("Chủ đề : " + categoryName);

        Database database = new Database(this);
        questionList = database.getQuestions(categoryID);
        questionSize = questionList.size();

        Collections.shuffle(questionList);
        showNextQuestion();

        buttonConfirmNext.setOnClickListener(view -> {
            if (!answered) {
                if (rb1.isChecked() || rb2.isChecked() || rb3.isChecked() || rb4.isChecked()) {
                    checkAnswer();
                } else {
                    Toast.makeText(QuestionActivity.this, "Hãy chọn đáp án", Toast.LENGTH_SHORT).show();
                }
            } else {
                showNextQuestion();
            }
        });
    }

    private void showNextQuestion() {
        resetOptionsColor();
        rbGroup.clearCheck();

        if (questionCounter < questionSize) {
            currentQuestion = questionList.get(questionCounter);
            textViewQuestion.setText(currentQuestion.getQuestion());
            rb1.setText(currentQuestion.getOption1());
            rb2.setText(currentQuestion.getOption2());
            rb3.setText(currentQuestion.getOption3());
            rb4.setText(currentQuestion.getOption4());

            questionCounter++;
            getTextViewQuestionCount.setText("Câu hỏi : " + questionCounter + " / " + questionSize);
            answered = false;
            buttonConfirmNext.setText("Xác Nhận");

            timeLeftInMillis = 30000;
            startCountDown();
        } else {
            finishQuestion(); // ✅ Gọi khi hết câu
        }
    }

    private void startCountDown() {
        if (countDownTimer != null) countDownTimer.cancel();

        countDownTimer = new CountDownTimer(timeLeftInMillis, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timeLeftInMillis = millisUntilFinished;
                updateCountDownText();
            }

            @Override
            public void onFinish() {
                timeLeftInMillis = 0;
                updateCountDownText();
                checkAnswer();
            }
        }.start();
    }

    private void checkAnswer() {
        answered = true;
        RadioButton rbSelected = findViewById(rbGroup.getCheckedRadioButtonId());
        int answer = rbGroup.indexOfChild(rbSelected) + 1;

        if (answer == currentQuestion.getAnswer()) {
            Score += 10;
            checkedCount++;
            textViewScore.setText("Điểm : " + Score);
        } else {
            wrongCount++;
        }

        showSolution();
    }

    private void showResultDialog() {
        int totalCorrect = Score / 10;
        int totalWrong = questionSize - totalCorrect;

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Kết Quả");

        String message = "Số câu đúng: " + totalCorrect +
                "\nSố câu sai: " + totalWrong +
                "\nTổng điểm: " + Score;

        builder.setMessage(message);
        builder.setCancelable(false);
        builder.setPositiveButton("OK", (dialog, which) -> {
            Intent resultIntent = new Intent();
            resultIntent.putExtra("score", Score);
            setResult(RESULT_OK, resultIntent);
            finish();
        });

        builder.show();
    }

    private void showSolution() {
        rb1.setBackgroundResource(R.drawable.radio_unchecked);
        rb2.setBackgroundResource(R.drawable.radio_unchecked);
        rb3.setBackgroundResource(R.drawable.radio_unchecked);
        rb4.setBackgroundResource(R.drawable.radio_unchecked);

        if (rb1.isChecked() && currentQuestion.getAnswer() != 1) {
            rb1.setBackgroundResource(R.drawable.radio_wrong);
        }
        if (rb2.isChecked() && currentQuestion.getAnswer() != 2) {
            rb2.setBackgroundResource(R.drawable.radio_wrong);
        }
        if (rb3.isChecked() && currentQuestion.getAnswer() != 3) {
            rb3.setBackgroundResource(R.drawable.radio_wrong);
        }
        if (rb4.isChecked() && currentQuestion.getAnswer() != 4) {
            rb4.setBackgroundResource(R.drawable.radio_wrong);
        }

        switch (currentQuestion.getAnswer()) {
            case 1:
                rb1.setBackgroundResource(R.drawable.radio_checked);
                textViewQuestion.setText("Đáp án là A");
                break;
            case 2:
                rb2.setBackgroundResource(R.drawable.radio_checked);
                textViewQuestion.setText("Đáp án là B");
                break;
            case 3:
                rb3.setBackgroundResource(R.drawable.radio_checked);
                textViewQuestion.setText("Đáp án là C");
                break;
            case 4:
                rb4.setBackgroundResource(R.drawable.radio_checked);
                textViewQuestion.setText("Đáp án là D");
                break;
        }

        if (questionCounter < questionSize) {
            buttonConfirmNext.setText("Câu Tiếp");
        } else {
            buttonConfirmNext.setText("Hoàn Thành");
        }

        if (countDownTimer != null) {
            countDownTimer.cancel();
        }
    }

    private void resetOptionsColor() {
        rb1.setBackgroundResource(R.drawable.radio_unchecked);
        rb2.setBackgroundResource(R.drawable.radio_unchecked);
        rb3.setBackgroundResource(R.drawable.radio_unchecked);
        rb4.setBackgroundResource(R.drawable.radio_unchecked);

        rb1.setTextColor(Color.BLACK);
        rb2.setTextColor(Color.BLACK);
        rb3.setTextColor(Color.BLACK);
        rb4.setTextColor(Color.BLACK);
    }

    private void updateCountDownText() {
        int minutes = (int) ((timeLeftInMillis / 1000) / 60);
        int seconds = (int) ((timeLeftInMillis / 1000) % 60);

        String timeFormatted = String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds);
        textViewCountDown.setText(timeFormatted);

        if (timeLeftInMillis < 10000) {
            textViewCountDown.setTextColor(Color.RED);
        } else {
            textViewCountDown.setTextColor(Color.WHITE);
        }
    }

    private void finishQuestion() {
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }

        // 🔽 Lưu lịch sử làm bài vào CSDL
        int totalQuestions = questionSize;
        int correctAnswers = checkedCount;
        String currentDate = new SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault()).format(new Date());

        History history = new History();
        history.setScore(Score);
        history.setTotalQuestions(totalQuestions);
        history.setCorrectAnswers(correctAnswers);
        history.setDate(currentDate);
        history.setCategoryID(categoryID);


        Database dbHelper = new Database(this);
        dbHelper.insertHistory(history);
        // 🔼 Kết thúc lưu lịch sử làm bài

        showResultDialog(); // Hiển thị kết quả
    }

    private void anhxa() {
        textViewQuestion = findViewById(R.id.text_view_question);
        textViewScore = findViewById(R.id.text_view_score);
        getTextViewQuestionCount = findViewById(R.id.text_view_question_count);
        textViewCategory = findViewById(R.id.text_view_category);
        textViewCountDown = findViewById(R.id.text_view_countdown);

        rbGroup = findViewById(R.id.radio_group);
        rb1 = findViewById(R.id.radio_button1);
        rb2 = findViewById(R.id.radio_button2);
        rb3 = findViewById(R.id.radio_button3);
        rb4 = findViewById(R.id.radio_button4);

        buttonConfirmNext = findViewById(R.id.btn_confirm_next);
    }
}
