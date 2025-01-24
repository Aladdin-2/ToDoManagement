package service;

import excpetions.NotFound;
import excpetions.UserNotFound;
import model.User;
import repository.CommonRepository;

import java.util.List;

public class UserService {

    private final CommonRepository commonRepository;

    public UserService(CommonRepository commonRepository) {
        this.commonRepository = commonRepository;
    }

    public User addUser(User newUser) {
        commonRepository.getUsers().add(newUser);
        System.out.println(newUser);
        return newUser;
    }

    public void removeUser(String email) {
        List<User> users = commonRepository.getUsers();
        if (!users.isEmpty()) {
            users.stream().filter(user -> user.getEmail().equalsIgnoreCase(email)).findFirst()
                    .ifPresentOrElse(users::remove, () -> {
                        throw new UserNotFound("User with email " + email + " not found.");
                    });
        } else {
            throw new NotFound("There is no information in the database:");
        }
    }


    public void printAllUser() {
        commonRepository.getTodos().forEach(System.out::println);
    }
}
