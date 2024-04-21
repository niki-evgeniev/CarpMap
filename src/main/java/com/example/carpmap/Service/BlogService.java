package com.example.carpmap.Service;

import com.example.carpmap.Models.DTO.Blog.BlogDetailsDTO;

import java.util.List;

public interface BlogService {

    void addBlog();

    List<BlogDetailsDTO> getDetailsBlog();
}
