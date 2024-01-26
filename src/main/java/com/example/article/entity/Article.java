package com.example.article.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Lob;
import jakarta.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

/*
CREATE TABLE article(
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    title TEXT,
    content TEXT,
    writer TEXT
)
 */
// 단, @Data는 사용하지 않는다.

@Getter
@Entity
@NoArgsConstructor // 수동 생성자를 추가하면, Default Constructor 가 사라지게 됨
        // 엔티티의 조건이 아무 매개변수가 없는 생성자가 존재해야한다 이기 때문에?
public class Article {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter
    private String title;

    @Setter
    @Lob
    private String content;

    @Setter
    private String writer;

    @OneToMany
    private List<Comment> comments = new ArrayList<>();

    public Article(String title, String content, String writer) {
        this.title = title;
        this.content = content;
        this.writer = writer;
    }
}



