package repository;

import model.Todo;
import model.User;

import java.util.ArrayList;
import java.util.List;

public class CommonRepository {
    private static final List<User> USERS = new ArrayList<>();
    private static final List<Todo> TODOS = new ArrayList<>();


    public List<User> getUsers() {
        return USERS;
    }

    public List<Todo> getTodos(){
        return TODOS;
    }
}
