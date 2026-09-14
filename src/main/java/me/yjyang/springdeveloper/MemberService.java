package me.yjyang.springdeveloper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class MemberService {
    @Autowired
    private MemberRepository memberRepository;

    // 멤버 테이블에 있는 모든 레코드들을 읽어서 반환하는 것을 하나의 메소드로 구현
    public List<Member> getAllMembers() {
        return memberRepository.findAll();

    }
}
