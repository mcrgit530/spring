package com.example.demo.Service;

import com.example.demo.Model.User;
import com.example.demo.Model.UserPosts;
import com.example.demo.Repository.PostRepository;
import com.example.demo.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PostRepository postRepository;

    public void getEmail(String email, String name, String GoogleId ,  String photoUrl) {
        User userEmail = new User();
        userEmail.setEmail(email);
        userEmail.setName(name);

        userEmail.setGoogleId(GoogleId);

        userEmail.setPhotoUrl(photoUrl);
        userRepository.save(userEmail);
    }

    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }


    public void postComplaint(String googleId, String complaint, String domain) {
        UserPosts post = new UserPosts();
        User user = findByGoogleId(googleId);
        Random random = new Random();
        int index = random.nextInt(90000) + 10000;
        System.out.println(index);
        post.setIndex(index);
        post.setComplaint(complaint);
        System.out.println(post.getComplaint());
        post.setDomain(domain);
        System.out.println(post.getDomain());
        post.setCreatedAt(LocalDateTime.now());
        post.setLikeCount(0);
        post.setUser(user);
        postRepository.save(post);

    }

    public User findByGoogleId(String googleId){
        return userRepository.findByGoogleId(googleId);
    }

    public void deletePost(int index) {
        postRepository.deleteById(index);
    }

    public Map<String, Object> updateLikes(int index, String googleId) {
        Optional<UserPosts> postOptional = postRepository.findById(index);

        if (postOptional.isPresent()) {
            UserPosts post = postOptional.get();
            Set<String> likedIds = post.getLikedIds();

            boolean likedByUser = likedIds.contains(googleId);
            int updatedLikes = post.getLikeCount() + (likedByUser ? -1 : 1);

            if (likedByUser) {
                likedIds.remove(googleId);
            } else {
                likedIds.add(googleId);
            }

            post.setLikeCount(updatedLikes);
            post.setLikedIds(likedIds);
            postRepository.save(post);

            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("likes", updatedLikes);
            return response;
        } else {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("success", false);
            errorResponse.put("message", "Post not found");
            return errorResponse;
        }
    }


}