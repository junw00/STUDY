package db.study.domain;

import db.study.dto.member.MemberResponseDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
@Slf4j
public class Member {
    private String id;
    private String password;
    private String name;

    public MemberResponseDto toDto() {
        MemberResponseDto memberResponseDto = new MemberResponseDto();
        memberResponseDto.setMemberId(id);
        memberResponseDto.setPassword(password);
        memberResponseDto.setName(name);
        return memberResponseDto;
    }
}
