package com.jnu.jnuelibrary.Model;

public class StudentModel {
    String name,id,session,uid;

    public StudentModel() {
    }

    public StudentModel(String name, String id, String session, String uid) {
        this.name = name;
        this.id = id;
        this.session = session;
        this.uid = uid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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
}
