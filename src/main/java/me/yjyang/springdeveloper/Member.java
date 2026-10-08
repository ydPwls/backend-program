package me.yjyang.springdeveloper;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
@Entity
public class Member {
    @Id // Id 칼럼이 있어야 오류가 없어짐 (Primary Key)
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    @Column(name="id", updatable = false)
    private Long id;

    @Column(name="name",nullable = false) // null값 불허
    private String name;

    // 회원 관리 API 실습
    // @Column(name="email", updatable = false)
    private String  email;

    // 이름만 받는 인자 생성 | 1008
    public Member(String name) {
        this.name = name;
    }
}
