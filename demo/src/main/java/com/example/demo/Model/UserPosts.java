package com.example.demo.Model;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;


@Entity
@Table(name = "complaints")
public class UserPosts {
    @Id
    private int index;

    private String complaint;
    private String domain;
    private LocalDateTime createdAt;
    private int likeCount;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "google_id", nullable = false)
    private User user;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "post_likes", joinColumns = @JoinColumn(name = "post_index"))
    @Column(name = "-google_id")
    private Set<String> likedIds = new HashSet<>();

    public Set<String> getLikedIds() {
        return likedIds;
    }

    public void setLikedIds(Set<String> likedIds) {
        this.likedIds = likedIds;
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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }


}
