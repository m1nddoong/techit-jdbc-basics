package org.example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        // 1. 어떤 데이터베이스에 연결할지를 String 으로 작성
        String connectionString = "jdbc:sqlite:db.sqlite";

        try (Connection connection = DriverManager.getConnection(connectionString)) {
            System.out.println("Connection Success!");

            // 3. 데이터베이스 연결 객체로부터 Statement 객체를 받는다.
            Statement statement = connection.createStatement();

            // 4. execute를 이용해 간단한 SQL 문을 사용한다.
            statement.execute("""
            DROP TABLE IF EXISTS user;
            """);
            statement.execute("""
            CREATE TABLE user(
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                username TEXT,
                password TEXT,
                first_name TEXT,
                last_name TEXT,
                email TEXT
            );
            """);
            // 서로다른 사용자 계정 3개 입력
            statement.execute("""
            INSERT INTO user(username, password, first_name, last_name, email)
            VALUES ('alex', 'aaa', 'Alex', 'Rad', 'a.rod@gmail.com');
            """);
            statement.execute("""
            INSERT INTO user(username, password, first_name, last_name, email)
            VALUES ('brad', 'bbb', 'Brad', 'Snyder', 'brad@gmail.com');
            """);
            statement.execute("""
            INSERT INTO user(username, password, first_name, last_name, email)
            VALUES ('chad', 'ccc', 'Chad', 'Bradford', 'moneyball@gmail.com');
            """);

            String insertSql = """
            INSERT INTO user(username, password, first_name, last_name, email)
            VALUES ('%s', '%s', 'Chad', 'Bradford', 'moneyball@gmail.com');
            """;

//            Scanner scanner = new Scanner(System.in);
//            String username = scanner.nextLine();
//            String password = scanner.nextLine();
//            insertSql = String.format(insertSql, username, password);
//            statement.execute(insertSql);

            // Select 해보자.
            String selectSql = """
            SELECT * FROM user WHERE id = 4;     
            """;
            // 조회하는 쿼리는 ResultSet으로 데이터를 받는다.
            // ResultSet 은 결과 테이블을 살펴볼 수 있게 도와주는 인터페이스
            ResultSet resultSet = statement.executeQuery(selectSql);
            if (resultSet.next()) {
                System.out.println(resultSet.getString("username"));
                System.out.println(resultSet.getString("first_name"));
                System.out.println(resultSet.getString("email"));
            } else {
                System.out.println("Could not find");
            }

            // 다음 줄이 없으면 next()는 false를 반환
            selectSql = """
            SELECT * FROM user;
            """;
            resultSet = statement.executeQuery(selectSql);
            while (resultSet.next()) {
                System.out.println(resultSet.getString("username"));
                /* System.out.println(resultSet.getString("first_name"));
                System.out.println(resultSet.getString("email")); */
            }

            String updateSql = """       
                UPDATE user
                SET first_name = 'Alexander'
                WHERE id = 1;
            """;

            // executeUpdate는 내가 실행한 SQL문의 결과로 바뀐 줄의 갯수를 반환한다.
            int rows = statement.executeUpdate(updateSql);
            System.out.println("rows affected: " + rows);

        } catch (SQLException e) {
            System.out.println(e.getErrorCode());
            System.out.println(e.getMessage());
            throw new RuntimeException(e);
        }
    }
}