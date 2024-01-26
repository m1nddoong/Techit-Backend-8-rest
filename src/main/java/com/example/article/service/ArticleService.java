package com.example.article.service;

import com.example.article.dto.ArticleDto;
import com.example.article.entity.Article;
import com.example.article.repository.ArticleRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.server.ResponseStatusException;


@Slf4j
@Service // 비즈니스 로직을 담당하는 클래스
@RequiredArgsConstructor // final 붙은 것들 DI
public class ArticleService {
    private final ArticleRepository repository;

    // CREATE
    // 사용자가 ArticleDTO 를 전달하면
    // 그 안에 있는 내용을 바탕으로 새로운 ArticleDto 를 만들어 줄 것
    public ArticleDto create(ArticleDto dto) {
        Article newArticle = new Article(
                dto.getTitle(),
                dto.getContent(),
                dto.getWriter()
        );
        // JPA 권장 사항, 새로운 article 로 교체 해주자! (코드 상 안전함)
        // 변수를 추가하지 않고 newArticle 을 활용
        // newArticle = repository.save(newArticle);
        return ArticleDto.fromEntity(repository.save(newArticle));
    }

    // READ ALL
    public List<ArticleDto> readAll() {
        List<ArticleDto> articleList = new ArrayList<>();

        // 여기에 모든 게시글을 리스트로 정리해서 전달 - findAll? (id를 포함시켜서 만들어 주는 게 맞다)
        List<Article> articles = repository.findAll();
        for (Article entity : articles) {
            articleList.add(ArticleDto.fromEntity(entity));
        }
        return articleList;
    }

    // READ ONE
    public ArticleDto readOne(Long id) {
        Optional<Article> optionalArticle = repository.findById(id);
        // 해당하는 Article이 있었다.
        if (optionalArticle.isPresent()) {
            Article article = optionalArticle.get();
            return ArticleDto.fromEntity(article);
            // return ArticleDto.fromEntity(optionalArticle.get());
        }
        // 없으면 특정 예외를 발생시킨다.
        throw new ResponseStatusException(HttpStatus.NOT_FOUND);
    }

    // UPDATE
    public ArticleDto update(Long id, ArticleDto dto) {
        Optional<Article> optionalArticle = repository.findById(id);
        // 해당하는 Article 이 있었다.
        if (optionalArticle.isPresent()) {
            // optional 객체가 article 을 들고 있기 때문에 get 으로 가져오기
            Article targetEntity = optionalArticle.get();
            targetEntity.setTitle(dto.getTitle());
            targetEntity.setContent(dto.getContent());
            targetEntity.setWriter(dto.getWriter());
            // 수정된 entity 를 save 를 한 뒤 새로운 ArticleDto 로 반환
            return ArticleDto.fromEntity(repository.save(targetEntity));
        }
        // 없으면 예외를 발생시킨다.
        throw new ResponseStatusException(HttpStatus.NOT_FOUND);
    }


    //DELETE
    public void delete(Long id) {
        // id 가 존재하는지
        if (repository.existsById(id)) {
            repository.deleteById(id);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }


    // GET /query-page?page=1&perpage=25
    @GetMapping("/query-page")
    public Object queryPage(
            // defaultValue 는 문자열로 전달해줘야한다.
            @RequestParam(value = "page", defaultValue = "1")
            Integer page,
            @RequestParam(value = "perpage", defaultValue = "25")
            Integer perpage
    ) {
        log.info("page : " + page);
        log.info("perpage : " + perpage);
        return null;
    }

    // JPA Query Method
    // 페이지 단위로 구분하기 힘들다.
    // 마지막으로 확인한 게시글의 ID를 바탕으로 조회해야 한다는 단점
    public List<ArticleDto> readTop20() {
        // 반환을 위한 리스트
        List<ArticleDto> articleDtoList = new ArrayList<>();
        // repo에서 최상단 20개의 Article 을 가져온다
        List<Article> articleList = repository.findTop20ByOrderByIdDesc();
        for (Article entity : articleList) {
            articleDtoList.add(ArticleDto.fromEntity(entity));
        }

        return articleDtoList;
    }

    public List<ArticleDto> readArticlePagedList() {
        // PagingAndSortingRepository 의 findAll 의 인자로 전달함으로써
        // 조회하고 싶은 페이지와, 각 페이지 별 갯수를 조회해서
        // 조회하는 것을 도와주는 Pageable 객체
        Pageable pageable = PageRequest.of(0, 20); // 패아지 번호, 페이지당 게시물 갯수
        // Page<Article> : pageable을 전달해서 받은 결과를 정리해둔 객체
        // findAll : 인자를 넣어도 호출이 가능한 자바 메서드 특징은? 메서드 오버로딩
        Page<Article> articlePage = repository.findAll(pageable);
        // 결과 반환 준비
        List<ArticleDto> articleDtoList = new ArrayList<>();
        for (Article entity : articlePage) {
            articleDtoList.add(ArticleDto.fromEntity(entity));
        }
        return articleDtoList;
    }

    // Pageable 을 사용해서 Page<Entity>를 Page<Dto>로 변환 후
    // 모든 정보 활용
    public Page<ArticleDto> readArticlePaged(
            Integer pageNum,
            Integer pageSize
    ) {
        Pageable pageable = PageRequest.of(
                pageNum, pageSize, Sort.by("id").descending()); // 반대방향 정렬
        Page<Article> articlePage = repository.findAll(pageable);
        // map() 메서드 : Page의 각 데이터(Entity)를 인자로, 특정 메서드를 실행한 후
        // 해당 메서드 실행 결과를 모아서 새로운 Page 객체를,
        // 만약 반환형이 바뀐다면 타입을 바꿔서 반환한다.
        Page<ArticleDto> articleDtoPage
                // = articlePage.map(entity -> ArticleDto.fromEntity(entity));
                = articlePage.map(ArticleDto::fromEntity); // 축약형
        return articleDtoPage;
    }

}
