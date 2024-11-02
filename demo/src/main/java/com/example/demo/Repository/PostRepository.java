package com.example.demo.Repository;

import com.example.demo.Model.UserPosts;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<UserPosts, Integer>
{
    List<UserPosts> findByUser_GoogleId(String googleId);
    List<UserPosts> findByDomain(String domain);
}
