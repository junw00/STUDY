package db.study.service;

import db.study.domain.Member;
import db.study.dto.member.MemberRequestDto;
import db.study.dto.member.MemberResponseDto;
import db.study.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.NoSuchElementException;
import java.util.Optional;


@Service
@RequiredArgsConstructor
@Slf4j
public class MemberService {

    private final MemberRepository memberRepository;

    public void join(MemberRequestDto memberRequestDto) throws SQLException {
        Member member = new Member();
        validateDuplicateMemberId(memberRequestDto);
        member.setId(memberRequestDto.getMemberId());
        member.setPassword(memberRequestDto.getPassword());
        member.setName(memberRequestDto.getName());
        memberRepository.save(member);
    }

    public Optional<MemberResponseDto> findMember(MemberRequestDto memberRequestDto) throws SQLException, NoSuchElementException {
        Member member = memberRepository.findByMemberId(memberRequestDto.getMemberId())
                .filter(m -> m.getPassword().equals(memberRequestDto.getPassword())).get();
        return Optional.ofNullable(member.toDto());
    }

    private void validateDuplicateMemberId(MemberRequestDto memberRequestDto) throws SQLException {
        memberRepository.findByMemberId(memberRequestDto.getMemberId()).ifPresent(m -> {
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        });
    }
}
