package com.icia.member.entity;

import com.icia.member.dto.MemberDetailDTO;
import com.icia.member.dto.MemberSaveDTO;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name = "member_table")
public class MemberEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    @Column(length = 50, unique = true)
    private String memberEmail;

    @Column(length = 20)
    private String memberPassword;

    @Column
    private String memberName;

    // MemberSaveDTO -> MemberEntity 객체로 변환하기 위한 매서드
    public static MemberEntity saveMember(MemberSaveDTO memberSaveDTO) {
        MemberEntity memberEntity = new MemberEntity();
//        String memberEmail = memberSaveDTO.getMemberEmail();
//        memberEntity.setMemberEmail(memberEmail);
        memberEntity.setMemberEmail(memberSaveDTO.getMemberEmail());
        memberEntity.setMemberPassword(memberSaveDTO.getMemberPassword());
        memberEntity.setMemberName(memberSaveDTO.getMemberName());
        return memberEntity;


    }

    public static MemberEntity toUpdateMember(MemberDetailDTO memberDetailDTO) {
        // MemberEntity타입의 객체를 보내기 위해 memberEntity라는 객체 선언
        MemberEntity memberEntity = new MemberEntity();

        // memberEntity 객체에 memberDetailDTO 객체 안에 담긴 각 요소를 담아줌.
        memberEntity.setId(memberDetailDTO.getMemberId());
        memberEntity.setMemberEmail(memberDetailDTO.getMemberEmail());
        memberEntity.setMemberPassword(memberDetailDTO.getMemberPassword());
        memberEntity.setMemberName(memberDetailDTO.getMemberName());

        // 변환이 완료된 memberEntity 객체를 넘겨줌
        return memberEntity;
    }
}
