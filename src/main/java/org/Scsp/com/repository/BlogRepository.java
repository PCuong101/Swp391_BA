package org.Scsp.com.repository;

import org.Scsp.com.Enum.BlogStatus;
import org.Scsp.com.model.Blog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface BlogRepository extends JpaRepository<Blog,Long> {
    List<Blog> findBlogByStatus(BlogStatus status);
    List<Blog> findByAuthor_UserId(Long userId);
}
