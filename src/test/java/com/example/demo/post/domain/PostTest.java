package com.example.demo.post.domain;

import com.example.demo.mock.TestClockHolder;
import com.example.demo.user.domain.User;
import com.example.demo.user.domain.UserStatus;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class PostTest {

    @Test
    public void PostCreate으로_게시물을_만들_수_있다() {
        // given
        PostCreate postCreate = PostCreate.builder()
                .writerId(1)
                .content("helloworld")
                .build();

        User writer = User.builder()
                .id(1L)
                .email("sylvan0212@gmail.com")
                .nickname("OR")
                .address("Seoul")
                .status(UserStatus.ACTIVE)
                .certificationCode("aaaaaa-aaaaa-aaaa-aaab")
                .build();

        // when
        Post post = Post.from(writer, postCreate, new TestClockHolder(1_679_530_673_958L));

        // then
        assertThat(post.getContent()).isEqualTo("helloworld");
        assertThat(post.getCreatedAt()).isEqualTo(1_679_530_673_958L);
        assertThat(post.getWriter().getEmail()).isEqualTo("sylvan0212@gmail.com");
        assertThat(post.getWriter().getNickname()).isEqualTo("OR");
        assertThat(post.getWriter().getAddress()).isEqualTo("Seoul");
        assertThat(post.getWriter().getStatus()).isEqualTo(UserStatus.ACTIVE);
        assertThat(post.getWriter().getCertificationCode()).isEqualTo("aaaaaa-aaaaa-aaaa-aaab");
    }

    @Test
    public void PostUpdate로_게시물을_수정할_수_있다() {
        // given
        PostUpdate postUpdate = PostUpdate.builder()
                .content("foobar")
                .build();

        User writer = User.builder()
                .id(1L)
                .email("sylvan0212@gmail.com")
                .nickname("OR")
                .address("Seoul")
                .status(UserStatus.ACTIVE)
                .certificationCode("aaaaaa-aaaaa-aaaa-aaab")
                .build();
        Post post = Post.builder()
                .id(1L)
                .content("helloworld")
                .createdAt(1_678_530_673_958L)
                .modifiedAt(0L)
                .writer(writer)
                .build();

        // when
        post = post.update(postUpdate, new TestClockHolder(1_679_530_673_958L));

        // then
        assertThat(post.getContent()).isEqualTo("foobar");
        assertThat(post.getModifiedAt()).isEqualTo(1_679_530_673_958L);
    }

}
