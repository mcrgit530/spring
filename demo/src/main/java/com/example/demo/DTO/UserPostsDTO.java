package com.example.demo.DTO;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class UserPostsDTO {
    private int index;
    private String complaint;
    private String domain;
    private int likeCount;
    private String createdAt;

    public UserPostsDTO(int index, String complaint,String domain, int likeCount, LocalDateTime createdAt) {
        this.index = index;
        this.complaint = complaint;
        this.domain = domain;
        this.likeCount = likeCount;
        this.createdAt = createdAt.format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss"));
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public String getComplaint() {
        return complaint;
    }

    public void setComplaint(String complaint) {
        this.complaint = complaint;
    }

    public int getLikeCount() {
        return likeCount;
    }

    public void setLikeCount(int likeCount) {
        this.likeCount = likeCount;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }
}
