package com.example.demo.Controller;

import com.example.demo.DTO.ComplaintDTO;
import com.example.demo.DTO.UserPostsDTO;
import com.example.demo.Mapper.UserPostsMapper;
import com.example.demo.Model.UserPosts;
import com.example.demo.Repository.PostRepository;
import com.example.demo.Service.UserService;


import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true",
        allowedHeaders = {"Content-Type", "Authorization"},
        methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PATCH, RequestMethod.DELETE})
public class UserController {

    @Autowired
    private PostRepository postRepository;
    @Autowired
    private UserService userService;

    @GetMapping("/redirect")
    public void redirectToFrontend(HttpServletResponse response) throws IOException {
        response.sendRedirect("http://localhost:3000/dashboard");  // Replace with dynamic frontend URL if needed
    }


    @GetMapping("/dashboard")
    @ResponseBody
    public List<UserPostsDTO> getPosts() {
        List<UserPosts> userPosts = postRepository.findAll();
        List<UserPosts> sortedPosts = userPosts.stream()
                .sorted((p1, p2) -> p2.getCreatedAt().compareTo(p1.getCreatedAt()))
                .toList();

        return UserPostsMapper.toDTOList(sortedPosts);
    }

    @PostMapping("/submit")
    public void postComplaint(@AuthenticationPrincipal OidcUser oidcUser, @RequestBody ComplaintDTO complaintDTO) {
        System.out.println(oidcUser.getName());
        userService.postComplaint(oidcUser.getName(), complaintDTO.getComplaint(), complaintDTO.getDomain());
    }



    @GetMapping("/MyPosts")
    @ResponseBody
    public List<UserPostsDTO> getMyPosts(@AuthenticationPrincipal OidcUser oidcUser) {
        String googleId = oidcUser.getName();
        List<UserPosts> userPosts = postRepository.findByUser_GoogleId(googleId);
        for(UserPosts userPost : userPosts){
            System.out.println(userPost);
        }
        return UserPostsMapper.toDTOList(userPosts);
    }
    @DeleteMapping("/delete/{index}")
    public void deletePost (@PathVariable int index){
            userService.deletePost(index);
    }


    @GetMapping("/dashboard/{domain}")
    @ResponseBody
    public List<UserPostsDTO> getPostsByDomain(@PathVariable String domain) {
        List<UserPosts> userPosts = postRepository.findByDomain(domain);
        return UserPostsMapper.toDTOList(userPosts);
    }

    @PostMapping("/like/{index}")
    public Map<String, Object> handleLike(@AuthenticationPrincipal OidcUser oidcUser, @PathVariable int index) {
        String googleId = oidcUser.getName();
        return userService.updateLikes(index, googleId);
    }



}
