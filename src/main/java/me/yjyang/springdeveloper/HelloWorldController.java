package me.yjyang.springdeveloper;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloWorldController {

    // hello() 메서드 호출
    @GetMapping("/hello")
    public String hello() {
        return "Hello World";
    }

    // http://localhost:8080/test > "Hello Everyone!" 출력
    /*
    @GetMapping("/test")
    public String test() {
        return "Hello Everyone!";
    }
    */



    @DeleteMapping("/test")
    public String postTest() {
        return "Delete Test response";
    }

    @PutMapping("/test")
    public String deleteTest() {
        return "Put Test Response!";
    }
}