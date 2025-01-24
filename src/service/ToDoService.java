package service;

import excpetions.NotFound;
import excpetions.ToDoNotFound;
import model.Todo;
import repository.CommonRepository;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class ToDoService {

    private final CommonRepository commonRepository;


    public ToDoService(CommonRepository commonRepository) {
        this.commonRepository = commonRepository;
    }


    public Todo findTodoById(UUID id) {
        List<Todo> todos = commonRepository.getTodos();
        Todo foundTodo = null;
        if (!todos.isEmpty()) {
            for (Todo todo : todos) {
                if (todo.getId() == id) {
                    foundTodo = todo;
                } else {
                    throw new ToDoNotFound("There is no todo matching this id: ");
                }
            }
            return foundTodo;
        } else {
            throw new NotFound("There is no information in the database:");
        }
    }

    public Todo addTodo(Todo newTodo) {
        commonRepository.getTodos().add(newTodo);
        System.out.println(newTodo);
        return newTodo;
    }


    public String removeTodo(UUID id) {
        List<Todo> todos = commonRepository.getTodos();
        if (!todos.isEmpty()) {
            todos
                    .stream()
                    .filter(todo -> Objects.equals(todo.getId(), id))
                    .findFirst().ifPresentOrElse(todos::remove, () -> {
                        throw new ToDoNotFound("User with id " + id + " not found.");
                    });
        } else {
            throw new ToDoNotFound("There is no todo matching this id: ");
        }
        return "Successfully deleted!";
    }


    public Todo changeStatus(UUID id, boolean status) {
        Todo todoById = findTodoById(id);
        todoById.setCompleted(status);
        int indexOfTodo = commonRepository.getTodos().indexOf(todoById);
        commonRepository.getTodos().set(indexOfTodo, todoById);
        return todoById;
    }

    public void bindTodoToUser(String userEmail, UUID todoUUID) {
        commonRepository.getTodos().stream()
                .filter(todo -> todo.getId().equals(todoUUID))
                .findFirst()
                .ifPresentOrElse(todo -> todo.setOwnerEmail(userEmail),
                        () -> {
                            throw new ToDoNotFound("Todo with ID " + todoUUID + " not found.");
                        });
    }


    public void printAllTodos() {
        commonRepository.getTodos().forEach(System.out::println);
    }


}
