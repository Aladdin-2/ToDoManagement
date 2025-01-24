package excpetions;

public class ToDoNotFound extends RuntimeException {
        public ToDoNotFound(String message) {
        super(message);
    }
}
