package com.example.apco.service;

import com.example.apco.model.Todo;
import com.example.apco.repository.TodoRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

/* 핵심
    Controller의 역할 ? : 요청을 받는다, 타임리프에 화면을 돌려준다
    Service의 역할 ? 실제 기능/비즈니스 로직을 처리한다.
    Repository의 역할 ? : db와 대화한다
* */
@Slf4j
@Service
public class TodoService {
    private final TodoRepository todoRepository;

    public TodoService(TodoRepository todoRepository){
        this.todoRepository = todoRepository;
    }
    public List<Todo> findAll(){
        return todoRepository.findAll();
    }
    public void addTodo(String text,String priority, LocalDate dueDate){
        todoRepository.save(new Todo(text,priority,dueDate));
    }
    public void deleteTodo(Long id){
        todoRepository.deleteById(id);
    }
    public Todo findById(Long id){
        return todoRepository.findById(id)
                .orElseThrow(()-> new IllegalArgumentException("해당 TODO가 없습니다"));
    }
    public void toggleTodo(Long id){
        Todo todo = findById(id);
        todo.toggle();
        todoRepository.save(todo);
    }
    public void updateTodo(Long id, String text,String priority, LocalDate dueDate){
        Todo todo = findById(id);
        if (text.isEmpty()){
            log.warn("수정할 todo의 text가 비었습니다");
            return;
        }else {
            todo.setText(text);
            todo.setPriority(priority);
            todo.setDueDate(dueDate);
        }
        todoRepository.save(todo);
    }
    public List<Todo> search(String text){
        if (text==null || text.isBlank()){
            return todoRepository.findAll();
        }
        return todoRepository.findByTextContaining(text);
    }
    public List<Todo> findAllSorted(){
        return todoRepository.findAllByOrderByDueDateAsc();
    }

}
