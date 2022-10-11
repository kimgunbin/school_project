package com.example.pocket.class_.board.adapter;

public class BoardVO {

    private String title;
    private String context;
    private String file;
    private String seq;
    private String code;
    private String date;


    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
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

    public String getSeq() {
        return seq;
    }

    public void setSeq(String seq) {
        this.seq = seq;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public BoardVO(String title, String context, String date, String seq, String code) {
        this.title = title;
        this.context = context;
        this.date = date;
        this.seq = seq;
        this.code = code;
    }

//    public BoardVO(String title, String seq, String code, String date) {
//        this.title = title;
//        this.seq = seq;
//        this.code = code;
//        this.date = date;
//    }

    // private (내부 메서드(get/set)를 사용해서 접근 가능)



}
