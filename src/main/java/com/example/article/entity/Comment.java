package com.example.article.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/*
CREATE TABLE comment(
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    content TEXT,
    writer TEXT,
    article_id INTEGER
)
 */

@Entity
@Getter
@NoArgsConstructor
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter
    private String content;
    @Setter
    private String writer;

    @Setter
    @ManyToOne
    private Article article;
    // 그냥 엔티티 타입을 속성으로 넣을 수 없으므로
    // ManyToOne 써주기

    public Comment(String content, String writer, Article article) {
        this.content = content;
        this.writer = writer;
        this.article = article;

    }
}
