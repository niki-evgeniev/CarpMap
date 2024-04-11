package com.example.carpmap.Service.Impl;

import com.example.carpmap.Models.DTO.Blog.BlogDetailsDTO;
import com.example.carpmap.Models.Entity.Blog;
import com.example.carpmap.Models.Entity.User;
import com.example.carpmap.Repository.BlogRepository;
import com.example.carpmap.Repository.UserRepository;
import com.example.carpmap.Service.BlogService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;


import static com.example.carpmap.Cammon.SuccessfulMessages.SUCCESSFUL_ADD_BLOG;

@Service
public class BlogServiceImpl implements BlogService {

    private final BlogRepository blogRepository;
    private final UserRepository userRepository;

    private final ModelMapper modelMapper;

    public BlogServiceImpl(BlogRepository blogRepository, UserRepository userRepository, ModelMapper modelMapper) {
        this.blogRepository = blogRepository;
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public void addBlog() {
        if (blogRepository.count() == 0 && userRepository.count() > 0) {
            Blog blog = new Blog();
            Optional<User> byId = userRepository.findById(1L);
            long id = 1;
            //ЗЕЛКА 2
            blog.setId(id);
            blog.setAuthorName(byId.get().getUsername());
            LocalDate now = LocalDate.now();
            blog.setDateAdded(LocalDate.parse("2023-07-25"));
            blog.setDescription("ЗЕЛКА 2 testing blog descriptions ");
            blog.setUrlImage("https://res.cloudinary.com/dsy8h2u3x/image/upload/v1712780225/taae8qfs1fiduriwnmwh.jpg");
            blog.setTitleBlog("Бесните шарани на яз. Зелево перо - Second challenge!");
            blog.setUrlVideo("https://www.youtube.com/watch?v=MltB12hCHIo");
            blog.setUser(byId.get());
            blogRepository.save(blog);
            System.out.printf(SUCCESSFUL_ADD_BLOG, blog.getTitleBlog(), blog.getDescription(), blog.getAuthorName());
            //ЗЕЛКА 1
            id++;
            blog.setId(id);
            blog.setAuthorName(byId.get().getUsername());
            blog.setDateAdded(LocalDate.parse("2022-05-23"));
            blog.setDescription("ЗЕЛКА 1 testing blog descriptions ");
            blog.setUrlImage("https://res.cloudinary.com/dsy8h2u3x/image/upload/v1712780225/oix7bnq1wfagjos7do59.jpg");
            blog.setTitleBlog("Лудите шарани на Зелево перо!");
            blog.setUrlVideo("https://www.youtube.com/watch?v=58aBokf-BiQ");
            blog.setUser(byId.get());
            blogRepository.save(blog);
            System.out.printf(SUCCESSFUL_ADD_BLOG, blog.getTitleBlog(), blog.getDescription(), blog.getAuthorName());
            //КУЛЕК
            id++;
            blog.setId(id);
            blog.setAuthorName(byId.get().getUsername());
            blog.setDateAdded(LocalDate.parse("2022-06-12"));
            blog.setDescription("КУЛЕК testing blog descriptions ");
            blog.setUrlImage("https://res.cloudinary.com/dsy8h2u3x/image/upload/v1712780224/yeueeoug4gwd9zhjmgfy.jpg");
            blog.setTitleBlog("В търсене на шарани 20+ на Кулеков лейк!");
            blog.setUrlVideo("https://www.youtube.com/watch?v=mdzotPA6kuU&t=5s");
            blog.setUser(byId.get());
            blogRepository.save(blog);
            System.out.printf(SUCCESSFUL_ADD_BLOG, blog.getTitleBlog(), blog.getDescription(), blog.getAuthorName());

        }
    }

    @Override
    public List<BlogDetailsDTO> getDetailsBlog() {
        List<Blog> allBlogs = blogRepository.findAll();
        List<BlogDetailsDTO> allBlogsDTO = allBlogs.stream()
                .map(this::convertToDTO)
                .toList();
        return allBlogsDTO;
    }

    private BlogDetailsDTO convertToDTO(Blog blog) {
        BlogDetailsDTO map = modelMapper.map(blog, BlogDetailsDTO.class);
        return map;
    }
}
