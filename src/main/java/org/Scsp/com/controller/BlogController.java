package org.Scsp.com.controller;



import lombok.AllArgsConstructor;
import org.Scsp.com.dto.BlogDto;
import org.Scsp.com.service.impl.BlogService;
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
}
