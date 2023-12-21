package org.example.article;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

// ArticleDao - Data Access Object

// 오롯이 데이터베이스를 다루는 역할이다. (데이터 베이스와의 소통)
public class ArticleDao {
    // 어떤 데이터베이스와의 연결을 나타내는 connection
    private final Connection connection;

    public ArticleDao(Connection connection) {
        this.connection = connection;
    }

    // 제목과 내용을 전달받으면
    // 데이터베이스에 새로운 게시글(article) 행을 만들고,
    // 성공 여부에 따라서 boolean을 반환한다.
    public boolean create(String title, String content) {
        /*
        -- article 테이블에 새로운 데이터를 넣어주는 SQL
        INSERT INTO article(title, content) VALUES ('title', 'content')
        -- JDBC를 쓴다는 것은 SQL을 직접 작성하는 건데, 어떤식으로 DB에 전송할까를 생각해야함.
        -- 그 전송 방법은 ??
         */

        System.out.println("받은 제목: " + title);
        System.out.println("받은 내용: " + content);

        // Statement : JDBC에 전달하는 SLQ문을 전달하는 역할
        // Statement 작성 후 option+Enter -> Surround try-catch
        try (Statement statement = connection.createStatement()) {
            // try - with - resources -> 위 트라이 구문이 끝날떄 자동으로 닫아준다 (메모리 절약을 위해서인가?)
            // 작성하고 싶은 SQL문을 문자열의 형태로 저장
            // String insertSql = "INSERT INTO article(title, content) VALUES ('title', 'content')";
            // 위에서 변하는 부분은 VALUE 부분 -> Stirng.format 활용
            String insertSql = String.format("INSERT INTO article(title, content) VALUES ('%s', '%s');", title, content);
            System.out.println("준비된 SQL: " + insertSql);
            // 데이터의 구조를 바꿀떄 사용
            System.out.println(statement.executeUpdate(insertSql));
            return true;
        } catch (SQLException e) {
            throw new RuntimeException(e);

        }
    }




    // 내가 데이터를 받아와서 사용하고싶다면 Service

    // 어쩃든 다음으로

    // 데이터베이스에 저장된 모든 글 정보를 list 형태로 반환한다.
    public List<ArticleDTO> readALl() {
        /*
        -- article 테이블의 모든 데이터를 반환하는 SQL문
        SELECT * FROM article;
         */
        System.out.println("모든 데이터 조회하기");
        // insert 할때와 마찬가지로 statement 사용
        try (Statement statement = this.connection.createStatement()) {
            String selectSql = "SELECT * FROM article";
            // 조회(select)시 사용하는 메서드 -> 결과는 ResultSet이다.
            // ResultSet은 마치 테이블의 열단위로 하나씩 행을 내려오면서 살펴보는 역할을 한다.
            ResultSet resultSet = statement.executeQuery(selectSql);


            // 여러개의 데이터를 돌려줄 예정이니
            List<ArticleDTO> articles = new ArrayList<>();
            // ResultSet은 .next() 메서드를 통해서 !다음 줄!을 확인하며,
            // 만약 다음 줄이 없으면 .next()의 결과는 false 이다.
            while (resultSet.next()) {
                // 이제는 idx가 아니라, id 즉 column 명들로 접근을 하는 것이다.
                // System.out.println(resultSet.getInt("id"));
                // System.out.println(resultSet.getString("title"));
                // System.out.println(resultSet.getString("content"));

                // resultSet의 데이터를 바탕으로 새로운 ArticleDTO 객체를 만들자
                ArticleDTO article = new ArticleDTO(
                        resultSet.getInt("id"),
                        resultSet.getString("title"),
                        resultSet.getString("content")
                );
                articles.add(article);
            }
            return articles;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }


    // PreparedSatatement
    // 조회하고 싶은 게시글을 받고, 그 게시글을 객체의 형태로 반환하는 메서드
    public ArticleDTO readOne(int id) {
        System.out.println("단일 데이터 조회하기");
        /*
        -- 특정 id인 article 행 조회
        SELECT * FROM article WHERE id = "%d";
         */
        // PreparedStatement는 SQL을 먼저 준비 -> %d 를 ? 로 변경 (?는 article의 id)
        // PreparedStatement는 내부에서 SQL Injection 대비가 되어 있다.
        String selectSql = "SELECT * FROM article WHERE id = ?;";
        try (PreparedStatement statement = connection.prepareStatement(selectSql)) {
            // ?가 총 몇개있는지를 확인해서, 그 물음표에다 내가 넣고싶은 값을 넣을떈 setInt, setLong 등의 메서드가 있다.
            // 첫번쨰이다(1부터 시작) 라고 쓰고, 넣고싶은 값을 id로 넣어준다
            statement.setInt(1, id);
            // 결과는 동일하게 ResultSet으로 받는다.
            ResultSet resultSet = statement.executeQuery();
            // ResultSet을 사용한다.
            if (resultSet.next()) {
                return new ArticleDTO (
                    resultSet.getInt("id"),
                    resultSet.getString("title"),
                    resultSet.getString("content")
                );
            }
            // SQL 문을 전달하는 것이 아니라, 이미 sql문을 먼저 받았기 떔누에
         } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        // 지금은 null이지만 ... 나아가면 Optional을 사용가능.
        return null;
    }

    //

}
























