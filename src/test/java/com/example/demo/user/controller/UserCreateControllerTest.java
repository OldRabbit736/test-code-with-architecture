package com.example.demo.user.controller;

import com.example.demo.mock.TestContainer;
import com.example.demo.user.controller.response.UserResponse;
import com.example.demo.user.domain.UserCreate;
import com.example.demo.user.domain.UserStatus;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;

class UserCreateControllerTest {

    @Test
    void 사용자는_회원_가입을_할_수_있고_회원가입된_사용자는_PENDING_상태이다() {
        // given
        TestContainer testContainer = TestContainer.builder()
                .uuidHolder(() -> "aaaaaa-aaaaa-aaaa-aaab")
                .build();
        UserCreate userUpdate = UserCreate.builder()
                .email("sylvan0212@gmail.com")
                .nickname("OR")
                .address("Seoul")
                .build();

        // when
        ResponseEntity<UserResponse> result = testContainer.userCreateController
                .createUser(userUpdate);

        // then
        assertThat(result.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(201));
        assertThat(result.getBody()).isNotNull();
        assertThat(result.getBody().getId()).isNotNull();
        assertThat(result.getBody().getEmail()).isEqualTo("sylvan0212@gmail.com");
        assertThat(result.getBody().getNickname()).isEqualTo("OR");
        assertThat(result.getBody().getStatus()).isEqualTo(UserStatus.PENDING); // PENDING 상태
        assertThat(result.getBody().getLastLoginAt()).isNull();
        assertThat(testContainer.userRepository.getById(1).getCertificationCode())
                .isEqualTo("aaaaaa-aaaaa-aaaa-aaab");
    }
}
