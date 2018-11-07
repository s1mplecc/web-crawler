package xyz.s1mple.crawler.gateways.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import xyz.s1mple.crawler.application.NovelService;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.concurrent.ExecutionException;

@RestController
public class NovelController {
    @Resource
    private NovelService novelService;

    @GetMapping("/novel")
    public String download(@RequestParam String index) throws InterruptedException, ExecutionException, IOException {
        return novelService.crawl(index);
    }
}
