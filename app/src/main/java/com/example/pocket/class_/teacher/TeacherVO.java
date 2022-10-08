package com.example.pocket.class_.teacher;

public class TeacherVO {

    private String name;
    private String context;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }

    public TeacherVO(String name, String context) {
        this.name = name;
        this.context = context;
    }

    public TeacherVO(String name) {
        this.name = name;
    }
}
