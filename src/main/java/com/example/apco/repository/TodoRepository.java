package com.example.apco.repository;

import com.example.apco.model.Todo;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

//이 인터페이스 하나가 DB CRUD의 대부분을 대신해준다
public interface TodoRepository extends JpaRepository<Todo,Long> {
    List<Todo> findByTextContaining(String keyword, Sort sort);
}
// 이 주석은 테스트용 주석입니다