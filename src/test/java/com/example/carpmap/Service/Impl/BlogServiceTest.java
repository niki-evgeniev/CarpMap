package com.example.carpmap.Service.Impl;


import com.example.carpmap.Models.DTO.Blog.BlogDetailsDTO;
import com.example.carpmap.Models.DTO.Blog.BlogPackagesDTO;
import com.example.carpmap.Models.Entity.Blog;
import com.example.carpmap.Models.Entity.User;
import com.example.carpmap.Repository.BlogRepository;
import com.example.carpmap.Repository.UserRepository;
import com.example.carpmap.Service.Impl.BlogServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class BlogServiceTest {
    @Mock
    private BlogRepository blogRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private BlogServiceImpl blogService;

    @BeforeEach
    void setUp() {
        blogService = new BlogServiceImpl(blogRepository, userRepository, modelMapper);
    }

    @Test
    void testAddBlog_WhenNoBlogsExistAndUserExists() {
        when(blogRepository.count()).thenReturn(0L);
        when(userRepository.count()).thenReturn(1L);
        when(userRepository.findById(1L)).thenReturn(Optional.of(new User()));

        blogService.addBlog();

        verify(blogRepository, atLeastOnce()).save(any(Blog.class));
    }

    @Test
    void testGetDetailsBlog() {
        Blog blog = new Blog();
        when(blogRepository.findAll()).thenReturn(List.of(blog));
        when(modelMapper.map(any(Blog.class), eq(BlogDetailsDTO.class))).thenReturn(new BlogDetailsDTO());

        List<BlogDetailsDTO> result = blogService.getDetailsBlog();

        assertEquals(1, result.size());
        verify(blogRepository, times(1)).findAll();
    }

    @Test
    void testGetBlogPackages() {
        Blog blog = new Blog();
        when(blogRepository.findAll()).thenReturn(List.of(blog));
        when(modelMapper.map(any(Blog.class), eq(BlogPackagesDTO.class))).thenReturn(new BlogPackagesDTO());

        List<BlogPackagesDTO> result = blogService.getBlogPackages();

        assertEquals(1, result.size());
        verify(blogRepository, times(1)).findAll();
    }
}
