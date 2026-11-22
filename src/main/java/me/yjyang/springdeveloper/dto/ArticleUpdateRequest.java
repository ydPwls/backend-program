package me.yjyang.springdeveloper.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
// lombok : Java에서 반복적으로 작성해야 하는 코드를 자동으로 만들어주는 라이브러리

@AllArgsConstructor
@Getter // get. 쓰기 위해 추가

// 기존 블로그글 수정 | 1112
public class ArticleUpdateRequest {
    private String title;
    private  String content;

    // 클라이언트가 JSON으로 보낸 데이터를 Spring이 ArticleUpdateRequest 객체에 자동으로 담아주는 것


}
