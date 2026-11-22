package me.yjyang.springdeveloper.dao;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED) // 기본생성자 생성 | 같은 패키지 내에서 생성 가능
public class Article {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    @Column(updatable = false) // = Not Null
    private Long id; // YG

    @Column(nullable = false) // = Not Null
    private String title; // Not Null

    @Column(nullable = false) // = Not Null
    private String content; // Not Null

    @Builder
    public Article(String title, String content) {
        this.title = title;
        this.content = content;
    }

    // 기존 게시글 수정 실습 | 1112 - 1116
    public void update(String title, String content) {
        this.title = title;
        this.content = content;
    }



}