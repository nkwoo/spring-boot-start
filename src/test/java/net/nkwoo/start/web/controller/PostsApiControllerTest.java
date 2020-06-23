package net.nkwoo.start.web.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import net.nkwoo.start.domain.posts.Posts;
import net.nkwoo.start.domain.posts.PostsRepository;
import net.nkwoo.start.web.dto.PostsSaveRequestDto;
import net.nkwoo.start.web.dto.PostsUpdateRequestDto;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PostsApiControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private PostsRepository postsRepository;

    @Autowired
    private WebApplicationContext context;

    private MockMvc mvc;

    @BeforeEach
    public void setup() {
        mvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();
    }

    @AfterEach
    public void tearDown() throws Exception {
        postsRepository.deleteAll();
    }

    @Test
    @WithMockUser(roles = "USER")
    @DisplayName("게시글 등록 테스트")
    public void postInsert() throws Exception {
        String title = "title";
        String content = "content";

        PostsSaveRequestDto requestDto = PostsSaveRequestDto.builder()
                .title(title)
                .content(content)
                .author("test@naver.com")
                .build();

        String url = "http://localhost:" + port + "/api/v1/posts";

        mvc.perform(post(url)
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(requestDto)))
                .andExpect(status().isOk());

        List<Posts> all = postsRepository.findAll();
        assertThat(all.get(0).getTitle()).isEqualTo(title);
        assertThat(all.get(0).getContent()).isEqualTo(content);
    }

    @Test
    @WithMockUser(roles = "USER")
    @DisplayName("게시글 수정 테스트")
    public void postUpdate() throws Exception {
        Posts savedPosts = postsRepository.save(Posts.builder()
                            .title("title")
                            .content("content")
                            .author("test@naver.com")
                            .build());

        Long updateId = savedPosts.getId();
        String expectedTitle = "title2";
        String expectedContent = "content2";

        PostsUpdateRequestDto requestDto = PostsUpdateRequestDto.builder()
                .title(expectedTitle)
                .content(expectedContent)
                .build();

        String url = "http://localhost:" + port + "/api/v1/posts/" + updateId;


        mvc.perform(put(url)
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(requestDto)))
                .andExpect(status().isOk());

        List<Posts> allPosts = postsRepository.findAll();
        assertThat(allPosts.get(0).getTitle()).isEqualTo(expectedTitle);
        assertThat(allPosts.get(0).getContent()).isEqualTo(expectedContent);
    }

    @Test
    @WithMockUser(roles = "USER")
    @DisplayName("게시글 삭제 테스트 진행")
    public void postDelete() throws Exception {
        Posts posts = postsRepository.save(Posts.builder()
                .title("testPost")
                .content("test")
                .author("test@naver.com")
                .build());

        Long postId = posts.getId();

        assertThat(postId).isGreaterThan(0L);

        String url = "http://localhost:" + port + "/api/v1/posts/" + postId;

        mvc.perform(delete(url))
            .andExpect(status().isOk());

        Optional<Posts> searchPost = postsRepository.findById(postId);

        assertThat(searchPost).isEmpty();
    }

    @Test
    @WithMockUser(roles = "USER")
    @DisplayName("Auditing 테스트 응용")
    public void baseTimeTest() throws Exception {
        postsRepository.save(Posts.builder()
                        .title("title")
                        .content("content")
                        .author("test@naver.com")
                        .build());

        List<Posts> postsList = postsRepository.findAll();

        Posts posts = postsList.get(0);

        System.out.println(">>>>> create = " + posts.getCreatedDate() + ", modified = " + posts.getModifiedDate());

        Long updateId = posts.getId();
        String expectedTitle = "title2";
        String expectedContent = "content2";

        PostsUpdateRequestDto requestDto = PostsUpdateRequestDto.builder()
                .title(expectedTitle)
                .content(expectedContent)
                .build();

        String url = "http://localhost:" + port + "/api/v1/posts/" + updateId;

        mvc.perform(put(url)
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(requestDto)))
                .andExpect(status().isOk());

        postsList = postsRepository.findAll();

        posts = postsList.get(0);

        System.out.println(">>>>> create = " + posts.getCreatedDate() + ", modified = " + posts.getModifiedDate());
    }
}
