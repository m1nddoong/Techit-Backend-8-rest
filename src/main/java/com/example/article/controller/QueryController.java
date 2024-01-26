package com.example.article.controller;

import com.example.article.dto.ArticleDto;
import com.example.article.service.ArticleService;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class QueryController {
    private final ArticleService service;

    // GET /query-exmaple?query=keyword&limit=20 HTTP/1.1
    @GetMapping("/query-example") // 이 경로에 요청을 받을 예정
    public String queryParams(
            @RequestParam("query")
            String query,
            // 받을 자료형 선택 가능
            // 만약 변환 불가일 경우, Bad Request (400)
            @RequestParam("limit")
            Integer limit,
            // 반드시 포함해야 하는지 아닌지를 required로 정의 가능
            @RequestParam(value = "notreq", required = false)
            String notRequired,
            // 기본값 설정을 원한다면 defaultValue
            @RequestParam(value = "default", defaultValue = "hello")
            String defaultVal
    ) {
        log.info("query : " + query);
        log.info("limit : " + limit);
        log.info("notreq : " + notRequired);
        log.info("default : " + defaultVal);
        return "done";
    }

    // GET /query-page?page=1&perpage=25
    @GetMapping("/query-page")
    public Object queryPage(
            // defaultValue 는 문자열로 전달해줘야한다.
            @RequestParam(value = "page", defaultValue = "1")
            Integer page,
            @RequestParam(value = "perpage", defaultValue = "25")
            Integer perPage
    ) {
        log.info("page : " + page);
        log.info("perpage : " + perPage);
        return service.readArticlePaged(page, perPage);
    }
}
