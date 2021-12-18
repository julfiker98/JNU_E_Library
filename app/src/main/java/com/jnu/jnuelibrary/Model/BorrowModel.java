package com.jnu.jnuelibrary.Model;

public class BorrowModel {
    String st_name,st_email,st_id,session,uid,timeStamp,book_name,writer_name,book_type,serial_no,isbn_no;

    public BorrowModel() {
    }

    public BorrowModel(String st_name, String st_email, String st_id, String session,
                       String uid, String timeStamp, String book_name, String writer_name, String book_type, String serial_no, String isbn_no) {
        this.st_name = st_name;
        this.st_email = st_email;
        this.st_id = st_id;
        this.session = session;
        this.uid = uid;
        this.timeStamp = timeStamp;
        this.book_name = book_name;
        this.writer_name = writer_name;
        this.book_type = book_type;
        this.serial_no = serial_no;
        this.isbn_no = isbn_no;
    }

    public String getSt_name() {
        return st_name;
    }

    public void setSt_name(String st_name) {
        this.st_name = st_name;
    }

    public String getSt_email() {
        return st_email;
    }

    public void setSt_email(String st_email) {
        this.st_email = st_email;
    }

    public String getSt_id() {
        return st_id;
    }

    public void setSt_id(String st_id) {
        this.st_id = st_id;
    }

    public String getSession() {
        return session;
    }

    public void setSession(String session) {
        this.session = session;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String getBook_name() {
        return book_name;
    }

    public void setBook_name(String book_name) {
        this.book_name = book_name;
    }

    public String getWriter_name() {
        return writer_name;
    }

    public void setWriter_name(String writer_name) {
        this.writer_name = writer_name;
    }

    public String getBook_type() {
        return book_type;
    }

    public void setBook_type(String book_type) {
        this.book_type = book_type;
    }

    public String getSerial_no() {
        return serial_no;
    }

    public void setSerial_no(String serial_no) {
        this.serial_no = serial_no;
    }

    public String getIsbn_no() {
        return isbn_no;
    }

    public void setIsbn_no(String isbn_no) {
        this.isbn_no = isbn_no;
    }
}
