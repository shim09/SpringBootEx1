package com.icia.member;

import com.icia.member.dto.MemberDetailDTO;
import com.icia.member.dto.MemberLoginDTO;
import com.icia.member.dto.MemberSaveDTO;
import com.icia.member.repository.MemberRepository;
import com.icia.member.service.MemberService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import javax.transaction.Transactional;

import java.lang.reflect.Member;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.InstanceOfAssertFactories.OPTIONAL;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;



@SpringBootTest
public class MemberTest {

    @Autowired
    private MemberService ms;

    @Test
    @Transactional
    @DisplayName("로그인 테스트")
    @Rollback
    public void LoginTest() {

        // 로그인시 필요한 항목들
        // 1. 회원가입 먼저 진행을 한 후 이메일과 pw 를 받아서
        // 일치하면 로그인 일치하지 않으면 실패.
        final String testEmail = "로그인 테스트 이메일";
        final String testPassword = "로그인 테스트 비밀번호";
        final String testName = "로그인 테스트 이름";

        MemberSaveDTO memberSaveDTO = new MemberSaveDTO(testEmail, testPassword, testName);
        ms.save(memberSaveDTO);

        MemberLoginDTO memberLoginDTO = new MemberLoginDTO(testEmail, testPassword);
        Boolean result = ms.login(memberLoginDTO);

        assertThat(result).isEqualTo(true);
    }

    @Test
    @DisplayName("회원 생성")
    public void newMembers() {
        IntStream.rangeClosed(1, 15).forEach(i -> {
            ms.save(new MemberSaveDTO("email" + i, "pw" + i, "name" + i));
        });

    }

    @Test
    @DisplayName("회원 삭제 테스트")
    @Transactional
    public void  deleteTest() {
        // 필요한 항목 살펴보기...
        // 회원 생성을 진행하고, memberId를 기준으로 해당 회원을 삭제한다.

        final String testEmail = "삭제 회원 이메일";
        final String testPassword = "삭제 회원 비번";
        final String testName = "삭제 회원 이름";

        MemberSaveDTO memberSaveDTO = new MemberSaveDTO(testEmail, testPassword, testName);
        Long memberId = ms.save(memberSaveDTO);

        ms.deleteById(memberId);

//        System.out.println(ms.findById(memberId));
        // 삭제한 회원의 id로 조회를 시도했을 때 null이어야 테스트 통과
        // NoSuchElementException은 무시하고 테스트를 수행
        assertThrows(NoSuchElementException.class, () -> {
            assertThat(ms.findById(memberId)).isNull();
        });


    }

}



