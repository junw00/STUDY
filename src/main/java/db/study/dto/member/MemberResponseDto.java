package db.study.dto.member;

import lombok.*;

@Setter @Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class MemberResponseDto {
    private String memberId;
    private String password;
    private String name;
}
