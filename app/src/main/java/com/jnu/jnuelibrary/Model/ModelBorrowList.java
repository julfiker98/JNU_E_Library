package com.jnu.jnuelibrary.Model;

public class ModelBorrowList {
    String st_name,book_name,serial_no,st_id,time;

    public ModelBorrowList() {
    }

    public ModelBorrowList(String st_name, String book_name, String serial_no, String st_id, String time) {
        this.st_name = st_name;
        this.book_name = book_name;
        this.serial_no = serial_no;
        this.st_id = st_id;
        this.time = time;
    }

    public String getSt_name() {
        return st_name;
    }

    public void setSt_name(String st_name) {
        this.st_name = st_name;
    }

    public String getBook_name() {
        return book_name;
    }

    public void setBook_name(String book_name) {
        this.book_name = book_name;
    }

    public String getSerial_no() {
        return serial_no;
    }

    public void setSerial_no(String serial_no) {
        this.serial_no = serial_no;
    }

    public String getSt_id() {
        return st_id;
    }

    public void setSt_id(String st_id) {
        this.st_id = st_id;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
