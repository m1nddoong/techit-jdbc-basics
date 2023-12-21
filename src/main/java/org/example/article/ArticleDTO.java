package org.example.article;

// DTO - Data Transfer Object

// Article을 표현하기 위한 클래스
// Article에 괸힌 데이터는 ResultSet으로 받아올 수 있음

public class ArticleDTO {
    private Integer id;
    private String title;

    private String content;

    public ArticleDTO(Integer id, String title, String content) {
        this.id = id;
        this.title = title;
        this.content = content;
    }

    public Integer getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }
}
