package com.example.pocket.class_.cctv;

public class CctvVO {

    private String title;
    private String context;
    private String file;
    private  String date;
    private String type;



    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }

    public CctvVO(String title, String context, String file) {
        this.title = title;
        this.context = context;
        this.file = file;
    }

    public CctvVO(String title, String file, String date, String type) {
        this.title = title;
        this.file = file;
        this.date = date;
        this.type = type;
    }

    // private (내부 메서드(get/set)를 사용해서 접근 가능)



}
