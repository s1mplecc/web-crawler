package xyz.s1mple.crawler.gateways.controllers;

import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import xyz.s1mple.crawler.application.NovelService;
import xyz.s1mple.crawler.domain.novel.Novel;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("/novel")
public class NovelController {
    @Resource
    private NovelService novelService;

    @GetMapping
    public void download(@RequestParam String index, HttpServletResponse response) throws InterruptedException, ExecutionException, IOException {
        Novel novel = novelService.crawl(index);

        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/octet-stream");
        response.setHeader("Content-Disposition", "attachment; filename=" + URLEncoder.encode(novel.title() + ".txt", "UTF-8"));
        IOUtils.copy(new ByteArrayInputStream(novel.contents().getBytes()), response.getOutputStream());
        response.flushBuffer();
    }
}
