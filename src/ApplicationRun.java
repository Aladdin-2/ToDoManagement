import model.Todo;
import model.User;
import repository.CommonRepository;
import service.ToDoService;
import service.UserService;

public class ApplicationRun {
    public static void main(String[] args) {
        CommonRepository commonRepository = new CommonRepository();
        commonRepository.getTodos().add(new Todo("Ders", "aladdin@gamil.com", "deersi oxud"));
        commonRepository.getUsers().add(new User("Asif Aliyev", "1234", "aladdin@gmail.com"));


        UserService userService = new UserService(commonRepository);
        userService.addUser(new User("Aladdin Biyabangerd", "1234", "aladdin@gmail.com"));


        userService.printAllUser();
        System.out.println("=============");
        userService.removeUser("aladdin@gmail.com");
        System.out.println("=============");

        System.out.println("=============");
        System.out.println("=============");

        ToDoService toDoService = new ToDoService(commonRepository);
        toDoService.addTodo(new Todo("Is","azer@gmail.com","Ise get"));

        System.out.println("==============");
        System.out.println("==============");
        System.out.println("==============");
        System.out.println("==============");

    }
}
