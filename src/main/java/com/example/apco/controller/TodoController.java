package com.example.apco.controller;

import com.example.apco.model.Todo;
import com.example.apco.repository.TodoRepository;
import com.example.apco.service.TodoService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
//JPA, H2
@Controller
public class TodoController {
    private final TodoService todoService;

    public TodoController(TodoService todoService) {
        this.todoService = todoService;
    }//Bean

    //Read
    @GetMapping("/")
    public String home(Model model){
        //todos라는 변수 자체를 thymeleaf에 넘겨줘서
        model.addAttribute("todos",todoService.findAll());
        // SELECT * FROM TODO
        return "index";
    }
    //Create
    @PostMapping("/add")
    public String addTodo(@RequestParam String text,
                          @RequestParam String priority,
                          @RequestParam
                          @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dueDate){

    //할 일 html에서 데이터 받아오기
        //todos.add
        todoService.addTodo(text,priority,dueDate);
        // create table todo todo ~~ bigint id varchar text boolean done
        return "redirect:/";
    }
    //Delete
    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id){
    //todos.remove(id)
        todoService.deleteTodo(id);
        return "redirect:/";
    }
    //update
    @GetMapping("/toggle/{id}")
    public String toggle(@PathVariable Long id){
        todoService.toggleTodo(id);
        return "redirect:/";
    }
    //UPDATE(U)
    @GetMapping("/edit/{id}")
    public String editForm(@PathVariable Long id,Model model){
        //localhost:8080/edit/1
        Todo todo=todoService.findById(id);
        model.addAttribute("todo",todo);
        return "edit";
    }
    @PostMapping("/edit/{id}")
    public String edit(@PathVariable Long id,
                       @RequestParam String text,
                       @RequestParam String priority,
                       @RequestParam @DateTimeFormat(iso=DateTimeFormat.ISO.DATE)LocalDate dueDate) {
        todoService.updateTodo(id,text,priority,dueDate);
        return "redirect:/";
    }

    @GetMapping("/search")
    public String search(@RequestParam String keyword,Model model){
        String sorttype = "dueDateAsc";
        model.addAttribute("todos",todoService.searchAndSort(keyword,sorttype));
        model.addAttribute("keyword",keyword);
        return "index";
    }
    @GetMapping("/sort")
    public String sort(@RequestParam(required = false,defaultValue = "")  String keyword
                        ,@RequestParam(defaultValue = "dueDateAsc") String sortType
            , Model model){
        model.addAttribute(
                "todos",
                todoService.searchAndSort(keyword,sortType)
        );
        model.addAttribute("keyword",keyword);
        model.addAttribute("sortType",sortType);
        return "index";
    }
    // 8가지 상황 테스트
    //1. 검색 없이 정렬
    //2. '숙제' 검색 후 정렬
    //3. 빈칸,공백 검색
    //4. 결과 없는 검색
    //5. 정렬 후 keyword 유지
    //6. 추가 정렬 필터 결과
    //7. 수정,완,삭
    //8. 오류 발생하면 url 기록

    // 중요도(priority), 마감일(duetime)
    // Todo entity에 priority,dueDate 필드 추가하기
    // html 입력 폼에서 중요도와 마감일 받기
    // 컨트롤러, 서비스, 레포 등이 바뀐다
    // MYSQL에 실제 컬럼과 데이터가 반영되는지 확인
    // 요구사항이 바뀌면 entity, 화면, controller, Sevice, DB가 함께 바뀐다

    //오늘의 전체 흐름
    // entity 수정 -> service 수정 -> controller -> html -> 실행 테스트 -> mysql 확인
}
