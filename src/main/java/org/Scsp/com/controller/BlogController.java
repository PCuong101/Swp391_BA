package org.Scsp.com.controller;



import lombok.AllArgsConstructor;
import org.Scsp.com.Enum.BlogStatus;
import org.Scsp.com.dto.BlogDto;
import org.Scsp.com.service.BlogService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/blog")
@AllArgsConstructor
public class BlogController {


    private BlogService blogService;


    @PostMapping("{userId}")
    public ResponseEntity<BlogDto> create(@RequestBody BlogDto dto, @PathVariable long userId) {
        return ResponseEntity.ok(blogService.create(dto, userId));
    }

    @GetMapping("/all")
    public ResponseEntity<List<BlogDto>> getAllBlogs() {
        return ResponseEntity.ok(blogService.findAll());
    }

    @GetMapping("/public")
    public List<BlogDto> getPublicBlogs() {
        return blogService.findByStatus(BlogStatus.APPROVED);
    }

    @PutMapping("/{id}/approve")
    public ResponseEntity<?> approve(@PathVariable Long id) {
        blogService.updateStatus(id, BlogStatus.APPROVED);
        return ResponseEntity.ok("Blog đã được duyệt");
    }

    @PutMapping("/{id}/reject")
    public ResponseEntity<?> reject(@PathVariable Long id) {
        blogService.updateStatus(id, BlogStatus.REJECTED);
        return ResponseEntity.ok("Blog đã bị từ chối");
    }

    @GetMapping("/{id}")
    public ResponseEntity<BlogDto> getBlogById(@PathVariable Long id) {
        return ResponseEntity.ok(blogService.findById(id));
    }
}
