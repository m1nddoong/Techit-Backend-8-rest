package com.example.article.dto;

import com.example.article.entity.Article;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ArticleDto {
    private Long id;
    @Setter
    private String title;
    @Setter
    private String content;
    @Setter
    private String writer;


    // 특정 재료가 있을때 ArticleDto를 만드는 방법을
    // 클래스에 기록해서 코드 중복을 줄이고 사용성을 높이는
    // Static Factory Method Pattern (정적 메서드 - 클래스에서 바로 호출 가능)
    public static ArticleDto fromEntity(Article entity) { // 재료는 entity 이다
        return new ArticleDto(
                entity.getId(),
                entity.getTitle(),
                entity.getContent(),
                entity.getWriter()
        );
    }
}


