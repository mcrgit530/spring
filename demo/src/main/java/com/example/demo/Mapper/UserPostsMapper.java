package com.example.demo.Mapper;

import com.example.demo.DTO.UserPostsDTO;
import com.example.demo.Model.UserPosts;

import java.util.List;
import java.util.stream.Collectors;

public class UserPostsMapper {

    public static UserPostsDTO toDTO(UserPosts userPost) {
        return new UserPostsDTO(
                userPost.getIndex(),
                userPost.getComplaint(),
                userPost.getDomain(),
                userPost.getLikeCount(),
                userPost.getCreatedAt()
        );
    }

    public static List<UserPostsDTO> toDTOList(List<UserPosts> userPosts) {
        return userPosts.stream()
                .map(UserPostsMapper::toDTO)
                .collect(Collectors.toList());
    }
}
