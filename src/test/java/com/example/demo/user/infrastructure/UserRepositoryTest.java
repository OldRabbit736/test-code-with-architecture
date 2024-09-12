package com.example.demo.user.infrastructure;

import com.example.demo.user.domain.UserStatus;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@Sql("/sql/user-repository-test-data.sql")
@DataJpaTest(showSql = true)
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    void findByIdAndStatus_로_유저_데이터를_찾아올_수_있다() {
        // given

        // when
        Optional<UserEntity> result = userRepository.findByIdAndStatus(1L, UserStatus.ACTIVE);

        // then
        assertThat(result).isNotEmpty();
    }

    @Test
    void findByIdAndStatus_는_데이터가_없으면_Optional_empty_를_내려준다() {
        // given

        // when
        Optional<UserEntity> result = userRepository.findByIdAndStatus(1L, UserStatus.PENDING);

        // then
        assertThat(result).isEmpty();
    }

    @Test
    void findByEmailAndStatus_로_유저_데이터를_찾아올_수_있다() {
        // given

        // when
        Optional<UserEntity> result = userRepository.findByEmailAndStatus("sylvan0212@gmail.com", UserStatus.ACTIVE);

        // then
        assertThat(result).isNotEmpty();
    }

    @Test
    void findByEmailAndStatus_는_데이터가_없으면_Optional_empty_를_내려준다() {
        // given

        // when
        Optional<UserEntity> result = userRepository.findByEmailAndStatus("sylvan0212@gmail.com", UserStatus.PENDING);

        // then
        assertThat(result).isEmpty();
    }
}
