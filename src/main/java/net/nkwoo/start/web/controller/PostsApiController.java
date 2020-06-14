package net.nkwoo.start.web.controller;

import lombok.RequiredArgsConstructor;
import net.nkwoo.start.service.posts.PostsService;
import net.nkwoo.start.web.dto.PostsSaveRequestDto;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class PostsApiController {

    private final PostsService postsService;

    @PostMapping("/api/v1/posts")
    public Long save(@RequestBody PostsSaveRequestDto requestDto) {
        return postsService.save(requestDto);
    }

}
