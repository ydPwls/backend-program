package me.yjyang.springdeveloper;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@SpringBootTest
@AutoConfigureMockMvc

public class QuizControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @DisplayName("GET /quiz?code=1 → 응답 코드는 201, 응답 본문은 Create!를 리턴함")
    @Test
    void getQuiz1() throws Exception {
        // 준비 (given)
        final String url = "/quiz";

        // 실행 (when)
        final ResultActions result = mockMvc.perform(
                get(url).param("code", "1")
        ); // Get /quiz?code=1

        // 검증 (then)
        result
                .andExpect(status().isCreated())
                .andExpect(content().string("Created!"));

        // 응답 전체 출력
        System.out.println("\n---------- 응답 전체 출력 ----------");
        result.andDo(print());
    }

    @DisplayName("GET /quiz?code=2 → 응답 코드는 400, 응답 본문은 Bad Request!")
    @Test
    public void getQuiz2() throws Exception {
        // given
        final String url = "/quiz";

        // when
        final ResultActions result = mockMvc.perform(get(url)
                .param("code", "2")
        );

        // then
        result
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Bad Request!"));

        // 응답 전체 출력
        System.out.println("\n---------- 응답 전체 출력 ----------");
        result.andDo(print());
    }


    @DisplayName("POST /quiz 요청, 요청 바디에 {'value': 1} → 응답 코드는 403, 응답 본문은 Forbidden!")
    @Test
    void postQuiz1() throws Exception {

        // 준비 (given)
        final String url = "/quiz";

        // 실행 (when)
        final ResultActions result = mockMvc.perform(
                post(url)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(new Code(1))) // Code 객체를 JSON 문자열로 만들어줌
        );

        // 검증 (then)
        result
                .andExpect(status().isForbidden())
                .andExpect(content().string("Forbidden!"));

        // 응답 전체 출력
        System.out.println("\n---------- 응답 전체 출력 ----------");
        result.andDo(print());
    }

    @DisplayName("POST /quiz 요청, 요청 바디에 {'value': 2} → 응답 코드는 200, 응답 본문에 OK!")
    @Test
    void postQuiz2() throws Exception {
        // 준비 (given)
        final String url = "/quiz";

        // 실행 (then)
        final ResultActions result = mockMvc.perform(
                post(url)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(new Code(2)))
        );

        // 검증 (then)
        result
                .andExpect(status().isOk())
                .andExpect(content().string("OK!"));

        // 응답 전체 출력
        System.out.println("\n---------- 응답 전체 출력 ----------");
        result.andDo(print());
    }

}
