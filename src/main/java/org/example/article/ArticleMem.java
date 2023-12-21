package org.example.article;

// POJO - Plain Old Java Object
// 아래의 클래스와 같이 평볌하게 데이터를 담고있는 자바 객체를 의미함

public class ArticleMem {
    // 이 클래스는 특수한 기능을 한다고 보기 어렵다. 단지 데이터를 옮겨주는 것 뿐
    private String title;
    private String content;

    // 기본 생성자
    public ArticleMem() {

    }

    // cmd + N -> constructor 로 매개변수가 있는 생성자를 만들자
    public ArticleMem(String title, String content) {
        this.title = title;
        this.content = content;
    }

    // private 한 속성들 접근

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
