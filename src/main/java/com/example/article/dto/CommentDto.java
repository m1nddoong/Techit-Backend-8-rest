package com.example.article.dto;


import com.example.article.entity.Comment;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class CommentDto {
    private Long id;
    @Setter
    private String content;
    @Setter
    private String writer;

    // static factory method
    public static CommentDto fromEntity(Comment entity) {
        return new CommentDto(
                entity.getId(),
                entity.getContent(),
                entity.getWriter()
        );
    }
}


