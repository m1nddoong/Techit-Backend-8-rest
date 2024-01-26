package com.example.article.controller;

import com.example.article.dto.ArticleDto;
import com.example.article.service.ArticleService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/*
CRUD를 위한 URL
POST /articles -> create()
GET /articles -> readAll()
GET /articles/{id} -> read()
PUT /articles/{id} -> update()
DELETE /articles/{id} -> delete()
 */
@Slf4j
@RestController // 뷰가 아니라 모든 메서드의 반환형에 ResponseBody 애너테이션 (데이터 그 자체)
@RequestMapping("/articles")// 앞쪽 부분 url 매핑
@RequiredArgsConstructor
public class ArticleController {
    private final ArticleService service;

    @PostMapping
    public ArticleDto create(
            @RequestBody
            ArticleDto dto
    ){
        // dto 를 있는 그대로 서비스에 전달한다.
        // 그 결과 생성된 새로운 dto 형태를 반환
        return service.create(dto);
    }

    @GetMapping
    public List<ArticleDto> readAll(){
        return service.readAll();
    }

//    @GetMapping("/{id}")
//    public ArticleDto read(
//            @PathVariable
//            Long id
//    ){
//        return service.readOne(id);
//    }


    // null 일떄, null 이 아닐때
    @GetMapping("/{id}")
    public ResponseEntity<ArticleDto> read(
            @PathVariable("id")
            Long id
    ) {
        ArticleDto responseBody = service.readOne(id);
        if (responseBody == null) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(responseBody);
        }
    }




    @PutMapping("/{id}")
    public ArticleDto update(
            @PathVariable("id")
            Long id,
            @RequestBody
            ArticleDto dto
    ) {
        return service.update(id, dto);
    }


    @DeleteMapping("/{id}")
    public void delete(
            @PathVariable("id")
            Long id
    ){
        service.delete(id);

    }

}
