package net.nkwoo.start.service.posts;

import lombok.RequiredArgsConstructor;
import net.nkwoo.start.domain.posts.PostsRepository;
import net.nkwoo.start.web.dto.PostsSaveRequestDto;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@RequiredArgsConstructor
@Service
public class PostsService {

    private final PostsRepository postsRepository;

    @Transactional
    public Long save(PostsSaveRequestDto requestDto) {
        return postsRepository.save(requestDto.toEntity()).getId();
    }
}