package com.nvtrung.luyendia12.model;

public class History {
    private int id;                 // id lịch sử (nếu có)
    private int categoryID;         // id chủ đề
    private String categoryName;    // tên chủ đề
    private int score;              // điểm đạt được
    private int totalQuestions;     // tổng số câu hỏi
    private int correctAnswers;     // số câu trả lời đúng
    private String date;            // ngày làm bài, có thể là String hoặc Date tùy bạn

    public History(int id, int categoryID, String categoryName, int score,
                   int totalQuestions, int correctAnswers, String date) {
        this.id = id;
        this.categoryID = categoryID;
        this.categoryName = categoryName;
        this.score = score;
        this.totalQuestions = totalQuestions;
        this.correctAnswers = correctAnswers;
        this.date = date;
    }

    public History() {

    }

    public int getId() {
        return id;
    }

    public int getCategoryID() {
        return categoryID;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public int getScore() {
        return score;
    }

    public int getTotalQuestions() {
        return totalQuestions;
    }

    public int getCorrectAnswers() {
        return correctAnswers;
    }

    public String getDate() {
        return date;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public void setTotalQuestions(int totalQuestions) {
        this.totalQuestions = totalQuestions;
    }

    public void setCorrectAnswers(int correctAnswers) {
        this.correctAnswers = correctAnswers;
    }

    public void setCategoryID(int categoryId) {
        this.categoryID = categoryId;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }
}
