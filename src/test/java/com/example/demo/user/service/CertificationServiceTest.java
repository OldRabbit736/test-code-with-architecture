package com.example.demo.user.service;

import com.example.demo.mock.FakeMailSender;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * SpringBootTest 애노테이션 없이 테스트 작성
 */
class CertificationServiceTest {

    @Test
    public void 이메일과_컨텐츠가_제대로_만들어져서_보내지는지_테스트한다() {
        // given
        FakeMailSender fakeMailSender = new FakeMailSender();
        CertificationService certificationService =
                new CertificationService(fakeMailSender);

        // when
        certificationService.send("sylvan0212@gmail.com", 1, "aaaaaa-aaaaa-aaaa-aaaa");

        // then - CertificationService.send 가 이메일, 타이틀, 컨텐츠를 제대로 만들어서 MailService에 전달했는지 확인 (MailSender의 동작은 이 테스트가 관심을 가지는 영역이 아니다.)
        assertThat(fakeMailSender.email).isEqualTo("sylvan0212@gmail.com");
        assertThat(fakeMailSender.title).isEqualTo("Please certify your email address");
        assertThat(fakeMailSender.content).isEqualTo(
                "Please click the following link to certify your email address: http://localhost:8080/api/users/1/verify?certificationCode=aaaaaa-aaaaa-aaaa-aaaa"
        );
    }
}
