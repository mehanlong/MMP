package com.cn.mis.domain.entity.mis;

public class UFCommunityWithBLOBs extends UFCommunity {
    private String comment;

    private String address;

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment == null ? null : comment.trim();
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address == null ? null : address.trim();
    }
}