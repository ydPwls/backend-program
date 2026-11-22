package me.yjyang.springdeveloper.service;

import lombok.RequiredArgsConstructor;
import me.yjyang.springdeveloper.dao.Article;
import me.yjyang.springdeveloper.dto.ArticleCreationRequest;
import me.yjyang.springdeveloper.repository.BlogRepository;
import org.springframework.stereotype.Service;
import me.yjyang.springdeveloper.dto.ArticleUpdateRequest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor // 초기화 생성자
@Service
public class BlogService {
    private final BlogRepository blogRepository;

    public Article save(ArticleCreationRequest  articleCreationRequest) {
        return blogRepository.save(articleCreationRequest.toEntity());
    }

    public List<Article> findAll() {
        return blogRepository.findAll();

    }

    // 클라이언트 요청 실습 (특정 블로그 조회) | 1109
    public Article getById(Long id) {
        return blogRepository.findById(id).get();
    }


    // 삭제 실습 | 1109
    public void deleteById(Long id){
        blogRepository.deleteById(id);
    }


    // 기존 게시글 수정 실습 | 1112 - 1116
    @Transactional // 메서드가 실행되는 동안 데이터베이스 작업을 하나의 트랜잭션으로 묶어주는 어노테이션
    public Article update(Long id, ArticleUpdateRequest req) {
        Article article = blogRepository
                .findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Article Not Found " + id));

        article.update(req.getTitle(), req.getContent());

        return article;
    }

}
