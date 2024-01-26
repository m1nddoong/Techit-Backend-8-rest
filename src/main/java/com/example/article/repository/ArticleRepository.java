package com.example.article.repository;

import com.example.article.entity.Article;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArticleRepository extends JpaRepository<Article, Long> {
    // ID 순서대로 큰 최상위 20개
    List<Article> findTop20ByOrderByIdDesc();

    // ID 순서대로 큰, 특정 ID 이후의 게시글 상위 20개
    // 특정 ID 이전 > ID가 특정 ID 보다 작은
    List<Article> findTop20ByIdLessThanOrderByIdDesc(Long id);

}
