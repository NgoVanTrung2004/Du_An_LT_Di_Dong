package com.nvtrung.luyendia12;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.nvtrung.luyendia12.model.Category;
import com.nvtrung.luyendia12.model.History;
import com.nvtrung.luyendia12.model.Question;

import java.util.ArrayList;
import java.util.List;

public class Database extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "Question.db";
    private static final int VERSION = 1;
    private SQLiteDatabase db;

    public Database(@Nullable Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }

    // Bật hỗ trợ khóa ngoại
    @Override
    public void onConfigure(SQLiteDatabase db) {
        super.onConfigure(db);
        db.setForeignKeyConstraintsEnabled(true);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        this.db = sqLiteDatabase;

        final String CATEGORIES_TABLE = "CREATE TABLE " +
                Table.CategoriesTable.TABLE_NAME + " ( " +
                Table.CategoriesTable._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                Table.CategoriesTable.COLUMN_NAME + " TEXT " +
                ")";

        final String QUESTIONS_TABLE = "CREATE TABLE " +
                Table.QuestionsTable.TABLE_NAME + " ( " +
                Table.QuestionsTable._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                Table.QuestionsTable.COLUMN_QUESTION + " TEXT, " +
                Table.QuestionsTable.COLUMN_OPTION1 + " TEXT, " +
                Table.QuestionsTable.COLUMN_OPTION2 + " TEXT, " +
                Table.QuestionsTable.COLUMN_OPTION3 + " TEXT, " +
                Table.QuestionsTable.COLUMN_OPTION4 + " TEXT, " +
                Table.QuestionsTable.COLUMN_ANSWER + " INTEGER, " +
                Table.QuestionsTable.COLUMN_CATEGORY_ID + " INTEGER, " +
                "FOREIGN KEY(" + Table.QuestionsTable.COLUMN_CATEGORY_ID + ") REFERENCES " +
                Table.CategoriesTable.TABLE_NAME + "(" + Table.CategoriesTable._ID + ") ON DELETE CASCADE" +
                ")";

        final String HISTORY_TABLE = "CREATE TABLE History (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "score INTEGER, " +
                "total_questions INTEGER, " +
                "correct_answers INTEGER, " +
                "category_id INTEGER, " +
                "date TEXT, " +
                "FOREIGN KEY(category_id) REFERENCES " + Table.CategoriesTable.TABLE_NAME + "(" + Table.CategoriesTable._ID + ") ON DELETE CASCADE)";

        db.execSQL(CATEGORIES_TABLE);
        db.execSQL(QUESTIONS_TABLE);
        db.execSQL(HISTORY_TABLE);

        // Thêm dữ liệu
        CreatCategories();
        CreateQuestions();
    }


    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        db = sqLiteDatabase;
        db.execSQL("DROP TABLE IF EXISTS " + Table.QuestionsTable.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + Table.CategoriesTable.TABLE_NAME);
        onCreate(db);
    }

    // Thêm chuyên mục
    private void insertCategories(Category category) {
        ContentValues values = new ContentValues();
        values.put(Table.CategoriesTable.COLUMN_NAME, category.getName());
        db.insert(Table.CategoriesTable.TABLE_NAME, null, values);
    }

    private void CreatCategories() {
        insertCategories(new Category("Địa Lí Tự Nhiên"));           // id = 1
        insertCategories(new Category("Địa Lí Dân Cư"));              // id = 2
        insertCategories(new Category("Địa Lí Kinh Tế"));             // id = 3
        insertCategories(new Category("Địa Lí Các Vùng Kinh Tế"));   // id = 4
        insertCategories(new Category("Kiểm Tra Tổng Hợp"));         // id = 5
    }

    // Thêm câu hỏi
    private void insertQuestion(Question question) {
        ContentValues values = new ContentValues();
        values.put(Table.QuestionsTable.COLUMN_QUESTION, question.getQuestion());
        values.put(Table.QuestionsTable.COLUMN_OPTION1, question.getOption1());
        values.put(Table.QuestionsTable.COLUMN_OPTION2, question.getOption2());
        values.put(Table.QuestionsTable.COLUMN_OPTION3, question.getOption3());
        values.put(Table.QuestionsTable.COLUMN_OPTION4, question.getOption4());
        values.put(Table.QuestionsTable.COLUMN_ANSWER, question.getAnswer());
        values.put(Table.QuestionsTable.COLUMN_CATEGORY_ID, question.getCategoryID());
        db.insert(Table.QuestionsTable.TABLE_NAME, null, values);
    }

    // Danh sách câu hỏi
    private void CreateQuestions() {

        // Chủ đề 1: Địa Lí Tự Nhiên (categoryID = 1)
        insertQuestion(new Question("Việt Nam nằm trong múi giờ số bao nhiêu?",
                "A. Múi giờ số 5",
                "B. Múi giờ số 6",
                "C. Múi giờ số 7",
                "D. Múi giờ số 8", 3, 1));
        insertQuestion(new Question("Đặc điểm nổi bật của khí hậu nhiệt đới ẩm gió mùa Việt Nam là:",
                "A. Lạnh quanh năm",
                "B. Khô hạn quanh năm",
                "C. Có một mùa mưa và một mùa khô rõ rệt",
                "D. Có sự phân hóa đa dạng", 3, 1));
        insertQuestion(new Question("Việt Nam có các mùa khí hậu chính là:",
                "A. Xuân, Hạ, Thu, Đông",
                "B. Mùa mưa và mùa khô",
                "C. Mùa lạnh và mùa nóng",
                "D. Mùa khô và mùa lạnh", 2, 1));
        insertQuestion(new Question("Địa hình chủ yếu của Việt Nam là:",
                "A. Đồng bằng",
                "B. Núi cao trên 3000m",
                "C. Đồi núi thấp",
                "D. Cao nguyên bazan", 3, 1));
        insertQuestion(new Question("Việt Nam có bao nhiêu vùng khí hậu chính?",
                "A. 2",
                "B. 3",
                "C. 4",
                "D. 5", 2, 1));
        insertQuestion(new Question("Dòng sông lớn nhất Việt Nam là:",
                "A. Sông Hồng",
                "B. Sông Mê Kông",
                "C. Sông Đồng Nai",
                "D. Sông Cửu Long", 2, 1));
        insertQuestion(new Question("Rừng ngập mặn tập trung chủ yếu ở vùng nào?",
                "A. Vùng Tây Bắc",
                "B. Vùng đồng bằng sông Cửu Long",
                "C. Vùng Tây Nguyên",
                "D. Vùng Đông Bắc", 2, 1));
        insertQuestion(new Question("Tài nguyên khoáng sản chủ yếu ở Việt Nam là:",
                "A. Than, dầu khí, quặng sắt",
                "B. Vàng, bạc, kim cương",
                "C. Đá quý, thủy ngân",
                "D. Đồng, thiếc, kẽm", 1, 1));
        insertQuestion(new Question("Nhiệt độ trung bình năm ở Việt Nam khoảng:",
                "A. 15-20 độ C",
                "B. 21-27 độ C",
                "C. 28-33 độ C",
                "D. 34-40 độ C", 2, 1));
        insertQuestion(new Question("Các hiện tượng thời tiết cực đoan thường gặp ở Việt Nam là:",
                "A. Bão, lũ lụt",
                "B. Hạn hán, sương muối",
                "C. Tuyết rơi, băng giá",
                "D. Sấm sét, tuyết rơi", 1, 1));

        // Chủ đề 2: Địa Lí Dân Cư (categoryID = 2)
        insertQuestion(new Question("Dân số Việt Nam hiện nay thuộc nhóm:",
                "A. Dân số già",
                "B. Dân số trẻ",
                "C. Dân số trung bình",
                "D. Dân số siêu già", 2, 2));
        insertQuestion(new Question("Mật độ dân số trung bình của Việt Nam cao nhất tập trung ở:",
                "A. Vùng núi phía Bắc",
                "B. Tây Nguyên",
                "C. Đồng bằng sông Hồng",
                "D. Duyên hải Nam Trung Bộ", 3, 2));
        insertQuestion(new Question("Tỉ lệ dân thành thị ở Việt Nam hiện nay khoảng:",
                "A. 30%",
                "B. 40%",
                "C. 50%",
                "D. 60%", 2, 2));
        insertQuestion(new Question("Ngôn ngữ chính thức và phổ biến nhất ở Việt Nam là:",
                "A. Tiếng Anh",
                "B. Tiếng Pháp",
                "C. Tiếng Việt",
                "D. Tiếng Hoa", 3, 2));
        insertQuestion(new Question("Tỉ lệ tăng dân số tự nhiên hiện nay ở Việt Nam là:",
                "A. 0,5%",
                "B. 1%",
                "C. 1,5%",
                "D. 2%", 2, 2));
        insertQuestion(new Question("Thành phần dân tộc chiếm đa số ở Việt Nam là:",
                "A. Kinh",
                "B. Tày",
                "C. Thái",
                "D. H'Mông", 1, 2));
        insertQuestion(new Question("Nguyên nhân chính dẫn đến di cư từ nông thôn ra thành thị là:",
                "A. Tìm việc làm",
                "B. Học tập",
                "C. Đất đai màu mỡ",
                "D. Môi trường sống tốt", 1, 2));
        insertQuestion(new Question("Tỉ lệ biết chữ của dân số Việt Nam hiện nay khoảng:",
                "A. 70%",
                "B. 80%",
                "C. 90%",
                "D. 95%", 4, 2));
        insertQuestion(new Question("Mức tuổi trung bình của dân số Việt Nam hiện nay là:",
                "A. 25 tuổi",
                "B. 30 tuổi",
                "C. 35 tuổi",
                "D. 40 tuổi", 2, 2));
        insertQuestion(new Question("Chính sách dân số Việt Nam hiện nay tập trung vào:",
                "A. Giảm sinh",
                "B. Cân bằng giới tính",
                "C. Tăng dân số",
                "D. Cả A và B", 4, 2));

        // Chủ đề 3: Địa Lí Kinh Tế (categoryID = 3)
        insertQuestion(new Question("Ngành công nghiệp trọng điểm của Việt Nam là:",
                "A. May mặc",
                "B. Khai thác gỗ",
                "C. Công nghiệp chế biến thực phẩm",
                "D. Công nghiệp năng lượng", 4, 3));
        insertQuestion(new Question("Cây công nghiệp lâu năm được trồng nhiều ở vùng nào sau đây?",
                "A. Đồng bằng sông Cửu Long",
                "B. Tây Nguyên",
                "C. Đồng bằng sông Hồng",
                "D. Bắc Trung Bộ", 2, 3));
        insertQuestion(new Question("Lĩnh vực kinh tế nào chiếm tỉ trọng lớn nhất trong GDP của Việt Nam?",
                "A. Nông nghiệp",
                "B. Công nghiệp - xây dựng",
                "C. Dịch vụ",
                "D. Xuất khẩu", 3, 3));
        insertQuestion(new Question("Trung tâm kinh tế lớn nhất vùng Đông Nam Bộ là:",
                "A. Cần Thơ",
                "B. TP. Hồ Chí Minh",
                "C. Vũng Tàu",
                "D. Biên Hòa", 2, 3));
        insertQuestion(new Question("Việt Nam có thế mạnh xuất khẩu mặt hàng nào?",
                "A. Dầu khí",
                "B. Gạo",
                "C. Dệt may",
                "D. Cả B và C", 4, 3));
        insertQuestion(new Question("Ngành dịch vụ phát triển mạnh ở Việt Nam là:",
                "A. Du lịch",
                "B. Vận tải",
                "C. Bưu chính viễn thông",
                "D. Tất cả các ngành trên", 4, 3));
        insertQuestion(new Question("Hạn chế lớn nhất của ngành nông nghiệp Việt Nam hiện nay là:",
                "A. Thiếu đất sản xuất",
                "B. Thiếu vốn",
                "C. Thiếu lao động",
                "D. Thiếu cơ sở hạ tầng", 4, 3));
        insertQuestion(new Question("Nguồn vốn đầu tư nước ngoài (FDI) chủ yếu tập trung vào lĩnh vực:",
                "A. Nông nghiệp",
                "B. Công nghiệp chế biến",
                "C. Dịch vụ",
                "D. Giao thông vận tải", 2, 3));
        insertQuestion(new Question("Đặc điểm nổi bật của ngành công nghiệp năng lượng ở Việt Nam là:",
                "A. Phát triển năng lượng tái tạo",
                "B. Phát triển nhiệt điện than",
                "C. Đầu tư mạnh vào thủy điện",
                "D. Cả B và C", 4, 3));
        insertQuestion(new Question("Việt Nam là nước xuất khẩu lớn trên thế giới về mặt hàng:",
                "A. Gạo",
                "B. Thuỷ sản",
                "C. Dệt may",
                "D. Tất cả các mặt hàng trên", 4, 3));

        // Chủ đề 4: Địa Lí Các Vùng Kinh Tế (categoryID = 4)
        insertQuestion(new Question("Việt Nam có bao nhiêu vùng kinh tế trọng điểm?",
                "A. 2",
                "B. 3",
                "C. 4",
                "D. 5", 4, 4));
        insertQuestion(new Question("Trung tâm kinh tế lớn nhất vùng Đồng bằng sông Hồng là:",
                "A. Hà Nội",
                "B. Hải Phòng",
                "C. Nam Định",
                "D. Thái Bình", 1, 4));
        insertQuestion(new Question("Vùng Tây Nguyên nổi bật về:",
                "A. Trồng cây công nghiệp lâu năm",
                "B. Phát triển công nghiệp nặng",
                "C. Du lịch biển",
                "D. Sản xuất nông sản ngắn ngày", 1, 4));
        insertQuestion(new Question("Vùng Đông Nam Bộ có thế mạnh về:",
                "A. Nông nghiệp",
                "B. Công nghiệp và dịch vụ",
                "C. Khai thác dầu khí",
                "D. Đánh bắt thủy sản", 2, 4));
        insertQuestion(new Question("Đồng bằng sông Cửu Long nổi bật về:",
                "A. Sản xuất lúa và thủy sản",
                "B. Công nghiệp chế biến",
                "C. Dịch vụ tài chính",
                "D. Du lịch", 1, 4));
        insertQuestion(new Question("Vùng Bắc Trung Bộ có đặc điểm địa hình chủ yếu là:",
                "A. Đồng bằng",
                "B. Núi cao",
                "C. Trung du và miền núi",
                "D. Cao nguyên", 3, 4));
        insertQuestion(new Question("Thành phố nào sau đây không thuộc vùng kinh tế trọng điểm phía Nam?",
                "A. TP. Hồ Chí Minh",
                "B. Biên Hòa",
                "C. Đà Nẵng",
                "D. Vũng Tàu", 3, 4));
        insertQuestion(new Question("Vùng duyên hải Nam Trung Bộ nổi bật về:",
                "A. Ngành công nghiệp năng lượng",
                "B. Du lịch và đánh bắt thủy sản",
                "C. Nông nghiệp trồng lúa",
                "D. Công nghiệp chế biến", 2, 4));
        insertQuestion(new Question("Vùng Trung du và miền núi Bắc Bộ nổi bật về:",
                "A. Trồng cây công nghiệp",
                "B. Khai thác than",
                "C. Chăn nuôi và trồng rừng",
                "D. Du lịch biển", 3, 4));
        insertQuestion(new Question("Vùng Đồng bằng sông Hồng có vai trò là:",
                "A. Trung tâm chính trị, kinh tế và văn hóa",
                "B. Vùng khai thác khoáng sản",
                "C. Vùng phát triển nông nghiệp",
                "D. Vùng sản xuất công nghiệp nặng", 1, 4));
        // Chủ đề 5: Địa Lí Quốc Tế (categoryID = 5)
        insertQuestion(new Question("Châu lục có diện tích lớn nhất thế giới là:",
                "A. Châu Âu",
                "B. Châu Á",
                "C. Châu Mỹ",
                "D. Châu Phi", 2, 5));
        insertQuestion(new Question("Quốc gia có dân số đông nhất thế giới là:",
                "A. Ấn Độ",
                "B. Hoa Kỳ",
                "C. Trung Quốc",
                "D. Indonesia", 3, 5));
        insertQuestion(new Question("Đường xích đạo đi qua châu lục nào sau đây?",
                "A. Châu Á",
                "B. Châu Âu",
                "C. Châu Phi",
                "D. Châu Mỹ", 3, 5));
        insertQuestion(new Question("Nước nào sau đây có lãnh thổ lớn nhất thế giới?",
                "A. Trung Quốc",
                "B. Canada",
                "C. Nga",
                "D. Hoa Kỳ", 3, 5));
        insertQuestion(new Question("Địa điểm có múi giờ GMT+0 là:",
                "A. London",
                "B. New York",
                "C. Tokyo",
                "D. Sydney", 1, 5));
        insertQuestion(new Question("Dòng sông dài nhất thế giới là:",
                "A. Sông Amazon",
                "B. Sông Nile",
                "C. Sông Mississippi",
                "D. Sông Mekong", 2, 5));
        insertQuestion(new Question("Châu lục nào có nhiều quốc gia nhất?",
                "A. Châu Á",
                "B. Châu Phi",
                "C. Châu Âu",
                "D. Châu Mỹ", 2, 5));
        insertQuestion(new Question("Thành phố nào là thủ đô của Nhật Bản?",
                "A. Osaka",
                "B. Tokyo",
                "C. Kyoto",
                "D. Hiroshima", 2, 5));
        insertQuestion(new Question("Biển lớn nhất thế giới là:",
                "A. Biển Đỏ",
                "B. Biển Đông",
                "C. Biển Caribbean",
                "D. Biển Coral", 3, 5));
        insertQuestion(new Question("Tổ chức quốc tế nào tập trung vào hòa bình và an ninh thế giới?",
                "A. Liên Hợp Quốc",
                "B. NATO",
                "C. ASEAN",
                "D. WTO", 1, 5));
    }


        // Lấy danh sách chuyên mục
    @SuppressLint("Range")
    public List<Category> getDataCategories() {
        List<Category> categoryList = new ArrayList<>();
        db = getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM " + Table.CategoriesTable.TABLE_NAME, null);
        if (c.moveToFirst()) {
            do {
                Category category = new Category();
                category.setId(c.getInt(c.getColumnIndex(Table.CategoriesTable._ID)));
                category.setName(c.getString(c.getColumnIndex(Table.CategoriesTable.COLUMN_NAME)));
                categoryList.add(category);
            } while (c.moveToNext());
        }
        c.close();
        return categoryList;
    }

    // Lấy danh sách câu hỏi theo chuyên mục
    @SuppressLint("Range")
    public ArrayList<Question> getQuestions(int categoryID) {
        ArrayList<Question> questionsArrayList = new ArrayList<>();
        db = getReadableDatabase();

        String selection = Table.QuestionsTable.COLUMN_CATEGORY_ID + " = ?";
        String[] selectionArgs = new String[]{String.valueOf(categoryID)};

        Cursor c = db.query(Table.QuestionsTable.TABLE_NAME, null, selection, selectionArgs, null, null, null);

        if (c.moveToFirst()) {
            do {
                Question question = new Question();
                question.setId(c.getInt(c.getColumnIndex(Table.QuestionsTable._ID)));
                question.setQuestion(c.getString(c.getColumnIndex(Table.QuestionsTable.COLUMN_QUESTION)));
                question.setOption1(c.getString(c.getColumnIndex(Table.QuestionsTable.COLUMN_OPTION1)));
                question.setOption2(c.getString(c.getColumnIndex(Table.QuestionsTable.COLUMN_OPTION2)));
                question.setOption3(c.getString(c.getColumnIndex(Table.QuestionsTable.COLUMN_OPTION3)));
                question.setOption4(c.getString(c.getColumnIndex(Table.QuestionsTable.COLUMN_OPTION4)));
                question.setAnswer(c.getInt(c.getColumnIndex(Table.QuestionsTable.COLUMN_ANSWER)));
                question.setCategoryID(c.getInt(c.getColumnIndex(Table.QuestionsTable.COLUMN_CATEGORY_ID)));

                questionsArrayList.add(question);
            } while (c.moveToNext());
        }
        c.close();
        return questionsArrayList;
    }


    // Hàm thêm 1 bản ghi vào bảng History
    public void insertHistory(History history) {
        db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("score", history.getScore());
        values.put("total_questions", history.getTotalQuestions());
        values.put("correct_answers", history.getCorrectAnswers());
        values.put("category_id", history.getCategoryID());
        values.put("category_name", history.getCategoryName());
        values.put("date", history.getDate()); // Ví dụ định dạng "2025-06-02 15:30"

        db.insert("History", null, values);

    }



    //Hàm lấy danh sách lịch sử làm bài
    @SuppressLint("Range")
    public List<History> getHistoryList() {
        List<History> historyList = new ArrayList<>();
        db = getReadableDatabase();

        Cursor c = db.rawQuery(
                "SELECT h.*, c.name AS category_name FROM History h " +
                        "JOIN Categories c ON h.category_id = c._id " +
                        "ORDER BY h.date DESC", null
        );

        if (c.moveToFirst()) {
            do {
                History history = new History();
                history.setId(c.getInt(c.getColumnIndex("id")));
                history.setScore(c.getInt(c.getColumnIndex("score")));
                history.setTotalQuestions(c.getInt(c.getColumnIndex("total_questions")));
                history.setCorrectAnswers(c.getInt(c.getColumnIndex("correct_answers")));
                history.setCategoryID(c.getInt(c.getColumnIndex("category_id")));
                history.setCategoryName(c.getString(c.getColumnIndex("category_name"))); // JOIN lấy tên chủ đề
                history.setDate(c.getString(c.getColumnIndex("date")));

                historyList.add(history);
            } while (c.moveToNext());
        }
        c.close();
        return historyList;
    }





    public void deleteHistoryById(int id) {
        db = getWritableDatabase();
        db.delete("History", "id = ?", new String[]{String.valueOf(id)});
    }



}
