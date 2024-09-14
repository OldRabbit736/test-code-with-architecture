package com.example.demo.post.controller;

import com.example.demo.common.domain.exception.ResourceNotFoundException;
import com.example.demo.mock.TestContainer;
import com.example.demo.post.controller.response.PostResponse;
import com.example.demo.post.domain.Post;
import com.example.demo.post.domain.PostUpdate;
import com.example.demo.user.domain.User;
import com.example.demo.user.domain.UserStatus;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class PostControllerTest {

    @Test
    void 사용자는_게시물을_단건_조회할_수_있다() {
        // given
        TestContainer testContainer = TestContainer.builder()
                .build();
        User writer = testContainer.userRepository.save(User.builder()
                .id(1L)
                .email("sylvan0212@gmail.com")
                .nickname("OR")
                .address("Seoul")
                .status(UserStatus.ACTIVE)
                .certificationCode("aaaaaa-aaaaa-aaaa-aaab")
                .build());
        testContainer.postRepository.save(Post.builder()
                .id(23L)
                .content("helloworld")
                .createdAt(1678530673958L)
                .modifiedAt(1679530673958L)
                .writer(writer)
                .build());

        // when
        ResponseEntity<PostResponse> result = testContainer.postController.getPostById(23);

        // then
        assertThat(result.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(200));
        assertThat(result.getBody()).isNotNull();
        assertThat(result.getBody().getId()).isEqualTo(23);
        assertThat(result.getBody().getContent()).isEqualTo("helloworld");
        assertThat(result.getBody().getCreatedAt()).isEqualTo(1678530673958L);
        assertThat(result.getBody().getModifiedAt()).isEqualTo(1679530673958L);
        assertThat(result.getBody().getWriter().getEmail()).isEqualTo("sylvan0212@gmail.com");
    }

    @Test
    void 사용자가_존재하지_않는_게시물을_조회할_경우_에러가_난다() {
        // given
        TestContainer testContainer = TestContainer.builder()
                .build();

        // when
        // then
        assertThatThrownBy(() -> testContainer.postController.getPostById(23))
                .isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void 사용자는_게시물을_수정할_수_있다() {
        // given
        TestContainer testContainer = TestContainer.builder()
                .clockHolder(() -> 1680530673958L)
                .build();
        User writer = testContainer.userRepository.save(User.builder()
                .id(1L)
                .email("sylvan0212@gmail.com")
                .nickname("OR")
                .address("Seoul")
                .status(UserStatus.ACTIVE)
                .certificationCode("aaaaaa-aaaaa-aaaa-aaab")
                .build());
        testContainer.postRepository.save(Post.builder()
                .id(23L)
                .content("helloworld")
                .createdAt(1678530673958L)
                .modifiedAt(1679530673958L)
                .writer(writer)
                .build());
        PostUpdate postUpdate = PostUpdate.builder()
                .content("hiworld")
                .build();

        // when
        ResponseEntity<PostResponse> result = testContainer.postController.updatePost(23, postUpdate);

        // then
        assertThat(result.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(200));
        assertThat(result.getBody()).isNotNull();
        assertThat(result.getBody().getId()).isEqualTo(23);
        assertThat(result.getBody().getContent()).isEqualTo("hiworld");
        assertThat(result.getBody().getCreatedAt()).isEqualTo(1678530673958L);
        assertThat(result.getBody().getModifiedAt()).isEqualTo(1680530673958L);
        assertThat(result.getBody().getWriter().getEmail()).isEqualTo("sylvan0212@gmail.com");
    }
}
