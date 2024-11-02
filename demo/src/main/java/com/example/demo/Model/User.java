package com.example.demo.Model;

import jakarta.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "clients")
public class User {
    @Id
    @Column(nullable = false, unique = true)
    private String googleId;

    @Column(nullable = false, unique = true)
    private String email;
    private String name;
    private String photoUrl;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<UserPosts> complaints = new HashSet<>();





    public void setGoogleId(String googleId) {
        this.googleId = googleId;
    }

    public String getGoogleId() {
        return googleId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public Set<UserPosts> getComplaints() {
        return complaints;
    }

    public void setComplaints(Set<UserPosts> complaints) {
        this.complaints = complaints;
    }


}
