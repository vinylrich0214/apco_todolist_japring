package com.example.apco.repository;

import com.example.apco.model.Todo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

//이 인터페이스 하나가 DB CRUD의 대부분을 대신해준다
public interface TodoRepository extends JpaRepository<Todo,Long> {
    List<Todo> findByTextContaining(String text); // text안에 특정 글자가 포함된 text 찾기
    List<Todo> findAllByOrderByDueDateAsc();
    List<Todo> findByTextContainingOrderByDueDateAsc(
            String text
    );
    //findByTextContaining -> text에 검색어가 포함된 todo
    //OrderByDueDateAsc -> 오름차순으로 정렬 dueDate를
}
// 이 주석은 테스트용 주석입니다