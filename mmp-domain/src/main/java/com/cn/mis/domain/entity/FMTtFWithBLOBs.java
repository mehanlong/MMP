package com.cn.mis.domain.entity;

public class FMTtFWithBLOBs extends FMTtF {
    private String comment;

    private String attachment;

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment == null ? null : comment.trim();
    }

    public String getAttachment() {
        return attachment;
    }

    public void setAttachment(String attachment) {
        this.attachment = attachment == null ? null : attachment.trim();
    }
}