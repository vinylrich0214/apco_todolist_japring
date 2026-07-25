package com.example.apco.repository;

import com.example.apco.model.Todo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

//이 인터페이스 하나가 DB CRUD의 대부분을 대신해준다
public interface TodoRepository extends JpaRepository<Todo,Long> {
    List<Todo> findByTextContaining(String text); // text안에 특정 글자가 포함된 text 찾기
    List<Todo> findAllByOrderByDueDateAsc();
}
