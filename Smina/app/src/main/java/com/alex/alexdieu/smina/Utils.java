package com.alex.alexdieu.smina;

/**
 * Created by Alex Dieu on 11/14/2015.
 */
public final class Utils {
    protected static String TOP_NAVIGATION_TITLE = "";
    protected final static String PACKAGE_NAME = "com.alex.alexdieu.smina";

    /*
    * 1. Từ vựng
    * 2. List các bài học tương ứng với mỗi trình độ
    * 3. List kanji của mỗi bài
    * 4. List ngữ pháp trong 1 bài
    * 5. List chữ cái trong bảng chữ cái
    * 6. List kaiwa trong 1 bài học - list này có 1 phần tử
    * */
    protected static int LIST_TYPE = 1;
    protected static int MAINCONTENT_LAYOUT_ID = 112;
    protected static int TOP_NAVIGATION_ID = 111;
    protected static int scrWidth = 0;

    /*
    * 0. Từ vựng
    * 1. Ngữ pháp
    * 2. Kaiwa
    * 3. Kanji
    * */
    protected static int TAB_OF_LESSON = 0;
    protected static int TAB_OF_LESSON_ID = 211;

    /*
    * Khi click chọn bài học, biến này sẽ lưu lại giá trị ID của bài học đó
    * */
    protected static int LESSON_NUMBER_ID = 0;

    /*
    * Lưu lại các thông tin khi gọi hàm genView
    * -
    *
    * */
    protected static String genView_screenName;
    protected static String[] genView_args;
}
