package xyz.s1mple.crawler;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AController {
    @GetMapping("/user")
    public String a() {
        return "user";
    }
}
