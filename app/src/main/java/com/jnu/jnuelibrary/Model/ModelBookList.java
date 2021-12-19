package com.jnu.jnuelibrary.Model;

public class ModelBookList {

    String book_name,writer_name,book_type,serial_no,isbn_no,book_link;

    public ModelBookList() {
    }

    public ModelBookList(String book_name, String writer_name, String book_type, String serial_no, String isbn_no, String book_link) {
        this.book_name = book_name;
        this.writer_name = writer_name;
        this.book_type = book_type;
        this.serial_no = serial_no;
        this.isbn_no = isbn_no;
        this.book_link = book_link;
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

    public String getBook_link() {
        return book_link;
    }

    public void setBook_link(String book_link) {
        this.book_link = book_link;
    }
}
