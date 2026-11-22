package me.yjyang.springdeveloper.controller;

// 객체를 JSON 문자열로 변환하거나 JSON 데이터를 객체로 변환하는 클래스
import com.fasterxml.jackson.databind.ObjectMapper;

// DB에 저장되는 블로그 글 엔티티 클래스
import me.yjyang.springdeveloper.dao.Article;

// 블로그 글 생성 요청에 필요한 제목과 내용을 담는 DTO
import me.yjyang.springdeveloper.dto.ArticleCreationRequest;

// 블로그 글 수정 요청에 필요한 제목과 내용을 담는 DTO
import me.yjyang.springdeveloper.dto.ArticleUpdateRequest;

// 블로그 글의 DB 접근을 담당하는 Repository
import me.yjyang.springdeveloper.repository.BlogRepository;

// 블로그 관련 비즈니스 로직을 처리하는 Service
import me.yjyang.springdeveloper.service.BlogService;

// JUnit에서 테스트 실행 후 처리할 작업을 지정하는 어노테이션
import org.junit.jupiter.api.AfterEach;

// 테스트 결과에 표시할 설명을 작성하는 어노테이션
import org.junit.jupiter.api.DisplayName;

// 해당 메서드가 테스트 메서드임을 나타내는 어노테이션
import org.junit.jupiter.api.Test;

// Spring 컨테이너에 등록된 객체를 자동으로 주입하는 어노테이션
import org.springframework.beans.factory.annotation.Autowired;

// 실제 서버를 실행하지 않고 MockMvc를 사용하여 컨트롤러를 테스트하도록 설정
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;

// Spring Boot 애플리케이션의 전체 테스트 환경을 구성하는 어노테이션
import org.springframework.boot.test.context.SpringBootTest;

// HTTP 요청 및 응답의 데이터 형식을 지정하는 클래스
import org.springframework.http.MediaType;

// MockMvc를 통해 컨트롤러에 가상 HTTP 요청을 보내는 객체
import org.springframework.test.web.servlet.MockMvc;

// HTTP 요청 실행 결과를 담아 검증에 사용하는 객체
import org.springframework.test.web.servlet.ResultActions;

// 여러 개의 블로그 글 데이터를 저장하는 List 자료형
import java.util.List;

// DB 조회 결과나 객체의 값을 비교하고 검증하는 AssertJ 메서드
import static org.assertj.core.api.Assertions.assertThat;

// GET, POST, PUT, DELETE 등의 HTTP 요청 메서드를 사용하기 위한 import
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

// 응답 JSON의 특정 값을 검증하는 메서드
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

// HTTP 응답 상태 코드를 검증하는 메서드
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

// Spring Boot 전체 환경에서 테스트를 실행
@SpringBootTest

// MockMvc를 자동으로 구성하여 실제 서버 실행 없이 컨트롤러 테스트 가능
@AutoConfigureMockMvc

class BlogApiControllerTest {

    // MockMvc 객체를 자동 주입받아 컨트롤러에 HTTP 요청을 보냄
    @Autowired
    private MockMvc mockMvc;

    // 요청 DTO 객체를 JSON 문자열로 변환하기 위해 ObjectMapper 주입
    @Autowired
    private ObjectMapper objectMapper;

    // DB에 블로그 글을 저장하거나 조회하기 위해 Repository 주입
    @Autowired
    private BlogRepository blogRepository;

    // 각 테스트 실행이 끝난 후 DB에 저장된 데이터 모두 삭제
    @AfterEach
    public void CleanUp() {

        blogRepository.deleteAll();
    }


    // 테스트 결과에 표시할 설명
    @DisplayName("addArticle: 블로그 글 추가에 성공한다.")

    // 블로그 글 생성 테스트
    @Test
    public void addArticle() throws Exception {

        // 준비 / given
        // 데이터 준비: 생성할 블로그 글의 제목과 내용
        String title = "JUnit 테스트를 통한 블로그 글 생성";
        String content = "수업 종료 10분 전, 이 테스트 성공 후 점심 식사한다.";

        // 글 생성 API의 요청 URL 지정
        String url = "/api/articles";

        // 제목과 내용을 담은 글 생성 요청 DTO 객체 생성
        ArticleCreationRequest req = new ArticleCreationRequest(title, content);

        // 요청 DTO 객체를 JSON 문자열로 변환
        String articlesStr = objectMapper.writeValueAsString(req);

        // 실행 / when
        // POST 요청을 보내 글 생성 API 실행
        ResultActions result =
                mockMvc.perform(post(url)
                        // 요청 데이터의 형식이 JSON임을 지정
                        .contentType(MediaType.APPLICATION_JSON)
                        // JSON으로 변환한 요청 데이터 전달
                        .content(articlesStr));

        // 검증 / then : DB에서 인출
        // DB에서 전체 블로그 글 목록 조회
        List<Article> articles =  blogRepository.findAll();

        // DB에 글이 1건 저장되었는지 확인
        assertThat(articles.size()).isEqualTo(1);

        // 조회한 글 목록에서 첫 번째 글 가져오기
        Article article = articles.get(0);

        // DB에 저장된 제목이 요청한 제목과 같은지 확인
        assertThat(article.getTitle()).isEqualTo(title);

        // DB에 저장된 내용이 요청한 내용과 같은지 확인
        assertThat(article.getContent()).isEqualTo(content);

    }


    // 모든 블로그 글 조회 테스트 | 1105
    @DisplayName("모든 블로그글 조회에 성공한다.")
    @Test
    public void getAllArticles() throws Exception {
        // 준비 / given
        // 모든 블로그 글 조회 API의 요청 URL 지정
        String url = "/api/articles";

        // DB에 미리 저장할 블로그 글의 제목과 내용 준비
        String title = "11월 5일 두 번째 시간";
        String content = "모든 블로그글 조회 테스트 글 1";

        // 테스트를 위해 블로그 글 1건을 DB에 미리 저장
        blogRepository.save(Article.builder().content(content).title(title).build());

        // 실행 / when
        // GET 요청을 보내 전체 블로그 글 조회 API 실행
        // accept()를 통해 서버로부터 JSON 형식의 응답을 받겠다고 지정
        ResultActions resultActions =  mockMvc.perform(get(url).accept(MediaType.APPLICATION_JSON));

        // 검증 / then
        resultActions
                // HTTP 응답 상태 코드가 200 OK인지 확인
                .andExpect(status().isOk())
                // 응답 JSON 배열의 첫 번째 글의 제목이 예상한 값과 같은지 확인
                .andExpect(jsonPath("$[0].title").value(title))
                // 응답 JSON 배열의 첫 번째 글의 내용이 예상한 값과 같은지 확인
                .andExpect(jsonPath("$[0].content").value(content));

    }


    // 특정 블로그 글 조회 테스트 | 1109
    @DisplayName("findArticle: 블로그 글 조회에 성공한다.")
    @Test
    public void findArticle() throws Exception {
        // given
        // 특정 글 조회 API의 URL 지정
        // {id}는 조회할 블로그 글의 ID가 들어감
        final String url = "/api/articles/{id}";

        // DB에 미리 저장할 블로그 글의 제목과 내용 준비
        final String title = "title";
        final String content = "content";

        // 테스트를 위해 블로그 글 1건을 DB에 저장
        // 저장된 객체를 savedArticle에 담아 생성된 ID를 사용
        Article savedArticle = blogRepository.save(Article.builder()
                .title(title)
                .content(content)
                .build());

        // when
        // GET 요청을 보내 저장된 글의 ID에 해당하는 글 조회
        final ResultActions resultActions = mockMvc.perform(get(url, savedArticle.
                getId()));

        // then
        resultActions
                // HTTP 응답 상태가 200 OK인지 확인
                .andExpect(status().isOk())
                // 응답 JSON의 content 값이 저장한 내용과 같은지 확인
                .andExpect(jsonPath("$.content").value(content))
                // 응답 JSON의 title 값이 저장한 제목과 같은지 확인
                .andExpect(jsonPath("$.title").value(title));
    }

    // 특정 블로그 글 삭제 테스트 | 1109
    @DisplayName("deleteArticle: 블로그 글 삭제에 성공한다.")
    @Test
    public void deleteArticle() throws Exception {
        // given
        // 특정 글 삭제 API의 URL 지정
        // {id}는 삭제할 블로그 글의 ID가 들어감
        final String url = "/api/articles/{id}";

        // DB에 미리 저장할 블로그 글의 제목과 내용 준비
        final String title = "title";
        final String content = "content";

        // 삭제 테스트를 위해 블로그 글 1건을 DB에 저장
        // savedArticle에 저장된 글의 ID를 보관
        Article savedArticle = blogRepository.save(Article.builder()
                .title(title)
                .content(content)
                .build());

        // when
        // DELETE 요청을 보내 해당 ID의 블로그 글 삭제
        mockMvc.perform(delete(url, savedArticle.getId()))
                // HTTP 응답 상태 코드가 200 OK인지 확인
                .andExpect(status().isOk());

        // then
        // DB에서 전체 블로그 글 목록을 다시 조회
        List<Article> articles = blogRepository.findAll();
        // DB에 남아 있는 글이 없는지 확인
        // 즉, 삭제 요청이 실제 DB에도 반영되었는지 검증
        assertThat(articles).isEmpty();
    }

    // 기존 블로그 글 수정 테스트 | 1112
    @DisplayName("updateArticle: 블로그 글 수정에 성공한다.")
    @Test
    public void updateArticle() throws Exception {
        // given
        // 특정 글 수정 API의 URL 지정
        // {id}는 수정할 블로그 글의 ID가 들어감
        final String url = "/api/articles/{id}";

        // 수정하기 전 기존 블로그 글의 제목과 내용 준비
        final String title = "title";
        final String content = "content";

        // 수정 테스트를 위해 기존 블로그 글 1건을 DB에 저장
        // savedArticle에 저장된 글의 ID를 보관
        Article savedArticle = blogRepository.save(Article.builder()
                .title(title)
                .content(content)
                .build());

        // 수정할 새로운 제목과 내용 준비
        final String newTitle = "new title";
        final String newContent = "new content";

        // 새로운 제목과 내용을 담은 수정 요청 DTO 객체 생성
        ArticleUpdateRequest request = new ArticleUpdateRequest(
                newTitle,
                newContent
        );

        // when
        // PUT 요청을 보내 기존 블로그 글 수정 API 실행
        ResultActions result = mockMvc.perform(put(url, savedArticle.getId())
                // 요청 데이터가 JSON 형식임을 지정
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                // 수정 요청 DTO 객체를 JSON 문자열로 변환하여 전달
                .content(objectMapper.writeValueAsString(request)));

        // then
        // HTTP 응답 상태 코드가 200 OK인지 확인
        result.andExpect(status().isOk());

        // 수정된 글을 DB에서 다시 조회
        // 요청 응답만 확인하는 것이 아니라 실제 DB에 반영되었는지 확인
        Article article = blogRepository.findById(savedArticle.getId()).get();

        // DB에 저장된 제목이 새로운 제목으로 변경되었는지 확인
        assertThat(article.getTitle()).isEqualTo(newTitle);
        // DB에 저장된 내용이 새로운 내용으로 변경되었는지 확인
        assertThat(article.getContent()).isEqualTo(newContent);
    }

}