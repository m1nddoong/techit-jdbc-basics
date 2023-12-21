package org.example.article;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

// ArticleService와 역할을 같은데
// 실제로 어떤 방식으로 동작하는지는 다르다.

// ArticleService가 데이터를 속성에 저장한다면,
// ArticleDBService는 데이터베이스를 사용한다.
public class ArticleDBService {
    // 사용자의 입력을 받는 스캐
    private final Scanner scanner;

    private final ArticleDao dao;

    public ArticleDBService(Scanner scanner) {
        this.scanner = scanner;
        try {
            // 데이터베이스에 연결을 해서
            Connection connection = DriverManager.getConnection("jdbc:sqlite:db.sqlite");
            // DAO로 넘겨준다.
            this.dao = new ArticleDao(connection);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    // 1, 게시글을 작성할 때 필요한 데이터를 일단 입력받자.
    public void writeArticle() {
        // 제목을 입력받는다.
        System.out.print("제목을 입력하세요: ");
        String newTitle = this.scanner.nextLine();
        // 본문을 입력받는다.
        System.out.print("본문을 입력하세요: ");
        String newContent = this.scanner.nextLine();
        // DAO에게 데이터를 전달해 데이터베이스에 작성을 요청한다.
        this.dao.create(newTitle, newContent);
    }

    // 2. 작성된 게시글들을 출력할건데, 아직은 아무것도 안함

    public void showAllTitles() {
        // this.dao.readALl();
        List<ArticleDTO> articles = this.dao.readALl();
        // 데이터 베이스에서 회수한 데이터를 하나씩 출력한다.
        for (ArticleDTO artiicle : articles) {
            System.out.println(String.format("%d. %s", artiicle.getId(), artiicle.getTitle()));
        }

    }

    // 3. 하나의 게시글의 정보를 출력하는 메서드
    public void readArticle() {
        System.out.println("글 번호를 선택하세요: ");
        String idxString = this.scanner.nextLine();
        int idx = Integer.parseInt(idxString);

        // readOne을 작성하였으니 아래 추가
        ArticleDTO article = this.dao.readOne(idx);
        System.out.println();
        System.out.println(String.format("제목: %s", article.getTitle()));
        System.out.println(String.format("내용: %s", article.getContent()));
    }


}
