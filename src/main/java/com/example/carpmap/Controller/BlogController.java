package com.example.carpmap.Controller;

import com.example.carpmap.Models.DTO.Blog.BlogDetailsDTO;
import com.example.carpmap.Service.BlogService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class BlogController {

    private final BlogService blogService;

    public BlogController(BlogService blogService) {
        this.blogService = blogService;
    }

    @GetMapping("/blog")
    public ModelAndView blog() {
        ModelAndView modelAndView = new ModelAndView("blog");
        List<BlogDetailsDTO> blogDetails = blogService.getDetailsBlog();
        modelAndView.addObject("blogsDetails", blogDetails);

        return modelAndView;
    }
}
