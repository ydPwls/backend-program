package me.yjyang.springdeveloper.controller;

import lombok.RequiredArgsConstructor;
import me.yjyang.springdeveloper.dao.Article;
import me.yjyang.springdeveloper.dto.ArticleCreationRequest;
import me.yjyang.springdeveloper.dto.ArticleResponse;
import me.yjyang.springdeveloper.dto.ArticleUpdateRequest;
import me.yjyang.springdeveloper.service.BlogService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;


// 생성자 자동으로 만들어주는
@RequiredArgsConstructor

@RestController
public class BlogApiController {
    private final BlogService blogService;

    @PostMapping("/api/articles")
    public ResponseEntity<Article> addArticle(@RequestBody ArticleCreationRequest articleCreationRequest) {
        // 서비스에세 블로그 글 등록을 의뢰.
        // blogService = new BlogService(); - X
        Article savedArticle = blogService.save(articleCreationRequest);

        // 성공했다면 ReponseEntity  객체를 반환
        return ResponseEntity.status(HttpStatus.CREATED).body(savedArticle);


    }

    @GetMapping("/api/articles")
    public ResponseEntity<List<ArticleResponse>> getAllArticles() {
        List<ArticleResponse> articleResponseList = blogService
                .findAll()
                .stream()
                .map(ArticleResponse::new)
                .toList();
        return ResponseEntity.ok().body(articleResponseList);

    }

    // 클라이언트 요청 실습 (특정 블로그 조회) | 1109
    @GetMapping("/api/articles/{id}")
    public ResponseEntity<ArticleResponse> getById(@PathVariable Long id) {
        Article article = blogService.getById(id);
        return ResponseEntity.ok().body(new ArticleResponse(article));
    }


    // 삭제 실습 | 1109
    @DeleteMapping("/api/articles/{id}")
    public ResponseEntity<Void> deleteArticle(@PathVariable Long id) {
        blogService.deleteById(id);
        return ResponseEntity.ok().build();
    }

    // 기존 글 수정 실습 | 1112
    @PutMapping("/api/articles/{id}")
    public ResponseEntity<ArticleResponse> updateArticle(@PathVariable Long id, @RequestBody ArticleUpdateRequest request) {
        Article article = blogService.update(id, request); // 수정해달라 요청
        ArticleResponse articleResponse = new ArticleResponse(article);

        return ResponseEntity.ok().body(articleResponse);
    }
}
