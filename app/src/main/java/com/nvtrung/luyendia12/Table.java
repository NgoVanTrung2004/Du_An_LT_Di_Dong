package com.nvtrung.luyendia12;

import android.provider.BaseColumns;

public class Table {
    private Table(){}
    //dữ liệu bảng thể loại
    public static class CategoriesTable implements BaseColumns {
        public static final  String TABLE_NAME = "Categories";
        public static final  String COLUMN_NAME = "name";
    }
    //Dữ liệu bảng câu hỏi
    public static class QuestionsTable implements BaseColumns {

        //tên bảng
        public static final  String TABLE_NAME = "questions";


        //câu hỏi
        public static final  String COLUMN_QUESTION = "question";

        //chọn 4 đáp án

        public static final  String COLUMN_OPTION1 = "option1";
        public static final  String COLUMN_OPTION2 = "option2";
        public static final  String COLUMN_OPTION3 = "option3";
        public static final  String COLUMN_OPTION4 = "option4";

        //đáp án đúng
        public static final  String COLUMN_ANSWER = "answer";

        //id chuyển mục
        public static final String COLUMN_CATEGORY_ID = "id_categories";
    }
}
