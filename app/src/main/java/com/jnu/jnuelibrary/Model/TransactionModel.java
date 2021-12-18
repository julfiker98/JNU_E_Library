package com.jnu.jnuelibrary.Model;

public class TransactionModel {
    String st_name,st_id,book_name,serial_no,time,transaction_type;

    public TransactionModel() {
    }

    public TransactionModel(String st_name, String st_id, String book_name, String serial_no, String time, String transaction_type) {
        this.st_name = st_name;
        this.st_id = st_id;
        this.book_name = book_name;
        this.serial_no = serial_no;
        this.time = time;
        this.transaction_type = transaction_type;
    }

    public String getSt_name() {
        return st_name;
    }

    public void setSt_name(String st_name) {
        this.st_name = st_name;
    }

    public String getSt_id() {
        return st_id;
    }

    public void setSt_id(String st_id) {
        this.st_id = st_id;
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

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getTransaction_type() {
        return transaction_type;
    }

    public void setTransaction_type(String transaction_type) {
        this.transaction_type = transaction_type;
    }
}
