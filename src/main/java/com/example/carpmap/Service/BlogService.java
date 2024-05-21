package com.example.carpmap.Service;

import com.example.carpmap.Models.DTO.Blog.BlogDetailsDTO;
import com.example.carpmap.Models.DTO.Blog.BlogPackagesDTO;

import java.util.List;

public interface BlogService {

    void addBlog();

    List<BlogDetailsDTO> getDetailsBlog();

    List<BlogPackagesDTO> getBlogPackages();
}
