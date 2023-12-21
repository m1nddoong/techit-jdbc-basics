package org.example;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class MemoMain {
    public static void main(String[] args) {
        System.out.println("시작");
        // 1. 사용자는 반복해서 명령을 입력한다.
        // 1-1. 사용자가 1을 입력하면, 메모를 작성하는 메뉴를 선택한 것.
        // 1-2. 사용자가 2를 입력하면, 작성된 메모를 모두 확인하는 메뉴를 선택한 것.
        // 1-3. 사용자가 q를 입력하면, 프로그램 종료 (main 메서드의 끝에 도달하는 것)

        // 입력을 받을 준비를 한다. Scanner를 사용한다.
        Scanner scanner = new Scanner(System.in);

        // 메모는 문자열로 저장할 것인데, 사용자가 만든 메모의 갯수가
        // 일정하지 않을것을 가정한다.
        List<String> memos = new ArrayList<>();

        // 사용자가 q를 입력할때 까지 반복한다.
        while (true) {
            // 사용자에게 선택지를 제시하는 출력을 한다.
            System.out.println("1. 메모 추가");
            System.out.println("2. 메모 보기");
            System.out.println("q. 종료");
            // 명령을 입력받는다.
            String command = scanner.nextLine();
            // 사용자가 q를 입력했는지 확인한다.
            if (command.equals("q")) break;
                // 1-1. 사용자가 1을 입력하면, 메모를 작성하는 메뉴를 선택한 것.
            else if (command.equals("1")) {
                System.out.print("메모를 입력하세요 : ");
                // 사용자의 입력을 다시 받고,
                String newMemo = scanner.nextLine();
                // memos에 저장한다.
                memos.add(newMemo);
            }
            // 1-2. 사용자가 2를 입력하면, 작성된 메모를 모두 확인하는 메뉴를 선택한 것.
            else if (command.equals("2")) {
                // memos가 가진 문자열을 다 순회하며 확인하고, 출력한다.
                for (String memo : memos) {
                    System.out.println(memo);
                }
            }
            System.out.println();
        }
        System.out.println("안녕히가세요~");
    }
}