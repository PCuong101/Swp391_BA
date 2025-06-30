package org.Scsp.com.service.impl;

import lombok.AllArgsConstructor;
import org.Scsp.com.Enum.BlogStatus;
import org.Scsp.com.dto.BlogDto;
import org.Scsp.com.model.Blog;
import org.Scsp.com.model.User;
import org.Scsp.com.repository.BlogRepository;
import org.Scsp.com.repository.UsersRepository;
import org.Scsp.com.service.BlogService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;


@Service
@AllArgsConstructor
public class BlogServiceImpl implements BlogService {

    private final BlogRepository blogRepository;
    private final UsersRepository usersRepository;

    @Override
    public BlogDto create(BlogDto dto, long userId) {
        User user = usersRepository.findById(userId).orElseThrow(
                () -> new IllegalArgumentException("User not found"));

        Blog blog = Blog.builder()
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .author(user)
                .status(BlogStatus.PENDING)
                .title(dto.getTitle())
                .content(dto.getContent())
                .build();
        Blog saved = blogRepository.save(blog);
        return toDto(saved);
    }

    @Override
    public List<BlogDto> findAll() {
        return blogRepository.findAll().stream()
                .map(this::toDto)
                .toList();
    }

    @Override
    public void updateStatus(Long id, BlogStatus status) {
        Blog blog = blogRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Blog not found"));
        blog.setStatus(status);
        blogRepository.save(blog);
    }

    @Override
    public List<BlogDto> findByStatus(BlogStatus status) {
        List<Blog> blogs = blogRepository.findBlogByStatus(status);
        return blogs.stream()
                .map(this::toDto)
                .toList();
    }


    private BlogDto toDto(Blog blog) {
        return BlogDto.builder()
                .id(blog.getId())
                .title(blog.getTitle())
                .content(blog.getContent())
                .authorName(blog.getAuthor().getName())
                .createdAt(blog.getCreatedAt())
                .updatedAt(blog.getUpdatedAt())
                .build();
    }
}
