package me.yjyang.springdeveloper.controller;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller

public class ExampleController {
    @GetMapping("/thymeleaf/example")
    @ResponseBody
    public String thymeleaf() {
        return "example"; //
    }

    @GetMapping("/thymeleaf/example2")
    public String thymeleaf2(Model model) {
        Person person = new Person();
        person.setName("홍길동");
        person.setAge(23);
        model.addAttribute("person", person);
        return "example"; // 문자열 return하면 resources/templates에서 example.HTML 파일 이름 (확장자 제외) 찾음
    }
}

@Getter
@Setter
class Person {
    private String name;
    private int age;
}