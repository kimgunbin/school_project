package com.example.pocket.class_.board.adapter;

public class BoardVO {

    private String title;
    private String context;
    private String file;
    private String seq;
    private String code;

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


    public BoardVO(String title, String context, String file,String seq,String code) {
        this.title = title;
        this.context = context;
        this.file = file;
        this.seq = seq;
        this.code = code;
    }

// private (내부 메서드(get/set)를 사용해서 접근 가능)



}
