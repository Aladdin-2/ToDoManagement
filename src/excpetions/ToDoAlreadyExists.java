package excpetions;

public class ToDoAlreadyExists extends RuntimeException {
    public ToDoAlreadyExists(String message) {
        super(message);
    }
}
