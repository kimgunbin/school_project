package com.example.pocket.class_.board.adapter;

public class CommentVO {

    private String  seq;
    private String id;
    private String content;

    public CommentVO(String seq, String id, String content) {
        this.seq = seq;
        this.id = id;
        this.content = content;
    }

    public String getSeq() {
        return seq;
    }

    public void setSeq(String seq) {
        this.seq = seq;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
