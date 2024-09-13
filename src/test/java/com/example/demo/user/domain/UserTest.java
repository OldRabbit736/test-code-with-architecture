package com.example.demo.user.domain;

import com.example.demo.common.domain.exception.CertificationCodeNotMatchedException;
import com.example.demo.mock.TestClockHolder;
import com.example.demo.mock.TestUuidHolder;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class UserTest {

    @Test
    public void UserCreate_객체로_생성할_수_있다() {
        // given
        UserCreate userCreate = UserCreate.builder()
                .email("sylvan0212@gmail.com")
                .nickname("OR")
                .address("Pangyo")
                .build();

        // when
        User user = User.from(userCreate, new TestUuidHolder("aaaaaa-aaaaa-aaaa-aaaa"));

        // then
        assertThat(user.getId()).isNull();
        assertThat(user.getEmail()).isEqualTo("sylvan0212@gmail.com");
        assertThat(user.getNickname()).isEqualTo("OR");
        assertThat(user.getAddress()).isEqualTo("Pangyo");
        assertThat(user.getStatus()).isEqualTo(UserStatus.PENDING);
        assertThat(user.getCertificationCode()).isEqualTo("aaaaaa-aaaaa-aaaa-aaaa");
    }

    @Test
    public void UserUpdate_객체로_업데이트_할_수_있다() {
        // given
        User user = User.builder()
                .id(1L)
                .email("sylvan0212@gmail.com")
                .nickname("OR")
                .address("Seoul")
                .status(UserStatus.ACTIVE)
                .certificationCode("aaaaaa-aaaaa-aaaa-aaab")
                .lastLoginAt(100L)
                .build();
        UserUpdate userUpdate = UserUpdate.builder()
                .nickname("OR-2")
                .address("Busan")
                .build();

        // when
        user = user.update(userUpdate);

        // then
        assertThat(user.getId()).isEqualTo(1);
        assertThat(user.getEmail()).isEqualTo("sylvan0212@gmail.com");
        assertThat(user.getNickname()).isEqualTo("OR-2");
        assertThat(user.getAddress()).isEqualTo("Busan");
        assertThat(user.getStatus()).isEqualTo(UserStatus.ACTIVE);
        assertThat(user.getCertificationCode()).isEqualTo("aaaaaa-aaaaa-aaaa-aaab");
        assertThat(user.getLastLoginAt()).isEqualTo(100);
    }

    @Test
    public void 로그인을_할_수_있고_로그인시_마지막_로그인_시간이_업데이트된다() {
        // given
        User user = User.builder()
                .id(1L)
                .email("sylvan0212@gmail.com")
                .nickname("OR")
                .address("Seoul")
                .status(UserStatus.ACTIVE)
                .certificationCode("aaaaaa-aaaaa-aaaa-aaab")
                .lastLoginAt(100L)
                .build();

        // when
        user = user.login(new TestClockHolder(1_000_000));

        // then
        assertThat(user.getLastLoginAt()).isEqualTo(1_000_000);
    }

    @Test
    public void 유효한_인증_코드로_계정을_활성화_할_수_있다() {
        // given
        User user = User.builder()
                .id(1L)
                .email("sylvan0212@gmail.com")
                .nickname("OR")
                .address("Seoul")
                .status(UserStatus.PENDING)
                .certificationCode("aaaaaa-aaaaa-aaaa-aaab")
                .lastLoginAt(100L)
                .build();

        // when
        user = user.certificate("aaaaaa-aaaaa-aaaa-aaab");

        // then
        assertThat(user.getStatus()).isEqualTo(UserStatus.ACTIVE);
    }

    @Test
    public void 잘못된_인증_코드로_계정을_활성화_시도하면_에러를_던진다() {
        // given
        User user = User.builder()
                .id(1L)
                .email("sylvan0212@gmail.com")
                .nickname("OR")
                .address("Seoul")
                .status(UserStatus.PENDING)
                .certificationCode("aaaaaa-aaaaa-aaaa-aaab")
                .lastLoginAt(100L)
                .build();

        // when
        // then
        assertThatThrownBy(() -> user.certificate("aaaaaa-aaaaa-aaaa-aaac"))
                .isInstanceOf(CertificationCodeNotMatchedException.class);
    }
}
