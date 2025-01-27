package service;

import excpetions.NotFound;
import excpetions.ToDoNotFound;
import excpetions.UserNotFound;
import model.Todo;
import model.User;
import repository.CommonRepository;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
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
            todos.stream().filter(todo -> Objects.equals(todo.getId(), id)).findFirst().ifPresentOrElse(todos::remove, () -> {
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
        commonRepository.getTodos().stream().filter(todo -> todo.getId().equals(todoUUID)).findFirst().ifPresentOrElse(todo -> todo.setOwnerEmail(userEmail), () -> {
            throw new ToDoNotFound("Todo with ID " + todoUUID + " not found.");
        });
    }


    public void printAllCompleted() {
        commonRepository.getTodos().stream().filter(Todo::isCompleted).forEach(System.out::println);
    }

    public void printAllUnCompleted() {
        commonRepository.getTodos().stream().filter(todo -> !todo.isCompleted()).forEach(System.out::println);
    }

    public void printAllCompletedTodosForSpecificUser(String email) {
        Optional<User> optionalUser = commonRepository.getUsers().stream().filter(user -> user.getEmail().equalsIgnoreCase(email)).findFirst();

        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            commonRepository.getTodos()
                    .stream()
                    .filter(todo -> todo.getOwnerEmail().equalsIgnoreCase(user.getEmail()) && todo.isCompleted())
                    .forEach(System.out::println);
        } else {
            throw new UserNotFound("User with email : " + email + " not found!");
        }

    }

    public void printAllUnCompletedTodosForSpecificUser(String email) {
        Optional<User> optionalUser = commonRepository.getUsers().stream().filter(user -> user.getEmail().equalsIgnoreCase(email)).findFirst();

        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            commonRepository.getTodos()
                    .stream()
                    .filter(todo -> todo.getOwnerEmail().equalsIgnoreCase(user.getEmail()) && !todo.isCompleted())
                    .forEach(System.out::println);
        } else {
            throw new UserNotFound("User with email : " + email + " not found!");
        }

    }

    public void kpiComputingForIndividualUser(String email) {
        Optional<User> optionalUser = commonRepository.getUsers().stream().filter(user -> user.getEmail().equalsIgnoreCase(email)).findFirst();

        if (optionalUser.isPresent()) {
            List<Todo> userTodos = commonRepository.getTodos().stream()
                    .filter(todo -> todo.getOwnerEmail().equalsIgnoreCase(email))
                    .toList();

            long completedCount = userTodos.stream().filter(Todo::isCompleted).count();
            long uncompletedCount = userTodos.size() - completedCount;

            if (completedCount > uncompletedCount && (completedCount - uncompletedCount) < 5) {
                System.out.println("Status: Suitable for KPI");
            } else {
                System.out.println("Status: Not Suitable for KPI");
            }

            System.out.println("User: " + optionalUser.get());
            System.out.println("Total : " + optionalUser.get());
            System.out.println("Completed Todos: " + completedCount);
            System.out.println("Uncompleted Todos: " + uncompletedCount);
        }
    }

    public void printAllTodos() {
        commonRepository.getTodos().forEach(System.out::println);
    }


}
