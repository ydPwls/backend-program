package me.yjyang.springdeveloper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
// import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

// 회원 관리 API 실습
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
public class MemberController {
    @Autowired
    private MemberService memberService;
    // 요청을 받아서 적절한 비즈니스 로직으로 연결
    // htttp://localhost:8080/member 요청과 메서드를 연결
    @GetMapping("/member")
    public List<Member> getAllMembers() {

        return memberService.getAllMembers();
    }

    // 회원 관리 API 실습
    @GetMapping("/api/members")
    public List<Member> getMembers() {
        return memberService.getAllMembers();
    }

    // 회원 정보를 등록하는 요청 | 260928
    // http:localhos:8080/member 요청을 post방식으로 했을 떄 회원 등록을 처리하도록 구현
    @PostMapping("/member")
    public ResponseEntity<Member> createMember(@RequestBody Member member) {
        // 비즈니스 로직 호출 (Service에 구현)
        // return ResponseEntity.ok(memberService.saveMember(member));
        return ResponseEntity .status(HttpStatus.CREATED).body(memberService.saveMember(member));

    }

}
