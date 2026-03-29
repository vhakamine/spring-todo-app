package com.example.todo.controller;

import com.example.todo.model.Todo;
import com.example.todo.repository.TodoRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class TodoController {

    private final TodoRepository todoRepository;

    public TodoController(TodoRepository todoRepository) {
        this.todoRepository = todoRepository;
    }

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("todos", todoRepository.findAll());
        return "index";
    }

    @PostMapping("/add")
    public String addTodo(@RequestParam String title) {
        if (title != null && !title.trim().isEmpty()) {
            Todo todo = new Todo();
            todo.setTitle(title.trim());
            todo.setCompleted(false);
            todoRepository.save(todo);
        }
        return "redirect:/";
    }

    @PostMapping("/toggle/{id}")
    public String toggleTodo(@PathVariable Long id) {
        todoRepository.findById(id).ifPresent(todo -> {
            todo.setCompleted(!todo.isCompleted());
            todoRepository.save(todo);
        });
        return "redirect:/";
    }

    @PostMapping("/delete/{id}")
    public String deleteTodo(@PathVariable Long id) {
        todoRepository.deleteById(id);
        return "redirect:/";
    }
}
